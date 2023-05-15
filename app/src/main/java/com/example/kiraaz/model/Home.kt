package com.example.kiraaz.model

data class Home(
    var images : ArrayList<String>,
    var address: Address,
    var floor : Int,
    var rooms : Int,
    var livingRooms : Int,
    var isAmericanKitchen : Boolean,
    var isFurnished : Boolean
)
