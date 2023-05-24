package com.example.kiraaz.model

import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var latLng: LatLng,
    var street : String,
    var city : String,
    var district : String
) : android.os.Parcelable