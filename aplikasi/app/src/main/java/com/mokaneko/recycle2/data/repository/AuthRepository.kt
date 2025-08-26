package com.mokaneko.recycle2.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mokaneko.recycle2.data.SessionManager
import com.mokaneko.recycle2.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val sessionManager: SessionManager
)   {
    suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            result.user?.let {
                sessionManager.saveUserSession(it.uid)
                Resource.Success(it)
            } ?: Resource.Error("User not found")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Login failed")
        }
    }
    suspend fun register(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let {
                sessionManager.saveUserSession(it.uid)
                Resource.Success(it)
            } ?: Resource.Error("User not found")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Registration failed")
        }
    }

    fun sendPasswordResetEmail(email: String, onResult: (Resource<Void?>) -> Unit) {
        onResult(Resource.Loading)
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(Resource.Success(null))
                } else {
                    val errorMsg = task.exception?.message ?: "Gagal mengirim email reset"
                    onResult(Resource.Error(errorMsg))
                }
            }
    }


    fun logout() {
        firebaseAuth.signOut()
        sessionManager.clearSession()
    }

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
}