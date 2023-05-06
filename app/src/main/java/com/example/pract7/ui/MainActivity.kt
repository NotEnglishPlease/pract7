package com.example.pract7.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pract7.R
import com.example.pract7.databinding.ActivityMainBinding
import com.example.pract7.model.TicketOrder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ticketOrder: TicketOrder

    private val departureDateActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val returnedTicketOrder =
                    result.data?.getParcelableExtra<TicketOrder>("ticketOrder")!!
                val date =
                    returnedTicketOrder.departureDate.ifEmpty { getString(R.string.not_chosen) }
                ticketOrder.departureDate = date
                binding.departureDateTextView.text = getString(R.string.departure_date, date)
            }
        }

    private val returningDateActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val returnedTicketOrder =
                    result.data?.getParcelableExtra<TicketOrder>("ticketOrder")!!
                val date =
                    returnedTicketOrder.returningDate.ifEmpty { getString(R.string.not_chosen) }
                ticketOrder.returningDate = date
                binding.returningDateTextView.text = getString(R.string.returning_date, date)
            }
        }

    private val seatActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val returnedTicketOrder =
                    result.data?.getParcelableExtra<TicketOrder>("ticketOrder")
                val seat = returnedTicketOrder?.seat?.ifEmpty { getString(R.string.not_chosen) }
                ticketOrder.seat = seat!!
                binding.seatTextView.text = getString(R.string.seat, seat)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ticketOrder = TicketOrder()

        initFields()

        binding.departureDateButton.setOnClickListener {
            val intent = Intent(this, DepartureDateActivity::class.java)
            intent.putExtra("ticketOrder", ticketOrder)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            departureDateActivityResult.launch(intent)
        }

        binding.returningDateButton.setOnClickListener {
            val intent = Intent(this, ReturningDateActivity::class.java)
            intent.putExtra("ticketOrder", ticketOrder)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            returningDateActivityResult.launch(intent)
        }

        binding.seatButton.setOnClickListener {
            val intent = Intent(this, PickSeatsActivity::class.java)
            intent.putExtra("ticketOrder", ticketOrder)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            seatActivityResult.launch(intent)
        }
    }

    private fun initFields() {
        binding.apply {
            val notChosen = getString(R.string.not_chosen)
            departureDateTextView.text = getString(R.string.departure_date, notChosen)
            returningDateTextView.text = getString(R.string.returning_date, notChosen)
            seatTextView.text = getString(R.string.seat, notChosen)
        }
    }
}