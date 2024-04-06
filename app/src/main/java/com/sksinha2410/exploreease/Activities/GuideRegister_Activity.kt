package com.sksinha2410.exploreease.Activities

import android.os.Bundle
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
import com.sksinha2410.exploreease.R

class GuideRegister_Activity : AppCompatActivity() {
    private lateinit var name:TextView
    private lateinit var contact: EditText
    private lateinit var location:EditText
    private lateinit var pincode:EditText
    private lateinit var description:EditText
    private lateinit var register: Button
    private lateinit var back: Button
    private lateinit var id: ImageView
    private var language:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_register)
        register = findViewById(R.id.btnRegister)
        name = findViewById(R.id.name)
        contact = findViewById(R.id.etContact)
        location = findViewById(R.id.etAddress)
        pincode = findViewById(R.id.etPinCode)
        description = findViewById(R.id.etDescription)
        back = findViewById(R.id.ivBack)
        id = findViewById(R.id.ivID)


        val radioButton1: RadioButton = findViewById(R.id.hindi)
        val radioButton2: RadioButton = findViewById(R.id.english)

        // Listen for changes in the selected radio button

        register.setOnClickListener{
            if (radioButton1.isChecked) {
                language+=radioButton1.text.toString()+","
            }
            if (radioButton2.isChecked) {
                language+=radioButton2.text.toString()+","
            }

            var map:Map<String,Any> = mapOf(
                "name" to name.text.toString(),
                "contact" to contact.text.toString(),
                "location" to location.text.toString(),
                "pincode" to pincode.text.toString(),
                "description" to description.text.toString(),
                "language" to language
            )
        }


    }


}