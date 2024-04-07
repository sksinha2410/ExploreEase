package com.sksinha2410.exploreease.Activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase
import com.sksinha2410.exploreease.DataClass.Trips
import com.sksinha2410.exploreease.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTripActivity : AppCompatActivity() {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private lateinit var start_date: TextView
    private lateinit var end_date: TextView
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etContact: EditText
    private lateinit var etAddress: EditText
    private lateinit var tourLocation: EditText
    private lateinit var budget: EditText
    private lateinit var people: EditText
    private lateinit var details: EditText
    private lateinit var trip:Trips
    private lateinit var button: Button
    val dRef = FirebaseDatabase.getInstance().getReference("Trips")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trip)
        trip = Trips()
        start_date = findViewById(R.id.startdate)
        end_date = findViewById(R.id.enddate)
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        details = findViewById(R.id.etDescription)
        etContact = findViewById(R.id.etContact)
        etAddress = findViewById(R.id.etAddress)
        tourLocation = findViewById(R.id.tourLocation)
        budget = findViewById(R.id.budget)
        people = findViewById(R.id.people)
        button = findViewById(R.id.btnRegister)
        start_date.setOnClickListener{
            showDatePickerDialog("start")
        }
        end_date.setOnClickListener{
            showDatePickerDialog("end")
        }
        button.setOnClickListener {
            trip.name = etName.text.toString()
            trip.age = etAge.text.toString()
            trip.contact = etContact.text.toString()
            trip.address = etAddress.text.toString()
            trip.tour_location = tourLocation.text.toString()
            trip.budget = budget.text.toString()
            trip.people = people.text.toString()
            trip.from = start_date.text.toString()
            trip.to = end_date.text.toString()
            trip.details = details.text.toString()

            val calendar = Calendar.getInstance().timeInMillis.toString()
            trip.tripId = calendar
            dRef.child(calendar).setValue(trip)
            finish()
        }
    }

    private fun showDatePickerDialog(str:String) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                if(str == "start"){
                    start_date.text = getDateString(selectedDate)}else{
                    end_date.text = getDateString(selectedDate)
                    }
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun getDateString(calendar: Calendar): String {
        return inputDateFormat.format(calendar.time)
    }
}