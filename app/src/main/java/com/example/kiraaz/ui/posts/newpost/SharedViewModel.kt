package com.example.kiraaz.ui.posts.newpost

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiraaz.model.Address
import com.example.kiraaz.model.Home
import com.example.kiraaz.model.HomePost
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class SharedViewModel : ViewModel() {

    private val _mAuth = FirebaseAuth.getInstance()
    val mAuth: FirebaseAuth
        get() = _mAuth

    private val _uid = _mAuth.currentUser?.uid
    val uid: String
        get() = _uid!!

    private val database = FirebaseFirestore.getInstance()

    private val storage = FirebaseStorage.getInstance()

    private val _images: MutableLiveData<ArrayList<Uri?>> = MutableLiveData()
    val images: MutableLiveData<ArrayList<Uri?>>
        get() = _images
    private val imageLinks: MutableLiveData<ArrayList<String>> = MutableLiveData()

    private val _isAddImage: MutableLiveData<ArrayList<Boolean>> =
        MutableLiveData(arrayListOf(false, false, false, false, false, false, false, false, false))
    val isAddImage: MutableLiveData<ArrayList<Boolean>>
        get() = _isAddImage


    private val address: MutableLiveData<Address> = MutableLiveData()

    private val _home: MutableLiveData<Home> = MutableLiveData()
    val home: MutableLiveData<Home>
        get() = _home

    private val _homePost: MutableLiveData<HomePost> = MutableLiveData()
    val homePost: MutableLiveData<HomePost>
        get() = _homePost

    fun setAddress(street: String, city: String, district: String, latLng: LatLng) {
        address.value = Address(latLng, street, city, district)
    }

    private fun setHome(floor: Int, rooms: Int, isAmericanKitchen: Boolean, isFurnished: Boolean) {
        _home.value =
            Home(imageLinks.value!!, address.value!!, floor, rooms, isAmericanKitchen, isFurnished)
    }

    fun uploadImages() {
        for (i in 0 until _images.value!!.size) {
            if (_images.value!![i] != null) {
                val imageName = _uid + "home" + (i+1)
                val imageRef = storage.reference.child("images/${imageName}.jpg")

                imageRef.putFile(_images.value!![i]!!).addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener {
                        imageLinks.value?.add(it.toString())
                    }
                }
            }
        }
    }

}