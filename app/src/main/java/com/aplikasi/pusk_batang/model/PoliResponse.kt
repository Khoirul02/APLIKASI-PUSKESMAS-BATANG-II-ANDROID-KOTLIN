package com.aplikasi.pusk_batang.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PoliResponse(

	@field:SerializedName("data")
	val data: List<DataPoliItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataPoliItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("kode_poli")
	val kode_poli: String? = null,

	@field:SerializedName("create_at")
	val createAt: String? = null
) : Parcelable
