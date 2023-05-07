package com.example.pract7.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Модель данных для заказа билета
 *
 * @property departureDate
 * @property returningDate
 * @property seat
 * @constructor Создает объект класса TicketOrder
 */
class TicketOrder(
    var departureDate: String = "",
    var returningDate: String = "",
    var seat: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    // Функция для записи данных в Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(departureDate)
        parcel.writeString(returningDate)
        parcel.writeString(seat)
    }

    // Функция для преобразования объекта в строку
    override fun toString(): String {
        return "$departureDate $returningDate $seat"
    }

    // Функция для описания объекта
    override fun describeContents(): Int {
        return 0
    }

    // Статический объект CREATOR, используемый для преобразования Parcel в объект TicketOrder
    companion object CREATOR : Parcelable.Creator<TicketOrder> {
        override fun createFromParcel(parcel: Parcel): TicketOrder {
            return TicketOrder(parcel)
        }

        override fun newArray(size: Int): Array<TicketOrder?> {
            return arrayOfNulls(size)
        }
    }
}