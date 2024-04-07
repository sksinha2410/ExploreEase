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
import com.google.firebase.database.ValueEventListener
import com.sksinha2410.exploreease.DataClass.Posts
import com.sksinha2410.exploreease.DataClass.Trips
import com.sksinha2410.exploreease.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TripsAdapter (options: FirebaseRecyclerOptions<Trips?>) :
    FirebaseRecyclerAdapter<Trips?, TripsAdapter .userAdapterHolder?>(options) {
        val dRef = FirebaseDatabase.getInstance().getReference("Trips")
    override fun onBindViewHolder(
        holder: userAdapterHolder,
        position: Int,
        model: Trips
    ) {

        dRef.child(model.tripId).child("People").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                holder.people.text = snapshot.childrenCount.toString()+"/"+model.people
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(holder.people.context, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        holder.location.text = model.tour_location
        holder.amount.text = model.budget.plus("₹ per person")
        holder.description.text = model.details
        Toast.makeText(holder.date.context, model.to, Toast.LENGTH_SHORT).show()
        val fromdate = model.from
        val datefrom = fromdate.substring(8,10)+fromdate.substring(4,8)+fromdate.substring(0,4)
        val todate = model.to
        val dateto = todate.substring(8,10)+todate.substring(4,8)+todate.substring(0,4)


        holder.date.text = datefrom.plus(" to ").plus(dateto)






    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): userAdapterHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.group_trip_item,parent,false)
        return userAdapterHolder(view)
    }

    inner class userAdapterHolder(innerView:View):RecyclerView.ViewHolder(innerView) {
        var date:TextView
        var people:TextView
        var location:TextView
        var amount:TextView
        var description:TextView
        var cardView:CardView

        init {
            date =innerView.findViewById(R.id.date)
            people =innerView.findViewById(R.id.people)
            location=innerView.findViewById(R.id.location)
            amount =innerView.findViewById(R.id.amount)
            description =innerView.findViewById(R.id.description)
            cardView =innerView.findViewById(R.id.cardView)
        }
    }
}