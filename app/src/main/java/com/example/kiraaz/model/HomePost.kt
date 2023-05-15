package com.example.kiraaz.model

data class HomePost(
    val owner : Profile,
    val home : Home,
    var title : String,
    var description : String?,
    var price : Int,
    var deposit : Int?,
    var dues : Int?,
    var roommate : Int,
    val date : String,
    val time : String,
    val id : String,
    var isAvailable : Boolean
)