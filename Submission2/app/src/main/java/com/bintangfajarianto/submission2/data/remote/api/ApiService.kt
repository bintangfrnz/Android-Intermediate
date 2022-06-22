package com.bintangfajarianto.submission2.data.remote.api

import com.bintangfajarianto.submission2.data.remote.response.LoginResponses
import com.bintangfajarianto.submission2.data.remote.response.PublishResponses
import com.bintangfajarianto.submission2.data.remote.response.RegisterResponses
import com.bintangfajarianto.submission2.data.remote.response.StoriesResponses
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @GET("stories")
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int? = 0
    ) : StoriesResponses

    @POST("login")
    fun login(
        @Body requestBody: Map<String, String>
    ): Call<LoginResponses>

    @POST("register")
    fun register(
        @Body requestBody: Map<String, String>
    ): Call<RegisterResponses>

    @Multipart
    @POST("stories")
    fun publish(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?
    ): Call<PublishResponses>
}