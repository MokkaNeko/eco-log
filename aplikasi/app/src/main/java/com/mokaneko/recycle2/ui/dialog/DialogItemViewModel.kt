package com.mokaneko.recycle2.ui.dialog

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.data.repository.AuthRepository
import com.mokaneko.recycle2.data.repository.KategoriData
import com.mokaneko.recycle2.data.repository.TrashRepository
import com.mokaneko.recycle2.utils.FileUtils
import com.mokaneko.recycle2.utils.helper.CloudinaryHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DialogItemViewModel @Inject constructor(
    private val trashRepository: TrashRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _kategoriList = MutableLiveData<List<String>>()
    val kategoriList: LiveData<List<String>> = _kategoriList

    private val _subkategoriList = MutableLiveData<List<String>>()
    val subkategoriList: LiveData<List<String>> = _subkategoriList

    val isLoading = MutableLiveData<Boolean>()

    fun loadKategori() {
        _kategoriList.value = KategoriData.listKategori.map { it.namaKategori }
    }

    fun loadSubkategoriByKategori(kategori: String) {
        val sub = KategoriData.listKategori
            .firstOrNull { it.namaKategori == kategori }
            ?.subKategoriList
            ?.map { it.namaSubKategori } ?: emptyList()

        _subkategoriList.value = sub
    }

    fun validateInput(nama: String, kategori: String, subkategori: String): Boolean {
        return nama.isNotBlank() && kategori.isNotBlank() && subkategori.isNotBlank()
    }

    fun uploadTrashItem(
        nama: String,
        kategori: String,
        subkategori: String,
        deskripsi: String,
        imageUri: Uri,
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val file = FileUtils.copyUriToCache(context, imageUri)
                if (file == null) {
                    onFailure("Gagal mengakses file gambar.")
                    return@launch
                }

                val imageUrl = CloudinaryHelper.uploadImage(file)
                val currentUser = authRepository.getCurrentUser()
                if (currentUser == null) {
                    onFailure("User tidak ditemukan")
                    return@launch
                }

                val item = SampahItem(
                    id = UUID.randomUUID().toString(),
                    userId = currentUser.uid,
                    title = nama,
                    description = deskripsi,
                    category = kategori,
                    subcategory = subkategori,
                    imageUrl = imageUrl,
                    isTerbuang = false
                )

                trashRepository.addTrashItem(item, isActive = true)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFailure(it.message ?: "Gagal menyimpan item") }

            } catch (e: Exception) {
                onFailure(e.localizedMessage ?: "Terjadi kesalahan saat upload")
            } finally {
                isLoading.value = false
            }
        }
    }
}