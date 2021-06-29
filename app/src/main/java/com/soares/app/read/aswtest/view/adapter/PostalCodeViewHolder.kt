package com.soares.app.read.aswtest.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.soares.app.read.aswtest.R
import com.soares.app.read.aswtest.model.PostalCode


class PostalCodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var txtPostalCode: TextView = itemView.findViewById(R.id.txtPostalCode)
    private var txtDesc: TextView = itemView.findViewById(R.id.txtDesc)

    fun bind(postalCode: PostalCode) {
        txtPostalCode.text = postalCode.postalCode
        txtDesc.text = postalCode.desc
    }

    companion object {
        fun create(parent: ViewGroup): PostalCodeViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
            return PostalCodeViewHolder(view)
        }
    }
}