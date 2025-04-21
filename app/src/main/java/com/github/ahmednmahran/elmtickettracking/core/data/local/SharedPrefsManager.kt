package com.github.ahmednmahran.elmtickettracking.core.data.local

import android.content.Context
import javax.inject.Inject
import androidx.core.content.edit

class SharedPrefsManager @Inject constructor(
    private val context: Context
) {
    private val sharedPrefs by lazy {
        context.getSharedPreferences("IncidentManagementPrefs", Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        sharedPrefs.edit() { putString("auth_token", token) }
    }

    fun getToken(): String? = sharedPrefs.getString("auth_token", null)

    fun clearAuthData() {
        sharedPrefs.edit() { remove("auth_token") }
    }
}