package com.sksinha2410.exploreease.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ingray.samagam.Adapters.BlogAdapter
import com.sksinha2410.exploreease.Adapter.DestinationAdapter
import com.sksinha2410.exploreease.DataClass.Blog
import com.sksinha2410.exploreease.DataClass.Destination
import com.sksinha2410.exploreease.R

class Home_Fragment : Fragment() {
    private lateinit var view:View
    private lateinit var recyclerDestination: RecyclerView
    private lateinit var rvBlog: RecyclerView
    private lateinit var blogAdapter: BlogAdapter
    private lateinit var destinationAdapter: DestinationAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view= inflater.inflate(R.layout.fragment_home_, container, false)
        callById()
        val dataBaseRef= FirebaseDatabase.getInstance().reference

        rvBlog.itemAnimator = null
        val options: FirebaseRecyclerOptions<Blog?> = FirebaseRecyclerOptions.Builder<Blog>().
        setQuery(dataBaseRef.child("Blog"), Blog::class.java).build()
        blogAdapter = BlogAdapter(options)
        rvBlog.adapter = blogAdapter
        blogAdapter.startListening()

        recyclerDestination.itemAnimator = null
        val option: FirebaseRecyclerOptions<Destination?> = FirebaseRecyclerOptions.Builder<Destination>().
        setQuery(dataBaseRef.child("Destination"), Destination::class.java).build()
        destinationAdapter = DestinationAdapter(option)
        recyclerDestination.adapter = destinationAdapter
        destinationAdapter.startListening()

        return view
    }

    private fun callById() {
        recyclerDestination=view.findViewById(R.id.rvTopDestinations)
        rvBlog=view.findViewById(R.id.rvBlog)
    }
}