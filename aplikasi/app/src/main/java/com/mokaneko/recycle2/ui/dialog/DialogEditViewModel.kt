package com.mokaneko.recycle2.ui.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.data.repository.AuthRepository
import com.mokaneko.recycle2.data.repository.KategoriData
import com.mokaneko.recycle2.data.repository.TrashRepository
import com.mokaneko.recycle2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DialogEditViewModel @Inject constructor(
    private val trashRepository: TrashRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _namaItem = MutableLiveData<String>()
    val namaItem: LiveData<String> = _namaItem

    private val _kategoriList = MutableLiveData<List<String>>()
    val kategoriList: LiveData<List<String>> = _kategoriList

    private val _subkategoriList = MutableLiveData<List<String>>()
    val subkategoriList: LiveData<List<String>> = _subkategoriList

    private val _editState = MutableLiveData<Resource<Unit>>()
    val editState: LiveData<Resource<Unit>> = _editState

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

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

    fun updateItem(item: SampahItem) {
        _editState.value = Resource.Loading
        isLoading.value = true

        trashRepository.editItem(item, !item.isTerbuang)
            .addOnSuccessListener {
                _editState.value = Resource.Success(Unit)
                isLoading.value = false
            }
            .addOnFailureListener {
                _editState.value = Resource.Error(it.message ?: "Gagal mengedit item")
                isLoading.value = false
            }
    }

    fun getCurrentUserId(): String {
        return authRepository.getCurrentUser()?.uid.orEmpty()
    }

    fun validateInput(nama: String, kategori: String, subkategori: String): Boolean {
        return nama.isNotBlank() && kategori.isNotBlank() && subkategori.isNotBlank()
    }
}