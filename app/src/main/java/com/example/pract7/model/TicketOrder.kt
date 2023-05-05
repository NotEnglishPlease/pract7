package com.example.pract7.model

import android.os.Parcel
import android.os.Parcelable

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(departureDate)
        parcel.writeString(returningDate)
        parcel.writeString(seat)
    }

    override fun toString(): String {
        return "$departureDate $returningDate $seat"
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TicketOrder> {
        override fun createFromParcel(parcel: Parcel): TicketOrder {
            return TicketOrder(parcel)
        }

        override fun newArray(size: Int): Array<TicketOrder?> {
            return arrayOfNulls(size)
        }
    }
}