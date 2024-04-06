package com.sksinha2410.exploreease.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.firebase.ui.database.FirebaseRecyclerAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ingray.samagam.Adapters.PostAdapter
import com.sksinha2410.exploreease.DataClass.Posts
import com.sksinha2410.exploreease.R
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.IOException

class Profile_Fragment : Fragment() {
    private lateinit var profileImage : CircleImageView
    private lateinit var profileName : TextView
    private lateinit var About : TextView
    var deRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var view :View
    private lateinit var recyclerPost: RecyclerView
    val Pick_image=1
    private lateinit var ppAdapter: PostAdapter
    var storageReference = FirebaseStorage.getInstance().reference
    lateinit var purl:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile_, container, false)

        callById()

        recyclerPost.itemAnimator = null
        val options: FirebaseRecyclerOptions<Posts?> = FirebaseRecyclerOptions.Builder<Posts>().
        setQuery(deRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("Posts"), Posts::class.java).build()
        ppAdapter = PostAdapter(options)
        recyclerPost.adapter = ppAdapter
        ppAdapter.startListening()
        profileImage.setOnClickListener{
            openGallery()
        }
        return view
    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Pick_image && resultCode == Activity.RESULT_OK && data != null) {
            val resultUri: Uri = data.data!!
            uploadImageToFirebase(resultUri)
            profileImage.setImageURI(resultUri)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun uploadImageToFirebase(imageUri: Uri) {
        val fileRef: StorageReference =
            storageReference.child("users/" + FirebaseAuth.getInstance().currentUser?.uid + "profile.jpg")

        // Load the image into a Bitmap
        val bitmap: Bitmap
        try {
            // Assuming imageUri is a valid URI
            val source = ImageDecoder.createSource(requireContext().contentResolver, imageUri)
            bitmap = ImageDecoder.decodeBitmap(source)
            // Use the bitmap here...
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }

// Compress the image with reduced quality (adjust quality as needed)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos) // Adjust the quality here (50 in this example)

        // Convert the compressed Bitmap to bytes
        val data = baos.toByteArray()

        // Upload the compressed image to Firebase Storage
        val uploadTask = fileRef.putBytes(data)
        uploadTask.addOnSuccessListener { // Handle the successful upload
            fileRef.downloadUrl.addOnSuccessListener { uri ->
                purl = uri.toString()
                val users: MutableMap<String, Any> =
                    HashMap()

                users["purl"] = purl
                try {
                    if (purl != null) {
                        deRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
                            .updateChildren(users)
                    }
                } catch (e: Exception) {
                }
                Glide.with(view.context).load(purl).into(profileImage)
            }
        }.addOnFailureListener { // Handle the failure to upload
            Toast.makeText(view.context, "Failed.", Toast.LENGTH_LONG).show()
        }
    }

    private fun openGallery() {
        val gallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(gallery,Pick_image)
    }
    private fun callById() {
        profileImage = view.findViewById(R.id.ivProfile)
        About = view.findViewById(R.id.tvAbout)
        profileName = view.findViewById(R.id.tvName)
        recyclerPost = view.findViewById(R.id.profile_recycler)
    }
}