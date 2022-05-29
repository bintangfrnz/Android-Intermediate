package com.bintangfajarianto.submission1.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bintangfajarianto.submission1.data.local.AuthPreference
import com.bintangfajarianto.submission1.data.repository.Repository

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "application")

object Injection {
    fun provideRepository(context: Context): Repository {
        val authPref = AuthPreference.getInstance(context.dataStore)

        return Repository.getInstance(authPref)
    }
}