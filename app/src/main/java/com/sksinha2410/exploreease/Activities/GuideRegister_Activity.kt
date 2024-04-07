package com.sksinha2410.exploreease.Activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sksinha2410.exploreease.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.Inet4Address
import java.util.Calendar

class GuideRegister_Activity : AppCompatActivity() {
    private lateinit var name:TextView
    private lateinit var contact: EditText
    private lateinit var location:EditText
    private lateinit var pincode:EditText
    private lateinit var description:EditText
    private lateinit var charge:EditText
    private lateinit var register: Button
    private lateinit var back: Button
    private lateinit var image: ImageView
    private var language:String = ""
    val Pick_image=1
    var storageReference = FirebaseStorage.getInstance().reference
    lateinit var purl:String
    private var dref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Guide")
    private var duref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")


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
        image = findViewById(R.id.ivID)
        charge = findViewById(R.id.etCharge)
        val currUser = FirebaseAuth.getInstance().currentUser?.uid

        duref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(currUser.toString())) {
                    name.text = snapshot.child(currUser.toString()).child("name").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@GuideRegister_Activity, "Error", Toast.LENGTH_SHORT).show()
            }
        })


        val radioButton1: RadioButton = findViewById(R.id.hindi)
        val radioButton2: RadioButton = findViewById(R.id.english)
        image.setOnClickListener{
            openGallery()
        }

        // Listen for changes in the selected radio button

        register.setOnClickListener{
            if (radioButton1.isChecked) {
                language+=radioButton1.text.toString()+","
            }
            if (radioButton2.isChecked) {
                language+=radioButton2.text.toString()+","
            }
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
                    "charge" to charge.text.toString(),
                    "image" to purl
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




    private fun openGallery() {
        val gallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(gallery,Pick_image)

    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Pick_image && resultCode == RESULT_OK && data != null) {
            val resultUri: Uri = data.data!!
            uploadImageToFirebase(resultUri)

            image.setImageURI(resultUri)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun uploadImageToFirebase(imageUri: Uri) {
        val fileRef: StorageReference =
            storageReference.child("GuideIDs/" + Calendar.getInstance().timeInMillis.toString() + "GuideID.jpg")

        // Load the image into a Bitmap
        val bitmap: Bitmap
        try {
            // Assuming imageUri is a valid URI
            val source = ImageDecoder.createSource(contentResolver, imageUri)
            bitmap = ImageDecoder.decodeBitmap(source)
            // Use the bitmap here...
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }

// Compress the image with reduced quality (adjust quality as needed)
        val baos = ByteArrayOutputStream()
        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            60,
            baos
        ) // Adjust the quality here (50 in this example)

        // Convert the compressed Bitmap to bytes
        val data = baos.toByteArray()

        // Upload the compressed image to Firebase Storage
        val uploadTask = fileRef.putBytes(data)
        uploadTask.addOnSuccessListener { // Handle the successful upload
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                purl = uri.toString()

                Glide.with(applicationContext).load(purl).into(image)
            }
        }.addOnFailureListener { // Handle the failure to upload
            Toast.makeText(applicationContext, "Failed.", Toast.LENGTH_LONG).show()
        }
    }
}