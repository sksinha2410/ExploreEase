package com.ingray.samagam.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.sksinha2410.exploreease.DataClass.BlogData
import com.sksinha2410.exploreease.R

class BlogAdapter(options: FirebaseRecyclerOptions<BlogData?>) :
    FirebaseRecyclerAdapter<BlogData?, BlogAdapter .userAdapterHolder?>(options) {

    override fun onBindViewHolder(
        holder: userAdapterHolder,
        position: Int,
        model: BlogData
    ) {
        Glide.with(holder.image.context).load(model.blogImage).into(holder.image)
        holder.userName.text = model.blogAuthor
        holder.address.text = model.blogAddress
        holder.description.text = model.blogDescription
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): userAdapterHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.blog_item,parent,false)
        return userAdapterHolder(view)
    }

    inner class userAdapterHolder(innerView:View):RecyclerView.ViewHolder(innerView) {
        var address: TextView = innerView.findViewById(R.id.address)
        var userName: TextView = innerView.findViewById(R.id.userName)
        var image: ImageView = innerView.findViewById(R.id.image)
        var description: TextView = innerView.findViewById(R.id.description)
    }
}