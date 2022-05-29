package com.bintangfajarianto.submission1.data.remote.response

import com.google.gson.annotations.SerializedName

data class PublishResponses(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
