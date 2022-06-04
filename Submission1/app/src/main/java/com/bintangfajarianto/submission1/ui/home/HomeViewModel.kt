package com.bintangfajarianto.submission1.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangfajarianto.submission1.data.remote.api.ApiConfig
import com.bintangfajarianto.submission1.data.remote.response.StoriesResponses
import com.bintangfajarianto.submission1.data.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (private val repo: Repository) : ViewModel() {

    private val _responseStories = MutableLiveData<StoriesResponses>()
    val responseStories: LiveData<StoriesResponses> = _responseStories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRefresh = MutableLiveData<Boolean>()
    val isRefresh: LiveData<Boolean> = _isRefresh

    private val _isNoData = MutableLiveData<Boolean>()
    val isNoData: LiveData<Boolean> = _isNoData

    fun getAllStories(token: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllStories(
            token, null, null
        )

        client.enqueue(object: Callback<StoriesResponses> {
            override fun onResponse(call: Call<StoriesResponses>, response: Response<StoriesResponses>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _responseStories.value = responseBody!!
                    _isNoData.value = responseBody.listStory.isNullOrEmpty()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoriesResponses>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getAllStoriesByRefresh(token: String) {
        _isRefresh.value = true

        val client = ApiConfig.getApiService().getAllStories(
            token, null, null
        )

        client.enqueue(object: Callback<StoriesResponses> {
            override fun onResponse(call: Call<StoriesResponses>, response: Response<StoriesResponses>) {
                _isRefresh.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _responseStories.value = responseBody!!
                    _isNoData.value = responseBody.listStory.isNullOrEmpty()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoriesResponses>, t: Throwable) {
                _isRefresh.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            repo.saveToken(token)
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}