package com.bintangfajarianto.submission1.data.remote.api

import com.bintangfajarianto.submission1.data.remote.response.LoginResponses
import com.bintangfajarianto.submission1.data.remote.response.PublishResponses
import com.bintangfajarianto.submission1.data.remote.response.RegisterResponses
import com.bintangfajarianto.submission1.data.remote.response.StoriesResponses
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("stories")
    fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int? = 0
    ) : Call<StoriesResponses>

    @POST("register")
    fun register(
        @Body requestBody: Map<String, String>
    ): Call<RegisterResponses>

    @POST("login")
    fun login(
        @Body requestBody: Map<String, String>
    ): Call<LoginResponses>

    @Multipart
    @POST("stories")
    fun publish(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<PublishResponses>
}