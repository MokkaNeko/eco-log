package com.mokaneko.recycle2.ui.kamera

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.databinding.ActivityCameraBinding
import com.mokaneko.recycle2.ui.recommendation.RecommendationActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var previewView: PreviewView
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var camera: Camera? = null
    private var imageCapture: ImageCapture? = null
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private val viewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        previewView = binding.cameraPreviewContainer

        binding.cameraButtonBack.setOnClickListener {
            finish()
        }
        binding.cameraButtonSwitch.setOnClickListener {
            cameraSelector = if (cameraSelector.equals(CameraSelector.DEFAULT_BACK_CAMERA)) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA

            startCamera()
        }
        binding.cameraButtonFlash.setOnClickListener {
            viewModel.toggleFlash()
        }

        binding.cameraButtonShutter.setOnClickListener {
            val imageCapture = imageCapture ?: return@setOnClickListener

            val photoFile = createImageFile()
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(this),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val uri = FileProvider.getUriForFile(
                            this@CameraActivity,
                            "${packageName}.fileprovider",
                            photoFile
                        )
                        viewModel.cropImageToSquare(this@CameraActivity, uri) { croppedUri ->
                            if (croppedUri != null) {
                                binding.cameraPreviewContainer.visibility = View.GONE
                                binding.cameraResultContainer.visibility = View.VISIBLE
                                Glide.with(this@CameraActivity)
                                    .load(croppedUri)
                                    .into(binding.cameraResultContainer)

                                binding.cameraBottomControl.visibility = View.GONE
                                binding.cameraBottomConfirm.visibility = View.VISIBLE
                                viewModel.resetFlash()
                            } else {
                                Toast.makeText(this@CameraActivity, "Gagal crop gambar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(this@CameraActivity, "Gagal mengambil gambar", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "onError: ${exception.message}")
                    }
                }
            )
        }

        binding.cameraButtonConfirm.setOnClickListener {
            val uri = viewModel.capturedImageUri.value
            if (uri != null) {
                viewModel.classifyImage(uri) { label, confidence ->
                    val intent = Intent(this, RecommendationActivity::class.java).apply {
                        putExtra("image_uri", uri.toString())
                        putExtra("label", label)
                        putExtra("confidence", confidence)
                    }
                    startActivity(intent)
                    finish()
                }
            }
        }

        binding.cameraButtonCancel.setOnClickListener {
            viewModel.clearCapturedImage()
            binding.cameraPreviewContainer.visibility = View.VISIBLE
            binding.cameraResultContainer.visibility = View.GONE
            binding.cameraBottomConfirm.visibility = View.GONE
            binding.cameraBottomControl.visibility = View.VISIBLE
        }

        binding.cameraButtonGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val selectedImageUri = result.data?.data
                if (selectedImageUri != null) {
                    viewModel.cropImageToSquare(this, selectedImageUri) { croppedUri ->
                        if (croppedUri != null) {
                            binding.cameraPreviewContainer.visibility = View.GONE
                            binding.cameraResultContainer.visibility = View.VISIBLE
                            binding.cameraBottomControl.visibility = View.GONE
                            binding.cameraBottomConfirm.visibility = View.VISIBLE

                            Glide.with(this)
                                .load(croppedUri)
                                .into(binding.cameraResultContainer)
                        } else {
                            Toast.makeText(this, "Gagal memproses gambar", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        viewModel.isFlashOn.observe(this) { isOn ->
            camera?.cameraControl?.enableTorch(isOn)
            val iconRes = if (isOn) R.drawable.new_icon_flashon else R.drawable.new_icon_flashoff
            binding.cameraButtonFlash.setImageResource(iconRes)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resetFlash()
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        val imageCaptureConfig = ImageCapture.Builder().build()
        imageCapture = imageCaptureConfig

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraPreviewContainer.surfaceProvider)
                }

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCaptureConfig
                )
                val isOn = viewModel.isFlashOn.value ?: false
                camera?.cameraControl?.enableTorch(isOn)
            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir = cacheDir
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    companion object {
        private const val TAG = "CameraActivity"
    }
}