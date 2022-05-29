package com.bintangfajarianto.submission1.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangfajarianto.submission1.data.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel (private val repo: Repository) : ViewModel() {



    fun saveToken(token: String) {
        viewModelScope.launch {
            repo.saveToken(token)
        }
    }
}