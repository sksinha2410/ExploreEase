package com.sksinha2410.exploreease.Activities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.sksinha2410.exploreease.DataClass.Users
import com.sksinha2410.exploreease.R

class SignUp_Activity : AppCompatActivity() {
    //Initialising all the variables
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var name: EditText
    private lateinit var signUp: Button
    private lateinit var logIn: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var sEmail:String
    private lateinit var sPass:String
    private lateinit var ivBack: ImageView
    private lateinit var cPass:String
    private lateinit var sName:String
    private var dref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        callVariablesByIds()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        signUp.setOnClickListener {
            sEmail = email.text.toString()
            sPass = password.text.toString()
            sName = name.text.toString()


            val b:Boolean=checkAllTheConditions(sEmail,sPass,cPass,sName)

            if (b){
                auth.createUserWithEmailAndPassword(sEmail,sPass).
                addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        user?.sendEmailVerification()?.addOnCompleteListener(
                            OnCompleteListener<Void?> { tasks ->
                                if (tasks.isSuccessful) {
                                    var currUser = FirebaseAuth.getInstance().currentUser?.uid
                                    var data= Users()
                                    data.name=sName
                                    data.email=sEmail
                                    data.userType="0"
                                    if (currUser != null) {
                                        dref.child(currUser).setValue(data)
                                    }

                                    Toast.makeText(
                                        applicationContext,
                                        "Registration successful! Check your email for verification link",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent: Intent = Intent(
                                        this,
                                        Login_Activity::class.java
                                    )
                                    intent.putExtra("email",sEmail)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        "Failed to send verification link",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "User Already Registered",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }

        logIn.setOnClickListener{
            var intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }

        ivBack.setOnClickListener{
            val i = Intent(applicationContext,SplashScreen_Activity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun checkAllTheConditions(email:String, password:String,confirmPass:String,name: String):Boolean {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show()
        }else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6) {
            Toast.makeText(this, "Password should be atleast 6 characters", Toast.LENGTH_SHORT).show()
        }else{
            return true;
        }
        return false
    }

    private fun callVariablesByIds() {
        name = findViewById(R.id.etName)
        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        logIn = findViewById(R.id.tvLogIn)
        signUp= findViewById(R.id.btnSignUp)
        auth = Firebase.auth
        ivBack= findViewById(R.id.ivBack)
    }
}