package com.aplikasi.pusk_batang.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.pusk_batang.R
import com.aplikasi.pusk_batang.model.DataPoliItem

class PoliAdapter (val result  : ArrayList<DataPoliItem>) : RecyclerView.Adapter<PoliAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoliAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_custome, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PoliAdapter.ViewHolder, position: Int) {
        val data = result[position]
        holder.nameList.text = data.nama
        holder.descriptionList.visibility = View.GONE
//        holder.descriptionList.text = "---"
    }

    override fun getItemCount(): Int {
        return result.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameList: TextView = itemView.findViewById(R.id.tv_list_name)
        val descriptionList: TextView = itemView.findViewById(R.id.tv_list_description)
    }

    fun setData(data: List<DataPoliItem>) {
        result.clear()
        result.addAll(data)
        notifyDataSetChanged()
    }
}