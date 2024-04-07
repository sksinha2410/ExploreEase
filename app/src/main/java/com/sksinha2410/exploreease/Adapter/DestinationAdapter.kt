package com.sksinha2410.exploreease.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.sksinha2410.exploreease.DataClass.Destination
import com.sksinha2410.exploreease.R


class DestinationAdapter(options: FirebaseRecyclerOptions<Destination?>) :
        FirebaseRecyclerAdapter<Destination?, DestinationAdapter.userAdapterHolder?>(options) {

        override fun onBindViewHolder(
            holder: userAdapterHolder,
            position: Int,
            model: Destination
        ) {
            Glide.with(holder.image.context).load(model.imageUrl).into(holder.image)
            holder.address.text = model.address

        }
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): userAdapterHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.destination_item,parent,false)
            return userAdapterHolder(view)
        }

        inner class userAdapterHolder(innerView: View): RecyclerView.ViewHolder(innerView) {
            var image: ImageView = innerView.findViewById(R.id.image)
            var address: TextView = innerView.findViewById(R.id.address)
        }
    }
