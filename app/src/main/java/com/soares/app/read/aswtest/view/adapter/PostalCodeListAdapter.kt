package com.soares.app.read.aswtest.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soares.app.read.aswtest.model.PostalCode

class PostalCodeListAdapter(private val listPostalCode: ArrayList<PostalCode>) : RecyclerView.Adapter<PostalCodeViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostalCodeViewHolder {
        return PostalCodeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostalCodeViewHolder, position: Int) {
        val current = listPostalCode[position]
        holder.bind(current)
    }

    override fun getItemCount(): Int {
       return listPostalCode.size
    }
}