package com.example.pract7.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pract7.R
import com.example.pract7.databinding.ActivityReturningDateBinding
import com.example.pract7.model.TicketOrder
import com.google.android.material.datepicker.MaterialDatePicker

class ReturningDateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReturningDateBinding
    private lateinit var ticketOrder: TicketOrder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturningDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ticketOrder = intent.getParcelableExtra("ticketOrder")!!

        initHeader()

        binding.pickReturningDateButton.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date))
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
                .build()

            datePicker.addOnPositiveButtonClickListener {
                ticketOrder.returningDate = datePicker.headerText
                initHeader()
            }

            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        binding.submitReturningDateButton.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra("ticketOrder", ticketOrder))
            finish()
        }
    }

    private fun initHeader() {
        ticketOrder = intent.getParcelableExtra("ticketOrder")!!
        val currentDate: String = ticketOrder.returningDate.ifEmpty {
            getString(R.string.not_chosen)
        }
        binding.currentReturningDateTextView.text = getString(R.string.returning_date, currentDate)
    }
}