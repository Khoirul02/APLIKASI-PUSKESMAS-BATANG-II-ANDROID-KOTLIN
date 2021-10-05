package com.aplikasi.pusk_batang.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.aplikasi.pusk_batang.R
import com.aplikasi.pusk_batang.adapter.AntrianAdapter
import com.aplikasi.pusk_batang.model.AntrianResponse
import com.aplikasi.pusk_batang.model.DataAntrianItem
import com.aplikasi.pusk_batang.rest.RetrofitClient
import kotlinx.android.synthetic.main.fragment_riwayat_antrian.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
@Suppress("UNCHECKED_CAST", "NAME_SHADOWING")
class RiwayatAntrianFragment : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    private lateinit var antrianAdapter: AntrianAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_riwayat_antrian, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onStart() {
        super.onStart()
        setUpRecyclerView()
        sharedPreferences = activity!!.getSharedPreferences("PUSK_BATANG", Context.MODE_PRIVATE)
        val idPasien: String? = sharedPreferences.getString("ID", "")
        getDataAntrianHariIni(idPasien!!)
    }

    private fun getDataAntrianHariIni(id_pasien : String) {
        RetrofitClient.instance.getAntrianByIdPasien(id_pasien,"get_antrian_id_pasien")
            .enqueue(object : Callback<AntrianResponse> {
                override fun onResponse(
                    call: Call<AntrianResponse>?,
                    response: Response<AntrianResponse>?
                ) {
                    if (response!!.isSuccessful){
                        if (response.body().data === null){
                            Toast.makeText(activity, "Data Antrian Kosong", Toast.LENGTH_LONG).show()
                        }else{
                            tampilAntrian(response.body())
                        }
                    }else{
                        Toast.makeText(activity, "Response Tidak Ada", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<AntrianResponse>?, t: Throwable?) {
                    Toast.makeText(activity, "Data Kosong", Toast.LENGTH_LONG).show()
                }

            })
    }

    private fun setUpRecyclerView() {
        antrianAdapter = AntrianAdapter(arrayListOf())
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = antrianAdapter
        }
    }

    private fun tampilAntrian(data : AntrianResponse){
        val data = data.data
        antrianAdapter.setData(data as List<DataAntrianItem>)
    }

}
