package com.example.kiraaz.model

data class Message(
    val id: String,
    val senderId: String,
    val message: String,
    val timestamp: Long
)