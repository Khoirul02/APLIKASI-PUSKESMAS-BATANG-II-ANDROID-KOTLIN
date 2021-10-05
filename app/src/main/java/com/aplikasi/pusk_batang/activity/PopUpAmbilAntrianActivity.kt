package com.aplikasi.pusk_batang.activity

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.graphics.ColorUtils
import com.aplikasi.pusk_batang.R
import com.aplikasi.pusk_batang.model.DefaultResponse
import com.aplikasi.pusk_batang.model.PoliResponse
import com.aplikasi.pusk_batang.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_pop_up_ambil_antrian.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PopUpAmbilAntrianActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var idPasien: String? = null
    var idPoli: String? = null
    var namaPasien: String? = null
    lateinit var sharedPreferences: SharedPreferences
    var spinnerPilihPoli: Spinner? = null
    private var darkStatusBar = false
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_pop_up_ambil_antrian)
        // Get the data
        sharedPreferences = getSharedPreferences("PUSK_BATANG", Context.MODE_PRIVATE)
        idPasien = sharedPreferences.getString("ID", "")
        namaPasien = sharedPreferences.getString("NAMA", "")
        popup_window_title.text = namaPasien
        spinnerPilihPoli = this.spn_pilih_poli
        spinnerPilihPoli!!.onItemSelectedListener = this
        getPilihPoli()
        popup_window_button.setOnClickListener {
            RetrofitClient.instance.insertAntrian(idPasien.toString().trim(),idPoli.toString().trim(),"insert_antrian")
                .enqueue(object : Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>?,
                        response: Response<DefaultResponse>?
                    ) {
                        if (response!!.isSuccessful){
                            Toast.makeText(this@PopUpAmbilAntrianActivity, "Berhasil Daftar Booking", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this@PopUpAmbilAntrianActivity, AntrianActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this@PopUpAmbilAntrianActivity, "Reponse Tidak Diterima", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse>?, t: Throwable?) {
                        Toast.makeText(this@PopUpAmbilAntrianActivity, "Reponse Gagal : ${t}", Toast.LENGTH_LONG).show()
                    }

                })
        }
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(this, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // If you want dark status bar, set darkStatusBar to true
                if (darkStatusBar) {
                    this.window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                this.window.statusBarColor = Color.TRANSPARENT
                setWindowFlag(this, false)
            }
        }
        // Fade animation for the background of Popup Window
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()
        // Fade animation for the Popup Window
        popup_window_view_with_border.alpha = 0f
        popup_window_view_with_border.animate().alpha(1f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()
    }

    private fun getPilihPoli() {
        RetrofitClient.instance.getPoli("get_poli")
            .enqueue(object : Callback<PoliResponse> {
                override fun onResponse(
                    call: Call<PoliResponse>?,
                    response: Response<PoliResponse>?
                ) {
                    if(response!!.isSuccessful){
                        if(response.body().data == null){
                            Toast.makeText(this@PopUpAmbilAntrianActivity, "Data Poli Kosong", Toast.LENGTH_LONG).show()
                            val listSpinner = arrayOf("Tidak Ada Pilihan")
                            val listIdPoli = arrayOf("0")
                            val poli = ArrayAdapter(this@PopUpAmbilAntrianActivity, android.R.layout.simple_spinner_item, listSpinner)
                            poli.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            spinnerPilihPoli!!.adapter = poli
                            spinnerPilihPoli!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val idPoliSelected: String = listIdPoli[position]
                                    idPoli = idPoliSelected
                                }

                                override fun onNothingSelected(parentView: AdapterView<*>?) {
                                    // your code here
                                }
                            }
                        }else{
                            val data = response.body().data!!.toTypedArray()
                            val listSpinner : MutableList<String> = ArrayList()
                            val listIdPoli : MutableList<String> = ArrayList()
                            for (i in data.indices) {
                                listIdPoli.add(data[i]!!.id!!)
                                listSpinner.add(data[i]!!.nama!!)
                            }
                            val poli = ArrayAdapter(this@PopUpAmbilAntrianActivity, android.R.layout.simple_spinner_item, listSpinner)
                            poli.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            spinnerPilihPoli!!.adapter = poli
                            spinnerPilihPoli!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parentView: AdapterView<*>?,
                                    selectedItemView: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    val idPoliSelected: String = listIdPoli[position]
                                    idPoli = idPoliSelected
                                }

                                override fun onNothingSelected(parentView: AdapterView<*>?) {
                                    // your code here
                                }
                            }
                        }
                    }else{
                        Toast.makeText(this@PopUpAmbilAntrianActivity, "Reponse Tidak Ada", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<PoliResponse>?, t: Throwable?) {
                    Toast.makeText(this@PopUpAmbilAntrianActivity, "Reponse Gagal : $t", Toast.LENGTH_LONG).show()
                }

            })
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS else {
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }

    @SuppressLint("SetTextI18n")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        tv_pilih_poli.text = "Pilih Poli"
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
