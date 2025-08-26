package com.mokaneko.recycle2.ui.kamera

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val _isFlashOn = MutableLiveData(false)
    val isFlashOn: MutableLiveData<Boolean> = _isFlashOn

    private val _capturedImageUri = MutableLiveData<Uri?>()
    val capturedImageUri: LiveData<Uri?> = _capturedImageUri

    fun toggleFlash() {
        _isFlashOn.value = _isFlashOn.value?.not() ?: false
    }

    fun resetFlash() {
        _isFlashOn.value = false
    }

    fun setCapturedImageUri(uri: Uri) {
        _capturedImageUri.value = uri
    }

    fun clearCapturedImage() {
        _capturedImageUri.value = null
    }

    fun cropImageToSquare(context: Context, uri: Uri, onComplete: (Uri?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val originalBytes = inputStream?.readBytes()
                inputStream?.close()

                if (originalBytes == null) {
                    withContext(Dispatchers.Main) { onComplete(null) }
                    return@launch
                }

                // Decode bitmap
                var bitmap = BitmapFactory.decodeByteArray(originalBytes, 0, originalBytes.size)

                // Baca dan terapkan orientasi
                val tempFile = File.createTempFile("EXIF_", ".jpg", context.cacheDir)
                FileOutputStream(tempFile).use { it.write(originalBytes) }

                val exif = ExifInterface(tempFile.absolutePath)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )

                bitmap = when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90f)
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180f)
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270f)
                    else -> bitmap
                }

                // Crop jadi square
                val dimension = minOf(bitmap.width, bitmap.height)
                val xOffset = (bitmap.width - dimension) / 2
                val yOffset = (bitmap.height - dimension) / 2
                val squareBitmap = Bitmap.createBitmap(bitmap, xOffset, yOffset, dimension, dimension)

                // Simpan hasil crop ke file
                val croppedFile = File.createTempFile("CROPPED_", ".jpg", context.cacheDir)
                FileOutputStream(croppedFile).use {
                    squareBitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
                    it.flush()
                }

                val croppedUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    croppedFile
                )

                _capturedImageUri.postValue(croppedUri)
                withContext(Dispatchers.Main) {
                    onComplete(croppedUri)
                }

            } catch (e: Exception) {
                Log.e("CameraViewModel", "Crop error: ${e.message}")
                withContext(Dispatchers.Main) { onComplete(null) }
            }
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun classifyImage(uri: Uri, onResult: (String, Float) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

                val input = Array(1) { Array(224) { Array(224) { FloatArray(3) } } }
                for (x in 0 until 224) {
                    for (y in 0 until 224) {
                        val pixel = resizedBitmap.getPixel(x, y)
                        input[0][y][x][0] = (Color.red(pixel) / 127.5f) - 1.0f
                        input[0][y][x][1] = (Color.green(pixel) / 127.5f) - 1.0f
                        input[0][y][x][2] = (Color.blue(pixel) / 127.5f) - 1.0f
                    }
                }

                val output = Array(1) { FloatArray(12) }

                // load model from assets
                val assetFileDescriptor = context.assets.openFd("model_klasifikasi_sampah.tflite")
                val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
                val fileChannel = fileInputStream.channel
                val startOffset = assetFileDescriptor.startOffset
                val declaredLength = assetFileDescriptor.declaredLength
                val modelBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)

                val interpreter = Interpreter(modelBuffer)
                interpreter.run(input, output)
                interpreter.close()

                val labelIndex = output[0].indices.maxByOrNull { output[0][it] } ?: -1
                val label = when (labelIndex) {
                    0 -> "Botol"
                    1 -> "Kaca"
                    2 -> "Kaleng"
                    3 -> "Kantong Plastik"
                    4 -> "Kardus"
                    5 -> "Kemasan Plastik"
                    6 -> "Kertas"
                    7 -> "Logam"
                    8 -> "Baterai dan Aki"
                    9 -> "Sampah Elektronik"
                    10 -> "Kayu"
                    11 -> "Sisa Makanan dan Dedaunan"
                    else -> "Tidak Dikenali"
                }
                val confidence = output[0][labelIndex] * 100f

                withContext(Dispatchers.Main) {
                    onResult(label, confidence)
                }

            } catch (e: Exception) {
                Log.e("CameraViewModel", "Classification error: ${e.message}")
                withContext(Dispatchers.Main) {
                    onResult("Error", 0f)
                }
            }
        }
    }
}