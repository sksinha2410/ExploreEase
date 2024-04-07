package com.sksinha2410.exploreease.Activities

import android.annotation.SuppressLint
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
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sksinha2410.exploreease.DataClass.MapPlace
import com.sksinha2410.exploreease.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Calendar

class AddPlaceActivity : AppCompatActivity() {
    private lateinit var ivBack: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var hotelRadioButton: RadioButton
    private lateinit var templeRadioButton: RadioButton
    private lateinit var trekRadioButton: RadioButton
    private lateinit var touristPlaceRadioButton: RadioButton
    private lateinit var hindiRadioButton: RadioButton
    private lateinit var image: ImageView
    private lateinit var registerButton: Button
    private lateinit var radioGroup: RadioGroup
    private var selectedType = ""
    val Pick_image=1
    var storageReference = FirebaseStorage.getInstance().reference
     var purl:String=""
    val dataBaseRef= FirebaseDatabase.getInstance().reference.child("Places")


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)

        val intent = intent
        val lat = intent.getStringExtra("latitude")
        val lon = intent.getStringExtra("longitude")

        // Finding views by their IDs
        ivBack = findViewById(R.id.ivBack)
        nameEditText = findViewById(R.id.name)
        addressEditText = findViewById(R.id.etAddress)
        numberEditText = findViewById(R.id.etNumber)
        hotelRadioButton = findViewById(R.id.hotel)
        templeRadioButton = findViewById(R.id.temple)
        trekRadioButton = findViewById(R.id.trek)
        touristPlaceRadioButton = findViewById(R.id.trstPlace)
        hindiRadioButton = findViewById(R.id.activity)
        image = findViewById(R.id.ivID)
        registerButton = findViewById(R.id.btnRegister)
        radioGroup = findViewById(R.id.radioGroup)

        // Now you can use these views throughout the activity

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Handle the checked state of RadioButtons
            when (checkedId) {
                R.id.hotel -> {
                    // Handle hotel RadioButton
                    selectedType = "Hotel"
                }
                R.id.temple -> {
                    // Handle temple RadioButton
                    selectedType = "Temple"
                }
                R.id.trek -> {
                    // Handle trek RadioButton
                    selectedType = "Trek"
                }
                R.id.trstPlace -> {
                    // Handle tourist place RadioButton
                    selectedType = "Tourist Place"
                }
                R.id.activity -> {
                    // Handle Hindi RadioButton
                    selectedType = "Activities/Adventure"
                }
            }


        }
        image = findViewById(R.id.ivID)


        image.setOnClickListener{
            openGallery()
        }


        registerButton.setOnClickListener {


            val MapPlace:MapPlace = MapPlace()
            MapPlace.placeName = nameEditText.text.toString()
            MapPlace.placeAddress = addressEditText.text.toString()
            MapPlace.placePhone = numberEditText.text.toString()
            MapPlace.placeType = selectedType

            val calendar = Calendar.getInstance().timeInMillis.toString()
            MapPlace.placeId = calendar
            MapPlace.placePhoto = purl
            MapPlace.placeLatitude = lat.toString()
            MapPlace.placeLongitude = lon.toString()

            dataBaseRef.child(calendar).setValue(MapPlace)
            finish()

        }
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
            storageReference.child("PlacesImage/" + Calendar.getInstance().timeInMillis.toString() + "blogImage.jpg")

        // Load the image into a Bitmap
        val bitmap: Bitmap
        try {
            // Assuming imageUri is a valid URI
            val source = ImageDecoder.createSource(this.contentResolver, imageUri)
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
