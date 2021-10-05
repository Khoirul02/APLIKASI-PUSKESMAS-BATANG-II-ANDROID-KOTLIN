package com.aplikasi.pusk_batang.rest

import com.aplikasi.pusk_batang.model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("restapi_puskesmas_batang.php")
    fun loginPasien(
        @Field("nohp") nohp: String,
        @Field("tgl_lahir") tgl_lahir: String,
        @Query("function") function:String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("restapi_puskesmas_batang.php")
    fun daftarPasien(
        @Field("nama_pasien") nama_pasien:String,
        @Field("no_kk") no_kk:String,
        @Field("nik") nik:String,
        @Field("nohp") nohp:String,
        @Field("tgl_lahir") tgl_lahir:String,
        @Field("jenkel") jenkel: String,
        @Field("alamat") alamat: String,
        @Query("function") function:String
    ): Call<DefaultResponse>
    @GET("restapi_puskesmas_batang.php")
    fun getDokter(
        @Query("function") function: String
    ): Call<DokterResponse>

    @GET("restapi_puskesmas_batang.php")
    fun getPoli(
        @Query("function") function: String
    ): Call<PoliResponse>

    @FormUrlEncoded
    @POST("restapi_puskesmas_batang.php")
    fun getAntrianByIdPasien(
        @Field("id_pasien") id_pasien:String,
        @Query("function") function:String
    ): Call<AntrianResponse>

    @FormUrlEncoded
    @POST("restapi_puskesmas_batang.php")
    fun insertAntrian(
        @Field("id_pasien") id_pasien:String,
        @Field("id_poli") id_poli:String,
        @Query("function") function:String
    ): Call<DefaultResponse>
}