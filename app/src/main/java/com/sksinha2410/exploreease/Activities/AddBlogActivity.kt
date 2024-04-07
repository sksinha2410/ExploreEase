package com.sksinha2410.exploreease.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sksinha2410.exploreease.R
import java.net.Inet4Address

class AddBlogActivity : AppCompatActivity() {
    private lateinit var name:TextView
    private lateinit var contact: EditText
    private lateinit var location:EditText
    private lateinit var pincode:EditText
    private lateinit var description:EditText
    private lateinit var charge:EditText
    private lateinit var register: Button
    private lateinit var back: Button
    private lateinit var id: ImageView
    private var language:String = ""
    private var dref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Guide")
    private var duref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blog)
        register = findViewById(R.id.btnRegister)
        name = findViewById(R.id.name)
        location = findViewById(R.id.etAddress)
        description = findViewById(R.id.etDescription)
        back = findViewById(R.id.ivBack)
        id = findViewById(R.id.ivID)
        val currUser = FirebaseAuth.getInstance().currentUser?.uid

        duref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(currUser.toString())) {
                    name.text = snapshot.child(currUser.toString()).child("name").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AddBlogActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })

        register.setOnClickListener{
            val b:Boolean=checkAllTheConditions(contact.text.toString(),location.text.toString()
                ,pincode.text.toString(),description.text.toString())

            if(b) {
                val map: Map<String, Any> = mapOf(
                    "name" to name.text.toString(),
                    "contact" to contact.text.toString(),
                    "location" to location.text.toString(),
                    "pincode" to pincode.text.toString(),
                    "description" to description.text.toString(),
                    "language" to language,
                    "verified"  to "false",
                    "charge" to charge.text.toString()
                )

                if (currUser != null) {
                    dref.child(currUser).setValue(map)
                }
            }
        }
    }

    private fun checkAllTheConditions(contact:String, address:String,pincode:String,description: String):Boolean {
        if (TextUtils.isEmpty(contact)) {
            Toast.makeText(this, "Enter Contact No", Toast.LENGTH_SHORT).show()
        }else if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(pincode)) {
            Toast.makeText(this, "Enter Pincode", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Write work description", Toast.LENGTH_SHORT).show()
        }else{
            return true;
        }
        return false
    }
}