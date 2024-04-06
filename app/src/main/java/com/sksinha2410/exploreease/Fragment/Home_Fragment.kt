package com.sksinha2410.exploreease.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sksinha2410.exploreease.R

class Home_Fragment : Fragment() {
    private lateinit var view:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view= inflater.inflate(R.layout.fragment_home_, container, false)



        return view
    }

}