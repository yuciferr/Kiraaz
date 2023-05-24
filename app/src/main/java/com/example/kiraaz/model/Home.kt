package com.example.kiraaz.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Home(
    var images : ArrayList<String>,
    var address: Address,
    var floor : Int,
    var rooms : String,
    var isAmericanKitchen : Boolean,
    var isFurnished : Boolean
) : Parcelable
