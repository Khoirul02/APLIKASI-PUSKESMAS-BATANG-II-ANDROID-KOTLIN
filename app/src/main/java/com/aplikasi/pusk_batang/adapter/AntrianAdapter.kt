package com.aplikasi.pusk_batang.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aplikasi.pusk_batang.R
import com.aplikasi.pusk_batang.model.DataAntrianItem
import java.text.DateFormat
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AntrianAdapter (val result  : ArrayList<DataAntrianItem>) : RecyclerView.Adapter<AntrianAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AntrianAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_antrian, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: AntrianAdapter.ViewHolder, position: Int) {
        val data = result[position]
        holder.poliList.text = data.nama
        val create_at = data.createAtAntrian
        val dateRegistrasiAntrian = create_at!!.substring(0,10)
        val dateFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val tglAntrian: String = dateFormat.format(df.parse(dateRegistrasiAntrian))
        holder.nameTanggalList.text = "Periksa $tglAntrian"
        holder.noAntrianList.text = "No Antrian - ${data.noAntrian}"
    }

    override fun getItemCount(): Int {
        return result.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val poliList : TextView = itemView.findViewById(R.id.tv_list_poli)
        val nameTanggalList : TextView = itemView.findViewById(R.id.tv_list_tanggal_pasien)
        val noAntrianList: TextView = itemView.findViewById(R.id.tv_list_no_antrian)
    }
    fun setData(data: List<DataAntrianItem>) {
        result.clear()
        result.addAll(data)
        notifyDataSetChanged()
    }
}