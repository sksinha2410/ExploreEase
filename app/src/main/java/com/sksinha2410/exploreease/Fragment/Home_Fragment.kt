package com.sksinha2410.exploreease.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ingray.samagam.Adapters.BlogAdapter
import com.sksinha2410.exploreease.Activities.DestinationMapActivity
import com.sksinha2410.exploreease.Activities.MainActivity
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
    private lateinit var ivMap: ImageView
    private lateinit var etSearch: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view= inflater.inflate(R.layout.fragment_home_, container, false)
        callById()
        val dataBaseRef= FirebaseDatabase.getInstance().reference

        ivMap.setOnClickListener{
            val intent= Intent(activity, DestinationMapActivity::class.java)
            intent.putExtra("query",etSearch.text.toString())
            startActivity(intent)
        }

        rvBlog.itemAnimator = null
        val options: FirebaseRecyclerOptions<Blog?> = FirebaseRecyclerOptions.Builder<Blog>().
        setQuery(dataBaseRef.child("Blogs"), Blog::class.java).build()
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
        ivMap=view.findViewById(R.id.ivMap)
        etSearch=view.findViewById(R.id.etSearch)
    }
}