package com.bintangfajarianto.submission1.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bintangfajarianto.submission1.data.repository.Repository

class SplashViewModel(private val repo: Repository) : ViewModel() {
    fun getToken(): LiveData<String?> = repo.getToken().asLiveData()
}