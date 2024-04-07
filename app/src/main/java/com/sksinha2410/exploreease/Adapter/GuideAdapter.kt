package com.sksinha2410.exploreease.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sksinha2410.exploreease.DataClass.Guide
import com.sksinha2410.exploreease.DataClass.Trips
import com.sksinha2410.exploreease.R

class GuideAdapter(options: FirebaseRecyclerOptions<Guide?>) :
    FirebaseRecyclerAdapter<Guide?, GuideAdapter.userAdapterHolder?>(options) {
    override fun onBindViewHolder(
        holder: userAdapterHolder,
        position: Int,
        model: Guide
    ) {
        Glide.with(holder.image.context).load(model.purl).into(holder.image)
        holder.name.text = model.name
        holder.contact.text = model.contact
        holder.location.text = model.location
        holder.language.text = model.language
        holder.description.text = model.description
        holder.price.text = model.price
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): userAdapterHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.guide_item,parent,false)
        return userAdapterHolder(view)
    }

    inner class userAdapterHolder(innerView: View): RecyclerView.ViewHolder(innerView) {
        var name: TextView
        var contact: TextView
        var location: TextView
        var language: TextView
        var description: TextView
        var price: TextView
        var image: ImageView
        init {
            name = innerView.findViewById(R.id.name)
            contact = innerView.findViewById(R.id.contact)
            location = innerView.findViewById(R.id.location)
            language = innerView.findViewById(R.id.language)
            description = innerView.findViewById(R.id.desc)
            price = innerView.findViewById(R.id.amount)
            image = innerView.findViewById(R.id.imageView)
        }
    }
}