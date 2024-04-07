package com.sksinha2410.exploreease.Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.PlacesClient
import com.sksinha2410.exploreease.R
import java.io.IOException


class DestinationMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var placesClient: PlacesClient
    private var fusedLocationClient: FusedLocationProviderClient? = null
    var C_Latitude = 0.0
    var C_Longitude:kotlin.Double = 0.0
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1
    var keyword = "atm"
    var radius = "1000"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_map)

        // Initialize Google Maps
        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as? SupportMapFragment
        if (mapFragment != null) {
            mapFragment.getMapAsync(this)
            Places.initialize(applicationContext,"@string/key");
            placesClient = Places.createClient(this)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        } else {
            // Handle the case where the fragment is not found
            // This could be due to incorrect ID or layout setup
        }

        // Initialize Places API
        Places.initialize(applicationContext, getString(R.string.map_api))
        placesClient = Places.createClient(this)

        // Check location permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                mMap.isMyLocationEnabled = true
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set default location (e.g., your city)
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        val defaultLocation = LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM))

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS
                )
            }
        } else {
            fusedLocationClient!!.lastLocation.addOnSuccessListener(
                this
            ) { location ->
                if (location != null) {
                    C_Latitude = location.latitude
                    C_Longitude = location.longitude
                    val CurrntLocation = LatLng(C_Latitude, C_Longitude)
                    val yourLocation = CameraUpdateFactory.newLatLngZoom(CurrntLocation, 19f) //zoom
                    mMap.addMarker(
                        MarkerOptions().position(CurrntLocation).title("Current Position")
                    ) //marker
                    mMap.animateCamera(yourLocation)
//                    GetNearbyPlace()
                }
            }
        }





        val intent: Intent = intent
        val query: String = intent.getStringExtra("query") ?: ""


        if (query.isNotEmpty()) {
            searchLocation(query)
        }
    }

    private fun searchLocation(query: String) {
        val token = AutocompleteSessionToken.newInstance()

        if (query != null && query.isNotEmpty()) {
            val location = query
            var addressList: List<Address>? = null
            val geocoder = Geocoder(this)
            try {
                addressList = geocoder.getFromLocationName(location, 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (addressList != null && addressList.isNotEmpty()) {
                val address = addressList[0]
                val latLng = LatLng(address.latitude, address.longitude)
                mMap.addMarker(MarkerOptions().position(latLng).title(location))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val DEFAULT_LATITUDE = 20.5937 // Default latitude
        private const val DEFAULT_LONGITUDE = 82.0 // Default longitude
        private const val DEFAULT_ZOOM = 4f // Default zoom level
    }
}
