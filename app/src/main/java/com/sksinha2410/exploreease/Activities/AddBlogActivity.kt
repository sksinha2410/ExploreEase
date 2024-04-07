package com.sksinha2410.exploreease.Activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sksinha2410.exploreease.DataClass.Blog
import com.sksinha2410.exploreease.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Calendar

class AddBlogActivity : AppCompatActivity() {
    private lateinit var name:TextView
    private lateinit var image:ImageView
    private lateinit var location:EditText
    private lateinit var description:EditText
    private lateinit var register: Button
    private lateinit var back: ImageView
    private lateinit var blogs: Blog
    val Pick_image=1
    var storageReference = FirebaseStorage.getInstance().reference
    lateinit var purl:String
    val dataBaseRef= FirebaseDatabase.getInstance().reference.child("Blogs")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blog)
        blogs= Blog()
        back = findViewById(R.id.ivBack)
        name = findViewById(R.id.name)
        location = findViewById(R.id.etAddress)
        description = findViewById(R.id.etDescription)
        image = findViewById(R.id.image)
        register = findViewById(R.id.btnRegister)
        image.setOnClickListener{
            openGallery()
        }
        back.setOnClickListener{
            finish()
        }

        register.setOnClickListener{
            if (name.text.toString().isEmpty() || location.text.toString().isEmpty() || description.text.toString().isEmpty() ||
                purl.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                addBlog()
            }
        }
    }

    private fun addBlog() {
        blogs.blogAuthor = name.text.toString()
        blogs.blogAddress = location.text.toString()
        blogs.blogDescription = description.text.toString()
        blogs.blogImage = purl
        val calendar = Calendar.getInstance().timeInMillis.toString()
        blogs.blogId = calendar
        dataBaseRef.child(calendar).setValue(blogs)
        Toast.makeText(this, "Blog Added", Toast.LENGTH_SHORT).show()
        finish()
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
            storageReference.child("BlogImage/" + Calendar.getInstance().timeInMillis.toString() + "blogImage.jpg")

        // Load the image into a Bitmap
        val bitmap: Bitmap
        try {
            // Assuming imageUri is a valid URI
            val source = ImageDecoder.createSource(this@AddBlogActivity.contentResolver, imageUri)
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