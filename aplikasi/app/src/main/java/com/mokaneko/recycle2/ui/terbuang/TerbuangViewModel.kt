package com.mokaneko.recycle2.ui.terbuang

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.data.repository.AuthRepository
import com.mokaneko.recycle2.data.repository.TrashRepository
import com.mokaneko.recycle2.utils.helper.CloudinaryHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class TerbuangViewModel @Inject constructor(
    private val repository: TrashRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _terbuangItems = MutableLiveData<List<SampahItem>>()
    val terbuangItems: LiveData<List<SampahItem>> = _terbuangItems

    private val _eventMessage = MutableLiveData<String>()
    val eventMessage: LiveData<String> = _eventMessage

    fun loadTerbuangItems() {
        val user = authRepository.getCurrentUser() ?: return
        viewModelScope.launch {
            repository.getTrashItemsByUser(user.uid, isActive = false)
                .collect { items -> _terbuangItems.value = items }
        }
    }

    fun deleteTerbuangItem(item: SampahItem) {
        viewModelScope.launch {
            try {
                repository.deleteItem(item.id, isActive = false).await()
                CloudinaryHelper.deleteImageByUrl(item.imageUrl)
                _eventMessage.value = "Sampah berhasil dihapus"
            } catch (e: Exception) {
                Log.e("TerbuangVM", "Gagal hapus item: ${e.message}")
            }
        }
    }

    fun restoreItemToAktif(item: SampahItem) {
        viewModelScope.launch {
            try {
                val newItem = item.copy(isTerbuang = false)
                repository.moveItem(newItem, toActive = true).await()
                _eventMessage.value = "Sampah dipindahkan ke daftar aktif"
            } catch (e: Exception) {
                Log.e("TerbuangVM", "Gagal kembalikan item: ${e.message}")
            }
        }
    }
}
