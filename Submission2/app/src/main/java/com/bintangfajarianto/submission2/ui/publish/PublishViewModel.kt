package com.bintangfajarianto.submission2.ui.publish

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bintangfajarianto.submission2.data.remote.api.ApiConfig
import com.bintangfajarianto.submission2.data.remote.response.PublishResponses
import com.bintangfajarianto.submission2.data.repository.AuthRepository
import com.bintangfajarianto.submission2.utils.Event
import java.io.File
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublishViewModel(private val repo: AuthRepository) : ViewModel() {

    private val _message = MutableLiveData<Event<String>>()
    val message : LiveData<Event<String>> get() = _message

    private val _responsePublish = MutableLiveData<PublishResponses>()
    val responsePublish: LiveData<PublishResponses> = _responsePublish

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun upload(file: File, desc: String, token: String) {
        _isLoading.value = true

        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )

        val client = ApiConfig.getApiService().publish(
            token,
            imageMultipart,
            desc.toRequestBody("text/plain".toMediaType()),
            "1".toRequestBody(),
            "1".toRequestBody()
        )

        client.enqueue(object: Callback<PublishResponses> {
            override fun onResponse(call: Call<PublishResponses>, response: Response<PublishResponses>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _responsePublish.value = responseBody!!
                } else {
                    _message.value = Event("Unsuccessful")
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PublishResponses>, t: Throwable) {
                _isLoading.value = false
                _message.value = Event("API Failure")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getToken(): LiveData<String?> = repo.getToken().asLiveData()

    companion object {
        private const val TAG = "PublishViewModel"
    }
}
