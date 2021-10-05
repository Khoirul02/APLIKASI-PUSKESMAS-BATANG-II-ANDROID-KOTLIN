package com.aplikasi.pusk_batang.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DokterResponse(

	@field:SerializedName("data")
	val data: List<DataDokterItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataDokterItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("bidang")
	val bidang: String? = null,

	@field:SerializedName("create_at")
	val createAt: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
