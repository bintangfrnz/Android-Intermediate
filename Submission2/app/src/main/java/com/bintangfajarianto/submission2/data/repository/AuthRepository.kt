package com.bintangfajarianto.submission2.data.repository

import com.bintangfajarianto.submission2.data.local.AuthPreference
import kotlinx.coroutines.flow.Flow

class AuthRepository private constructor(private val authPref: AuthPreference) {
    suspend fun saveToken(token: String) {
        authPref.saveToken(token)
    }

    fun getToken(): Flow<String?> = authPref.getToken()

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(
            authPref: AuthPreference
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(authPref)
            }.also { instance = it }
    }
}