package com.example.kiraaz.model

data class Profile(
    var name: String?,
    var gender: String?,
    var birthDate: String?,
    var city : String?,
    val email : String?,
    var image: String?,
    var problems: ArrayList<String>?)