package com.bintangfajarianto.submission1.ui.authentication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bintangfajarianto.submission1.data.remote.api.ApiConfig
import com.bintangfajarianto.submission1.data.remote.response.RegisterResponses
import com.bintangfajarianto.submission1.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>> get() = _message

    private val _responseRegister = MutableLiveData<RegisterResponses>()
    val responseRegister: LiveData<RegisterResponses> = _responseRegister

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true

        val requestBody = mapOf(
            "name" to name,
            "email" to email,
            "password" to password
        )
        val client = ApiConfig.getApiService().register(requestBody)

        client.enqueue(object: Callback<RegisterResponses> {
            override fun onResponse(call: Call<RegisterResponses>, response: Response<RegisterResponses>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _responseRegister.value = responseBody!!
                } else {
                    _message.value = Event("Email Already Used")
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponses>, t: Throwable) {
                _isLoading.value = false
                _message.value = Event("API Failure")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}