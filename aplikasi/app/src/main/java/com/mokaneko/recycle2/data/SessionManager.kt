package com.mokaneko.recycle2.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("sampahlog_prefs", Context.MODE_PRIVATE)

    fun saveUserSession(userId: String) {
        prefs.edit()
            .putString("user_id", userId)
            .putBoolean("is_logged_in", true)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun getUserSession(): String? {
        return prefs.getString("user_id", null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}