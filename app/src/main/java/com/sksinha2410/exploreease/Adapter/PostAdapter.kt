package com.ingray.samagam.Adapters

import android.app.AlertDialog
import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.sksinha2410.exploreease.DataClass.Posts
import com.sksinha2410.exploreease.R

class PostAdapter (options: FirebaseRecyclerOptions<Posts?>) :
    FirebaseRecyclerAdapter<Posts?, PostAdapter .userAdapterHolder?>(options) {

    override fun onBindViewHolder(
        holder: userAdapterHolder,
        position: Int,
        model: Posts
    ) {
        Glide.with(holder.posterImage.context).load(model.postUrl).into(holder.posterImage)
        holder.likes.setText(model.likes)
        holder.delete.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(holder.delete.context)
            builder
                .setTitle("Do You surely want to delete?")
                .setPositiveButton("Yes") { dialog, which ->
                    FirebaseDatabase.getInstance().reference.child("Users").child(
                        FirebaseAuth.getInstance().currentUser?.uid.toString()
                    ).child("Posts").child(model.postId).removeValue()

                    FirebaseDatabase.getInstance().reference.child("Posts").child(model.postId).removeValue()

                    Toast.makeText(holder.delete.context,"Post Deleted", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("No") { dialog, which ->
                    // Do something else.
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): userAdapterHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.profile_post_item,parent,false)
        return userAdapterHolder(view)
    }

    inner class userAdapterHolder(innerView:View):RecyclerView.ViewHolder(innerView) {
        var posterImage:ImageView
        var likes:TextView
        var delete:CardView

        init {
            posterImage =innerView.findViewById(R.id.image_post)
            likes =innerView.findViewById(R.id.likes)
            delete=innerView.findViewById(R.id.delete)
        }
    }
}