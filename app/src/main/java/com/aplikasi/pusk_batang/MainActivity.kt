package com.aplikasi.pusk_batang

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aplikasi.pusk_batang.dashboard.DashboardActivity
import com.aplikasi.pusk_batang.model.LoginResponse
import com.aplikasi.pusk_batang.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        sharedPreferences = getSharedPreferences("PUSK_BATANG", Context.MODE_PRIVATE)
        val id: String? = sharedPreferences.getString("ID", "")
        if (id != "") {
            startActivity(Intent(applicationContext, DashboardActivity::class.java))
        }
        txtRegister.setOnClickListener {
            val intent = Intent(this, RegistrasiActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val user: String = txtUserPasien.text.toString()
            val pass: String = txtPassword.text.toString()
            if (user.trim().isEmpty() || pass.trim().isEmpty()) {
                Toast.makeText(applicationContext, "Data Belum Lengkap! ", Toast.LENGTH_SHORT)
                    .show()
            }else{
                RetrofitClient.instance.loginPasien(user , pass, "login_pasien")
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>?,
                            response: Response<LoginResponse>?
                        ) {
                            val status = response!!.body().status
                            if (status == 1) {
                                val data = response.body().data
                                for (item in data!!) {
                                    val editor: SharedPreferences.Editor? = sharedPreferences.edit()
                                    editor!!.putString("ID", item!!.id)
                                    editor.putString("NAMA", item.nama_pasien)
                                    editor.putString("NO_KK", item.noKk)
                                    editor.putString("NIK", item.nik)
                                    editor.putString("NOHP", item.nohp)
                                    editor.putString("TGL_LAHIR", item.tglLahir)
                                    editor.putString("JENKEL", item.jenkel)
                                    editor.putString("ALAMAT", item.alamat)
                                    editor.apply()
                                    val intent = Intent(applicationContext, DashboardActivity::class.java)
                                    startActivity(intent)
                                }
                            }else{
                                Toast.makeText(applicationContext, "Gagal Login", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                            Toast.makeText(this@MainActivity,"Response Gagal : ${t}", Toast.LENGTH_LONG).show()
                        }

                    })
            }
        }
    }
}
