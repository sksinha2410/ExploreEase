package com.sksinha2410.exploreease.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.ingray.samagam.Adapters.BlogAdapter
import com.sksinha2410.exploreease.DataClass.Blog
import com.sksinha2410.exploreease.R

class BagPackers_Fragment : Fragment() {
    private lateinit var view:View
    private lateinit var rvBlog: RecyclerView
    private lateinit var blogAdapter: BlogAdapter
    val dataBaseRef= FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_bag_packers, container, false)
        rvBlog=view.findViewById(R.id.rvBlog)
        rvBlog.itemAnimator = null
        val options: FirebaseRecyclerOptions<Blog?> = FirebaseRecyclerOptions.Builder<Blog>().
        setQuery(dataBaseRef.child("Blog"), Blog::class.java).build()
        blogAdapter = BlogAdapter(options)
        rvBlog.adapter = blogAdapter
        blogAdapter.startListening()
        return view
    }
}