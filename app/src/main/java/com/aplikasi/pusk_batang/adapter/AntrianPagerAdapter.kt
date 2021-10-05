package com.aplikasi.pusk_batang.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aplikasi.pusk_batang.fragment.AntrianHariIniFragment
import com.aplikasi.pusk_batang.fragment.RiwayatAntrianFragment

@Suppress("DEPRECATION")
class AntrianPagerAdapter (fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val pages = listOf(
        AntrianHariIniFragment(),
        RiwayatAntrianFragment()
    )

    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Hari Ini"
            else -> "Riwayat"
        }
    }
}