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
import com.ingray.samagam.Adapters.BlogAdapter
import com.sksinha2410.exploreease.Activities.AddBlogActivity
import com.sksinha2410.exploreease.Activities.AddTripActivity
import com.sksinha2410.exploreease.DataClass.Blog
import com.sksinha2410.exploreease.R

class BagPackers_Fragment : Fragment() {
    private lateinit var view:View
    private lateinit var rvBlog: RecyclerView
    private lateinit var blogAdapter: BlogAdapter
    val dataBaseRef= FirebaseDatabase.getInstance().getReference("Blogs")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_bag_packers, container, false)
        rvBlog=view.findViewById(R.id.rvBlog)
         val addBlog: FloatingActionButton = view.findViewById(R.id.btnAddBlog)

        try {
            rvBlog.itemAnimator = null
            val options: FirebaseRecyclerOptions<Blog?> = FirebaseRecyclerOptions.Builder<Blog>().
            setQuery(dataBaseRef, Blog::class.java).build()
            blogAdapter = BlogAdapter(options)
            rvBlog.adapter = blogAdapter
            blogAdapter.startListening()
        }
        catch(e:Exception){}

        addBlog.setOnClickListener{
            val intent: Intent = Intent(activity, AddBlogActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}