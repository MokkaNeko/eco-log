package com.mokaneko.recycle2.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mokaneko.recycle2.data.repository.AuthRepository
import com.mokaneko.recycle2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableLiveData<Resource<FirebaseUser>>()
    val authState: LiveData<Resource<FirebaseUser>> = _authState

    private val _logoutState = MutableLiveData<Resource<Unit>>()
    val logoutState: LiveData<Resource<Unit>> = _logoutState

    private val _resetPasswordResult = MutableLiveData<Resource<Void?>>()
    val resetPasswordResult: LiveData<Resource<Void?>> get() = _resetPasswordResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading
            _authState.value = authRepository.login(email, password)
        }
    }

    fun sendPasswordReset(email: String) {
        authRepository.sendPasswordResetEmail(email) { result ->
            _resetPasswordResult.postValue(result)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = Resource.Loading
            _authState.value = authRepository.register(email, password)
        }
    }

    fun logout() {
        viewModelScope.launch {
            _logoutState.value = Resource.Loading
            authRepository.logout()
            _logoutState.value = Resource.Success(Unit)
        }
    }
}