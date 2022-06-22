package com.bintangfajarianto.submission2.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bintangfajarianto.submission2.data.local.AuthPreference
import com.bintangfajarianto.submission2.data.local.room.StoryDatabase
import com.bintangfajarianto.submission2.data.remote.api.ApiConfig
import com.bintangfajarianto.submission2.data.repository.AuthRepository
import com.bintangfajarianto.submission2.data.repository.StoryRepository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "application")

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val authPref = AuthPreference.getInstance(context.dataStore)
        return AuthRepository.getInstance(authPref)
    }

    fun provideStoryRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(database, apiService)
    }
}