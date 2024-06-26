package com.sksinha2410.exploreease.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.sksinha2410.exploreease.Adapter.TripsAdapter
import com.sksinha2410.exploreease.Activities.AddTripActivity
import com.sksinha2410.exploreease.DataClass.Trips
import com.sksinha2410.exploreease.R
import com.sksinha2410.exploreease.R.*

class ChatBot_Fragment : Fragment() {
    private lateinit var recTrip: RecyclerView
    private lateinit var ppAdapter: TripsAdapter
    var deRef = FirebaseDatabase.getInstance().getReference("Trips")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View =  inflater.inflate(layout.fragment_chat_bot_, container, false)
        val addTrip : FloatingActionButton = view.findViewById(R.id.btnAddTrip)
        recTrip = view.findViewById(R.id.recTrip)
        addTrip.setOnClickListener{
            val intent: Intent = Intent(activity, AddTripActivity::class.java)
            startActivity(intent)
        }
        recTrip.itemAnimator = null
        val options: FirebaseRecyclerOptions<Trips?> = FirebaseRecyclerOptions.Builder<Trips>().
        setQuery(deRef, Trips::class.java).build()
        ppAdapter = TripsAdapter(options)
        recTrip.adapter = ppAdapter
        ppAdapter.startListening()
        return view
    }
}