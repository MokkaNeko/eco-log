package com.mokaneko.recycle2.ui.katalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mokaneko.recycle2.data.model.SampahItem
import com.mokaneko.recycle2.data.repository.AuthRepository
import com.mokaneko.recycle2.data.repository.TrashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KatalogViewModel @Inject constructor(
    private val trashRepository: TrashRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _items = MutableLiveData<List<SampahItem>>()
    val items: LiveData<List<SampahItem>> = _items

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private var trashListenerJob: Job? = null

    fun loadUserTrashItems(isActive: Boolean = true) {
        _isLoading.value = true

        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser == null) {
                _items.value = emptyList()
                _isLoading.value = false
                return@launch
            }

            trashListenerJob?.cancel()
            trashListenerJob = launch {
                trashRepository.getTrashItemsByUser(currentUser.uid, isActive)
                    .collect { data ->
                        _items.postValue(data)
                        _isLoading.postValue(false)
                    }
            }
        }
    }
}
