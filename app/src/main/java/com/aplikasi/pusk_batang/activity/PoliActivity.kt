package com.aplikasi.pusk_batang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aplikasi.pusk_batang.R
import com.aplikasi.pusk_batang.adapter.PoliAdapter
import com.aplikasi.pusk_batang.model.DataPoliItem
import com.aplikasi.pusk_batang.model.PoliResponse
import com.aplikasi.pusk_batang.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_poli.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
class PoliActivity : AppCompatActivity() {
    private lateinit var poliAdapter: PoliAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poli)
        poliAdapter = PoliAdapter(arrayListOf())
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = poliAdapter
        getDataPoli()
    }

    private fun getDataPoli() {
        RetrofitClient.instance.getPoli("get_poli")
            .enqueue(object : Callback<PoliResponse> {
                override fun onResponse(
                    call: Call<PoliResponse>?,
                    response: Response<PoliResponse>?
                ) {
                    if(response!!.isSuccessful){
                        if(response.body().data == null){
                            Toast.makeText(this@PoliActivity, "Data Poli Tidak Ada", Toast.LENGTH_LONG).show()
                        }else{
                            tampilPoli(response.body())
                        }
                    }else{
                        Toast.makeText(this@PoliActivity, "Reponse Tidak Ada", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<PoliResponse>?, t: Throwable?) {
                    Toast.makeText(this@PoliActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
                }

            })
    }
    private fun tampilPoli(data : PoliResponse){
        val data = data.data
        poliAdapter.setData(data as List<DataPoliItem>)
    }
}
