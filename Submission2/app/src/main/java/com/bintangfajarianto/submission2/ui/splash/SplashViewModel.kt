package com.bintangfajarianto.submission2.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bintangfajarianto.submission2.data.repository.AuthRepository

class SplashViewModel(private val repo: AuthRepository) : ViewModel() {
    fun getToken(): LiveData<String?> = repo.getToken().asLiveData()
}