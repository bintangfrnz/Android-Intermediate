package com.bintangfajarianto.submission2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bintangfajarianto.submission2.data.local.entity.Story
import com.bintangfajarianto.submission2.data.repository.AuthRepository
import com.bintangfajarianto.submission2.data.repository.StoryRepository
import kotlinx.coroutines.launch

class HomeViewModel (
    private val authRepo: AuthRepository,
    private val storyRepo: StoryRepository
) : ViewModel() {
    fun getAllStories(token: String): LiveData<PagingData<Story>> =
        storyRepo.getAllStories(token).cachedIn(viewModelScope)

    fun saveToken(token: String) {
        viewModelScope.launch {
            authRepo.saveToken(token)
        }
    }
}
