package com.aplikasi.pusk_batang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplikasi.pusk_batang.R
import com.aplikasi.pusk_batang.adapter.DokterAdapter
import com.aplikasi.pusk_batang.model.DataDokterItem
import com.aplikasi.pusk_batang.model.DokterResponse
import com.aplikasi.pusk_batang.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_dokter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class DokterActivity : AppCompatActivity() {
    private lateinit var dokterAdapter: DokterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dokter)
        supportActionBar!!.setTitle("Dokter")
        dokterAdapter = DokterAdapter(arrayListOf())
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = dokterAdapter
        getDataDokter()
    }

    private fun getDataDokter() {
        RetrofitClient.instance.getDokter("get_dokter")
            .enqueue(object : Callback<DokterResponse> {
                override fun onResponse(
                    call: Call<DokterResponse>?,
                    response: Response<DokterResponse>?
                ) {
                    if (response!!.isSuccessful){
                        if (response.body().data == null){
                            Toast.makeText(this@DokterActivity, "Data Dokter Kosong", Toast.LENGTH_LONG).show()
                        }else{
                            tampilDokter(response.body())
                        }
                    }else{
                        Toast.makeText(this@DokterActivity, "Reponse Tidak Bisa", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<DokterResponse>?, t: Throwable?) {
                    Toast.makeText(this@DokterActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
                }

            })
    }
    private fun tampilDokter(data : DokterResponse){
        val result =data.data
        dokterAdapter.setData(result as List<DataDokterItem>)
    }
}
