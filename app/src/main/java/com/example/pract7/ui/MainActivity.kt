package com.example.pract7.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pract7.R
import androidx.activity.result.contract.ActivityResultContracts
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
                Log.d("aaa", "departureResult: ${returnedTicketOrder.departureDate}")
                val date =
                    returnedTicketOrder.departureDate
                ticketOrder.departureDate = date
                binding.departureDateTextView.text = getString(
                    R.string.departure_date,
                    date.ifEmpty { getString(R.string.not_chosen) })
                Log.d("TicketOrder", date)
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
                val seat = returnedTicketOrder?.seat ?: getString(R.string.not_chosen)
                ticketOrder.seat = seat
                binding.seatTextView.text = getString(R.string.seat, seat)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ticketOrder = TicketOrder()

        //initFields()

        binding.departureDateButton.setOnClickListener {
            Log.d("TicketOrder", "onCreate: sent: ${ticketOrder.toString()}")
            val departureDateIntent = Intent(this, DepartureDateActivity::class.java)
            departureDateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            departureDateIntent.putExtra("ticketOrder", ticketOrder)
            departureDateActivityResult.launch(departureDateIntent)
        }

        binding.returningDateButton.setOnClickListener {
            val returningDateIntent = Intent(this, ReturningDateActivity::class.java)
            returningDateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            returningDateIntent.putExtra("ticketOrder", ticketOrder)
            returningDateActivityResult.launch(returningDateIntent)
        }

        binding.seatButton.setOnClickListener {
            val seatIntent = Intent(this, PickSeatsActivity::class.java)
            seatIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            seatIntent.putExtra("ticketOrder", ticketOrder)
            seatActivityResult.launch(seatIntent)
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