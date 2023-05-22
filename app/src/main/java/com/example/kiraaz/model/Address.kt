package com.example.kiraaz.model

import com.google.android.gms.maps.model.LatLng

data class Address(
    var latLng: LatLng,
    var street : String,
    var city : String,
    var district : String
)