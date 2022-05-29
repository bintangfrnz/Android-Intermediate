package com.bintangfajarianto.submission1.ui.authentication.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bintangfajarianto.submission1.data.remote.api.ApiConfig
import com.bintangfajarianto.submission1.data.remote.response.LoginResponses
import com.bintangfajarianto.submission1.data.repository.Repository
import com.bintangfajarianto.submission1.utils.Event
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repo: Repository) : ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>> get() = _message

    private val _responseLogin = MutableLiveData<LoginResponses>()
    val responseLogin: LiveData<LoginResponses> = _responseLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true

        val requestBody = mapOf(
            "email" to email,
            "password" to password
        )
        val client = ApiConfig.getApiService().login(requestBody)

        client.enqueue(object: Callback<LoginResponses> {
            override fun onResponse(call: Call<LoginResponses>, response: Response<LoginResponses>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _responseLogin.value = responseBody!!

                    // Saving Token
                    saveToken(responseBody.loginResult.token)
                } else {
                    _message.value = Event("User Not Found")
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponses>, t: Throwable) {
                _isLoading.value = false
                _message.value = Event("API Failure")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun saveToken(token: String) {
        viewModelScope.launch {
            repo.saveToken(token)
        }
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}