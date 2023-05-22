package com.example.kiraaz.model

data class HomePost(
    val ownerId : String,
    val home : Home,
    var title : String,
    var description : String?,
    var price : Int,
    var deposit : Int?,
    var dues : Int?,
    var roommate : Int,
    val date : String,
    val id : String,
    var isAvailable : Boolean
)