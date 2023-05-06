package com.example.pract7.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pract7.R
import com.example.pract7.databinding.ActivityDepartureDateBinding
import com.example.pract7.model.TicketOrder
import com.google.android.material.datepicker.MaterialDatePicker

class DepartureDateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDepartureDateBinding
    private lateinit var ticketOrder: TicketOrder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepartureDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ticketOrder = intent.getParcelableExtra("ticketOrder")!!

        initHeader()
        binding.pickDepartureDateButton.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener {
                ticketOrder.departureDate = datePicker.headerText
                initHeader()
            }

            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        binding.submitDepartureDateButton.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra("ticketOrder", ticketOrder))
            Log.d("TicketOrder", "onCreate: " + ticketOrder.departureDate)
            finish()
        }
    }

    private fun initHeader() {
        ticketOrder = intent.getParcelableExtra("ticketOrder")!!
        val currentDate: String = ticketOrder.departureDate.ifEmpty {
            getString(R.string.not_chosen)
        }
        binding.currentDepartureDateTextView.text = getString(R.string.departure_date, currentDate)
    }
}