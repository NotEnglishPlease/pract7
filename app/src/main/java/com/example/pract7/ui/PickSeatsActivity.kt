package com.example.pract7.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pract7.databinding.ActivityPickSeatsBinding
import com.example.pract7.model.TicketOrder

/**
 * Активити для выбора места
 *
 * @property binding объект класса ActivityPickSeatsBinding, содержит все элементы интерфейса
 * @property ticketOrder объект класса TicketOrder, содержит данные о заказе билета
 *
 */
class PickSeatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPickSeatsBinding
    private lateinit var ticketOrder: TicketOrder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPickSeatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ticketOrder = intent.getParcelableExtra("ticketOrder")!!

        // Устанавливаем диапазон значений для NumberPicker
        binding.numberPicker.apply {
            minValue = 1
            maxValue = 30
        }

        // Список кресел
        val chairs = listOf('A', 'B', 'C', 'D', 'E', 'F')
        // Текущее кресло
        var currentChair = 0

        // Логика интерфейса
        binding.apply {
            // Устанавливаем начальное значение места "А"
            seatLetterTextView.text = chairs[currentChair].toString()
            // Делаем левую кнопку невидимой
            leftButton.visibility = android.view.View.INVISIBLE
            leftButton.setOnClickListener {
                if (currentChair > 0) {
                    it.visibility = android.view.View.VISIBLE
                    rightButton.visibility = android.view.View.VISIBLE
                    currentChair--
                    if (currentChair == 0) it.visibility = android.view.View.INVISIBLE
                    seatLetterTextView.text = chairs[currentChair].toString()
                } else {
                    it.visibility = android.view.View.INVISIBLE
                }
            }

            rightButton.setOnClickListener {
                if (currentChair < chairs.size - 1) {
                    it.visibility = android.view.View.VISIBLE
                    leftButton.visibility = android.view.View.VISIBLE
                    currentChair++
                    if (currentChair == chairs.size - 1) it.visibility = android.view.View.INVISIBLE
                    seatLetterTextView.text = chairs[currentChair].toString()
                } else {
                    it.visibility = android.view.View.INVISIBLE
                }
            }

            // Всё аналогично предыдущим активностям
            submitSeatsButton.setOnClickListener {
                ticketOrder.seat = binding.numberPicker.value.toString() + seatLetterTextView.text
                setResult(RESULT_OK, android.content.Intent().putExtra("ticketOrder", ticketOrder))
                finish()
            }
        }


    }
}