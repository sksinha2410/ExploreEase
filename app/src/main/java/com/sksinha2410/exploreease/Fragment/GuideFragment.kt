package com.sksinha2410.exploreease.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sksinha2410.exploreease.Activities.AddBlogActivity
import com.sksinha2410.exploreease.Activities.BeGuideActivity
import com.sksinha2410.exploreease.R

class GuideFragment : Fragment() {
    private lateinit var view :View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_guide, container, false)
        val addBlog: FloatingActionButton = view.findViewById(R.id.btnAdd)
        addBlog.setOnClickListener{
            val intent: Intent = Intent(activity, BeGuideActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}