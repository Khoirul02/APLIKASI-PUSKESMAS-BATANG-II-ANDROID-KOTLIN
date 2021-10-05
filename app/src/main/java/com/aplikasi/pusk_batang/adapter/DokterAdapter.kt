package com.aplikasi.pusk_batang.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.pusk_batang.R
import com.aplikasi.pusk_batang.model.DataDokterItem

class DokterAdapter (val result  : ArrayList<DataDokterItem>) : RecyclerView.Adapter<DokterAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DokterAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_custome, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DokterAdapter.ViewHolder, position: Int) {
        val data = result[position]
        holder.nameList.text = data.nama
        holder.descriptionList.text = data.bidang + " / Status : ${data.status}"
    }

    override fun getItemCount(): Int {
        return result.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameList : TextView = itemView.findViewById(R.id.tv_list_name)
        val descriptionList: TextView = itemView.findViewById(R.id.tv_list_description)
    }
    fun setData(data: List<DataDokterItem>) {
        result.clear()
        result.addAll(data)
        notifyDataSetChanged()
    }
}