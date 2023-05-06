package com.example.pract7.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pract7.R
import com.example.pract7.databinding.ActivityReturningDateBinding
import com.example.pract7.model.TicketOrder
import com.google.android.material.datepicker.MaterialDatePicker


/**
 * Активность для выбора даты возвращения
 *
 * @property ticketOrder объект класса TicketOrder, хранящий информацию о заказе билета
 * @property binding объект класса ActivityReturningDateBinding, хранящий информацию об элементах интерфейса
 *
 * @see TicketOrder
 */
class ReturningDateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReturningDateBinding
    private lateinit var ticketOrder: TicketOrder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturningDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем объект TicketOrder из предыдущей активности
        ticketOrder = intent.getParcelableExtra("ticketOrder")!!

        // Инициализируем заголовок
        initHeader()

        // Обработчики нажатий на кнопки
        binding.pickReturningDateButton.setOnClickListener {
            // Создаем объект MaterialDatePicker
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.select_date)) // Устанавливаем заголовок
                .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds()) // Устанавливаем текущую дату
                .build() // Создаем объект

            // Обработчик нажатия на кнопку "ОК"
            datePicker.addOnPositiveButtonClickListener {
                // Устанавливаем дату в объект TicketOrder
                ticketOrder.returningDate = datePicker.headerText
                // Инициализируем заголовок
                initHeader()
            }

            // Показываем диалоговое окно
            datePicker.show(supportFragmentManager, datePicker.toString())
        }

        // Обработчик нажатия на кнопку "Подтвердить"
        binding.submitReturningDateButton.setOnClickListener {
            // Возвращаем объект TicketOrder в предыдущую активность
            setResult(RESULT_OK, Intent().putExtra("ticketOrder", ticketOrder))
            // Закрываем текущую активность
            finish()
        }
    }

    /**
     * Инициализируем заголовок
     *
     * Сначала получаем текущую дату в формате строки, если она не пустая, то
     * устанавливаем заголовок в формате "Дата: <текущая дата>", иначе
     * устанавливаем заголовок в формате "Дата: не выбрано"
     */
    private fun initHeader() {
        ticketOrder = intent.getParcelableExtra("ticketOrder")!!
        val currentDate: String = ticketOrder.returningDate.ifEmpty {
            getString(R.string.not_chosen)
        }
        binding.currentReturningDateTextView.text = getString(R.string.returning_date, currentDate)
    }
}