package com.bintangfajarianto.submission1.data.repository

import com.bintangfajarianto.submission1.data.local.AuthPreference
import kotlinx.coroutines.flow.Flow

class Repository private constructor(private val authPref: AuthPreference) {
    suspend fun saveToken(token: String) {
        authPref.saveToken(token)
    }

    fun getToken(): Flow<String?> = authPref.getToken()

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            authPref: AuthPreference
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(authPref)
            }.also { instance = it }
    }
}