package com.mokaneko.recycle2.ui.katalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.data.repository.TrashRepository
import com.mokaneko.recycle2.utils.Resource
import com.mokaneko.recycle2.utils.helper.CloudinaryHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class DescriptionViewModel @Inject constructor(
    private val repository: TrashRepository
) : ViewModel() {

    private val _deleteState = MutableLiveData<Resource<Unit>>()
    val deleteState: LiveData<Resource<Unit>> = _deleteState

    private val _moveState = MutableLiveData<Resource<Unit>>()
    val moveState: LiveData<Resource<Unit>> = _moveState

    val isLoading = MutableLiveData<Boolean>()

    fun moveToTerbuang(item: SampahItem) {
        viewModelScope.launch {
            _moveState.value = Resource.Loading
            try {
                val newItem = item.copy(isTerbuang = true)
                repository.moveItem(newItem, toActive = false).await()
                _moveState.value = Resource.Success(Unit)
            } catch (e: Exception) {
                _moveState.value = Resource.Error("Gagal memindahkan item: ${e.message}")
            }
        }
    }

    fun deleteItem(item: SampahItem) {
        viewModelScope.launch {
            _deleteState.value = Resource.Loading
            try {
                val isActive = !item.isTerbuang
                repository.deleteItem(item.id, isActive).await()
                CloudinaryHelper.deleteImageByUrl(item.imageUrl)
                _deleteState.value = Resource.Success(Unit)
            } catch (e: Exception) {
                _deleteState.value = Resource.Error("Gagal menghapus item: ${e.message}")
            }
        }
    }
}