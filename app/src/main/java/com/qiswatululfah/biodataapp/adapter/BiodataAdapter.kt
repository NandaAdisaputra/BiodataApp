package com.qiswatululfah.biodataapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qiswatululfah.biodataapp.room.BiodataModel
import com.qiswatululfah.biodataapp.activity.DetailBiodataActivity
import com.qiswatululfah.biodataapp.R
import kotlinx.android.synthetic.main.item_biodata.view.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class BiodataAdapter (var list: List<BiodataModel>?) : RecyclerView.Adapter<BiodataAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView.tv_name.text = list?.get(position)?.name

            itemView.onClick {
                itemView.context.startActivity(itemView.context.intentFor<DetailBiodataActivity>("BIODATA" to list?.get(position)))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_biodata, parent, false))
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setListOfBiodata(list: List<BiodataModel>?){
        this.list = list
        notifyDataSetChanged()
    }

}