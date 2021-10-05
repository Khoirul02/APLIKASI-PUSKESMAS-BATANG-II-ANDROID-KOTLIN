package com.aplikasi.pusk_batang.dashboard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.aplikasi.pusk_batang.MainActivity
import com.aplikasi.pusk_batang.R
import com.aplikasi.pusk_batang.activity.AntrianActivity
import com.aplikasi.pusk_batang.activity.DokterActivity
import com.aplikasi.pusk_batang.activity.PoliActivity
import com.aplikasi.pusk_batang.activity.PopUpAmbilAntrianActivity
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar!!.hide()
        ll_lihat_antrian.setOnClickListener {
            val intent = Intent(this, AntrianActivity::class.java)
            startActivity(intent)
        }
        ll_lihat_dokter.setOnClickListener {
            val intent = Intent(this, DokterActivity::class.java)
            startActivity(intent)
        }
        ll_lihat_poli.setOnClickListener {
            val intent = Intent(this, PoliActivity::class.java)
            startActivity(intent)
        }
        btnAmbilAntrian.setOnClickListener {
            val intent = Intent(this, PopUpAmbilAntrianActivity::class.java)
            intent.putExtra("darkstatusbar", false)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Booking")
            setMessage("Apakah kamu mau logout dari aplikasi?")
            setPositiveButton("Ya") { _, _ ->
                // if user press yes, then finish the current activity
//                super.onBackPressed()
                logout(this@DashboardActivity)
            }
            setNegativeButton("Tidak"){_, _ ->
                // if user press no, then return the activity
                Toast.makeText(this@DashboardActivity, "Batal",
                    Toast.LENGTH_LONG).show()
            }
            setCancelable(true)
        }.create().show()
    }
    fun logout(context: Context) {
        val sharedPreferences = context.getSharedPreferences("PUSK_BATANG", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor!!.putString("ID", "")
        editor.putString("NAMA", "")
        editor.putString("NO_KK", "")
        editor.putString("NIK", "")
        editor.putString("NOHP", "")
        editor.putString("TGL_LAHIR", "")
        editor.putString("JENKEL", "")
        editor.putString("ALAMAT", "")
        editor.apply()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}
