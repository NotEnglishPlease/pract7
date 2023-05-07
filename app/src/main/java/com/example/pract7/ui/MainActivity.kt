package com.example.pract7.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pract7.R
import com.example.pract7.databinding.ActivityMainBinding
import com.example.pract7.model.TicketOrder

class MainActivity : AppCompatActivity() {

    // Создаем объект класса ActivityMainBinding, будем использовать его для получения доступа к элементам интерфейса
    private lateinit var binding: ActivityMainBinding

    // Создаем объект класса TicketOrder, будем использовать его для передачи данных между активити
    private lateinit var ticketOrder: TicketOrder

    // Создаем контракты для получения данных из других активити
    // Первый контракт для получения даты отправления
    private val departureDateActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Проверяем, что результат получен успешно
            if (result.resultCode == Activity.RESULT_OK) {
                // Получаем объект класса TicketOrder из возвращенных данных
                val returnedTicketOrder =
                    result.data?.getParcelableExtra<TicketOrder>("ticketOrder")!!
                // Получаем дату отправления из объекта TicketOrder
                // если дата не выбрана, то возвращаем строку "Не выбрана"
                val date =
                    returnedTicketOrder.departureDate.ifEmpty { getString(R.string.not_chosen) }
                // Записываем дату отправления в объект ticketOrder
                ticketOrder.departureDate = date
                // Записываем дату отправления в TextView
                binding.departureDateTextView.text = getString(R.string.departure_date, date)
            }
        }

    // Второй контракт для получения даты возвращения
    // Все аналогично первому контракту
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

    // Третий контракт для получения места
    // Все аналогично первому контракту
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

        // Будем использовать ViewBinding для получения доступа к элементам интерфейса
        // это позволит избежать использования функции findViewById

        // Инициализируем объект класса ActivityMainBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Устанавливаем полученный интерфейс для активити
        setContentView(binding.root)

        // Инициализируем объект класса TicketOrder
        ticketOrder = TicketOrder()

        // Инициализируем поля интерфейса
        initFields()

        // Устанавливаем обработчики нажатий на кнопки
        binding.departureDateButton.setOnClickListener {
            // Создаем явный интент для перехода на активити выбора даты отправления
            val intent = Intent(this, DepartureDateActivity::class.java)
            // Передаем объект класса TicketOrder в активити выбора даты отправления
            intent.putExtra("ticketOrder", ticketOrder)
            // Добавляем флаг FLAG_ACTIVITY_CLEAR_TOP, чтобы при возврате на это активити
            // не создавалось новое активити, а использовалось уже существующее
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            // Запускаем активити выбора даты отправления
            departureDateActivityResult.launch(intent)
        }

        // Все аналогично первой кнопке
        binding.returningDateButton.setOnClickListener {
            val intent = Intent(this, ReturningDateActivity::class.java)
            intent.putExtra("ticketOrder", ticketOrder)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            returningDateActivityResult.launch(intent)
        }

        // Все аналогично первой кнопке
        binding.seatButton.setOnClickListener {
            val intent = Intent(this, PickSeatsActivity::class.java)
            intent.putExtra("ticketOrder", ticketOrder)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            seatActivityResult.launch(intent)
        }
    }

    /**
     * Инициализируем поля интерфейса
     *
     * Все поля будут иметь значение "Не выбрано"
     */
    private fun initFields() {
        binding.apply {
            val notChosen = getString(R.string.not_chosen)
            departureDateTextView.text = getString(R.string.departure_date, notChosen)
            returningDateTextView.text = getString(R.string.returning_date, notChosen)
            seatTextView.text = getString(R.string.seat, notChosen)
        }
    }
}