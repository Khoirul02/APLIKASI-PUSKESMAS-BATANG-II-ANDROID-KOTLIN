package com.aplikasi.pusk_batang

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.aplikasi.pusk_batang.model.DefaultResponse
import com.aplikasi.pusk_batang.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_registrasi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class RegistrasiActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var listJenisKelamin = arrayOf("laki-laki", "perempuan")
    var spinnerJenis: Spinner? = null
    var cal = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)
        supportActionBar!!.hide()
        spinnerJenis = this.spn_jk_pengguna
        spinnerJenis!!.onItemSelectedListener = this
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listJenisKelamin)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerJenis!!.adapter = aa
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        div_tanggal_lahir_pengguna.setOnClickListener {
            DatePickerDialog(this,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        btnRegister.setOnClickListener {
            RetrofitClient.instance.daftarPasien(txtNamaPasien.text.toString().trim(),txtKKPasien.text.toString().trim(),
                txtNIKPAsien.text.toString().trim(),txtKepalaPasien.text.toString().trim(),txtNoHP.text.toString().trim(),tv_tanggal_lahir_pengguna.text.toString().trim(),
            spn_jk_pengguna.selectedItem.toString().trim(),txtAlamat.text.toString().trim(),"insert_pasien")
                .enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>?,
                        response: Response<DefaultResponse>?
                    ) {
                        if (response!!.body().status!! == 1){
                            txtKKPasien.setText("")
                            txtNamaPasien.setText("")
                            txtNIKPAsien.setText("")
                            txtNoHP.setText("")
                            txtKepalaPasien.setText("")
                            txtAlamat.setText("")
                            Toast.makeText(this@RegistrasiActivity, "Berhasil Mendaftar", Toast.LENGTH_SHORT)
                                .show()
                        }else{
                            Toast.makeText(this@RegistrasiActivity, "Gagal Mendaftar", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<DefaultResponse>?, t: Throwable?) {
                        Toast.makeText(this@RegistrasiActivity,"Response Gagal : ${t}", Toast.LENGTH_LONG).show()
                    }

                })
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        tv_jk_pengguna.text = "Jenis Kelamin"
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
    private fun updateDateInView(){
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tv_tanggal_lahir_pengguna!!.text = sdf.format(cal.getTime())
    }
}
