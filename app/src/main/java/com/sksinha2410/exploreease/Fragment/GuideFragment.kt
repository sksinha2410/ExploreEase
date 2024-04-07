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
import com.sksinha2410.exploreease.Activities.BeGuideActivity
import com.sksinha2410.exploreease.Adapter.GuideAdapter
import com.sksinha2410.exploreease.DataClass.Guide
import com.sksinha2410.exploreease.DataClass.Trips
import com.sksinha2410.exploreease.R

class GuideFragment : Fragment() {
    private lateinit var view :View
    private lateinit var tvGuide : RecyclerView
    private lateinit var ppAdapter: GuideAdapter
    var deRef = FirebaseDatabase.getInstance().getReference("Guides")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_guide, container, false)
        val addBlog: FloatingActionButton = view.findViewById(R.id.btnAdd)
        tvGuide = view.findViewById(R.id.rvGuide)
        addBlog.setOnClickListener{
            val intent: Intent = Intent(activity, BeGuideActivity::class.java)
            startActivity(intent)
        }
        tvGuide.itemAnimator = null
        val options: FirebaseRecyclerOptions<Guide?> = FirebaseRecyclerOptions.Builder<Guide>().
        setQuery(deRef, Guide::class.java).build()
        ppAdapter = GuideAdapter(options)
        tvGuide.adapter = ppAdapter
        ppAdapter.startListening()

        return view
    }
}