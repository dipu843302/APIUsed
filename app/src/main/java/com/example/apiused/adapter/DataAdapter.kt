package com.example.apiused.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apiused.R
import com.example.apiused.activity.ClickListener
import com.example.apiused.models.ResponseClass
import kotlinx.android.synthetic.main.item_layout.view.*

class DataAdapter(val list: MutableList<ResponseClass>,val clickListener: ClickListener) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(view,clickListener)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val responseClass = list[position]
        holder.setData(responseClass)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class DataViewHolder(val itemView: View,val clickListener: ClickListener) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("CheckResult")
        fun setData(responseClass: ResponseClass) {
            itemView.apply {

                Glide.with(this)
                    .load(responseClass.picture) // image url
                    .centerCrop()
                    .into(image as ImageView)  // imageview object

                firstName.text = responseClass.firstName

                linearLayout.setOnClickListener{
                    clickListener.userClickListener(responseClass)
                }
            }
        }

    }
}