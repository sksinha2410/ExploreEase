package com.sksinha2410.exploreease.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sksinha2410.exploreease.R

class BeGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var register: Button

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_be_guide)
        register = findViewById(R.id.registerBtn)
        register.setOnClickListener {
            val i = Intent(applicationContext,GuideRegister_Activity::class.java)
            startActivity(i)
        }
    }
}