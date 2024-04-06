package com.sksinha2410.exploreease.Activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sksinha2410.exploreease.R

class SplashScreen_Activity : AppCompatActivity() {
    private lateinit var btnSignIn:TextView
    private lateinit var btnSignUp:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        btnSignIn = findViewById(R.id.btnSignIn)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnSignIn.setOnClickListener{
            val mainIntent = Intent(this, Login_Activity::class.java)
            startActivity(mainIntent)
            finish()
        }
        btnSignUp.setOnClickListener{
            val mainIntent = Intent(this, SignUp_Activity::class.java)
            startActivity(mainIntent)
            finish()
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = Firebase.auth.currentUser
        if(FirebaseAuth.getInstance().currentUser?.isEmailVerified == true){
            if (currentUser != null) {
                var intent:Intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(
                    applicationContext,
                    "Email not Verified",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }
}
