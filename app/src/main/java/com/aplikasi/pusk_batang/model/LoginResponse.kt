package com.aplikasi.pusk_batang.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("nama_pasien")
	val nama_pasien: String? = null,

	@field:SerializedName("nohp")
	val nohp: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("jenkel")
	val jenkel: String? = null,

	@field:SerializedName("no_kk")
	val noKk: String? = null,

	@field:SerializedName("create_at")
	val createAt: String? = null,

	@field:SerializedName("tgl_lahir")
	val tglLahir: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
) : Parcelable
