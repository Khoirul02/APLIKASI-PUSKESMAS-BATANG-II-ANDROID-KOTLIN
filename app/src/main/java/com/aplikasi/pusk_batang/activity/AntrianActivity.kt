package com.aplikasi.pusk_batang.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aplikasi.pusk_batang.R
import com.aplikasi.pusk_batang.adapter.AntrianPagerAdapter
import kotlinx.android.synthetic.main.activity_antrian.*

class AntrianActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_antrian)
        sharedPreferences = getSharedPreferences("PUSK_BATANG", Context.MODE_PRIVATE)
        val nama: String? = sharedPreferences.getString("NAMA", "")
        supportActionBar!!.setTitle("Hai, Antrian ${nama} ")
        viewpager_main.adapter = AntrianPagerAdapter(supportFragmentManager)
        tab_main.setupWithViewPager(viewpager_main)
    }
}
