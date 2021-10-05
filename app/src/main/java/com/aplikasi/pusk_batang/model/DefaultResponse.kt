package com.aplikasi.pusk_batang.model

import com.google.gson.annotations.SerializedName

data class DefaultResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
