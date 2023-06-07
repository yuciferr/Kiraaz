package com.example.kiraaz.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomePost(
    val ownerId: String,
    val ownerPicture: String,
    val ownerName: String,
    val home: Home,
    var title: String,
    var description: String?,
    var price: Int,
    var deposit: Int?,
    var dues: Int?,
    var roommate: Int,
    val date: String,
    val id: String,
    var isAvailable: Boolean
) : Parcelable