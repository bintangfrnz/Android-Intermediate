package com.bintangfajarianto.submission2.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getToken(): Flow<String?> {
        return dataStore.data.map { pref ->
            pref[TOKEN]
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { pref ->
            pref[TOKEN] = token
        }
    }

    companion object {
        private val TOKEN = stringPreferencesKey("token")

        @Volatile
        private var instance: AuthPreference? = null

        fun getInstance(
            dataStore: DataStore<Preferences>
        ): AuthPreference =
            instance ?: synchronized(this) {
                instance ?: AuthPreference(dataStore)
            }.also { instance = it }
    }
}