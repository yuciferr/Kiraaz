package com.example.kiraaz.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiraaz.model.Address
import com.example.kiraaz.model.Home
import com.example.kiraaz.model.HomePost
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("UNCHECKED_CAST")
class SearchViewModel : ViewModel() {
    private val _mAuth = FirebaseAuth.getInstance()
    val mAuth: FirebaseAuth
        get() = _mAuth

    private val _uid = _mAuth.currentUser?.uid
    val uid: String
        get() = _uid!!


    private val database = FirebaseFirestore.getInstance()

    private val _homePosts: MutableLiveData<List<HomePost?>> = MutableLiveData()
    val homePosts: MutableLiveData<List<HomePost?>>
        get() = _homePosts

    val isEmpty: MutableLiveData<Boolean> = MutableLiveData()


    fun getPosts(city: String? = "Antalya") {
        database.collection("HomePosts")
            .whereEqualTo("home.address.city", city)
            .get()
            .addOnSuccessListener { documents ->
                val homePosts: ArrayList<HomePost?> = ArrayList()
                for (document in documents) {

                    val homeData = document.get("home") as HashMap<String, Any>
                    val addressData = homeData["address"] as HashMap<String, Any>
                    val latLngData = addressData["latLng"] as HashMap<String, Any>
                    val latLng = LatLng(
                        latLngData["latitude"] as Double,
                        latLngData["longitude"] as Double
                    )
                    val address = Address(
                        latLng,
                        addressData["street"] as String,
                        addressData["city"] as String,
                        addressData["district"] as String
                    )

                    val home = Home(
                        homeData["images"] as ArrayList<String>,
                        address,
                        homeData["floor"].toString().toInt(),
                        homeData["rooms"] as String,
                        homeData["isAmericanKitchen"].toString().toBoolean(),
                        homeData["isFurnished"].toString().toBoolean()
                    )

                    val homePost = HomePost(
                        document.getString("ownerId")!!,
                        document.getString("ownerPicture")!!,
                        home,
                        document.getString("title")!!,
                        document.getString("description"),
                        document.getLong("price")!!.toInt(),
                        document.getLong("deposit")!!.toInt(),
                        document.getLong("dues")!!.toInt(),
                        document.getLong("roommate")!!.toInt(),
                        document.getString("date")!!,
                        document.getString("id")!!,
                        document.getBoolean("available")!!
                    )

                    homePosts.add(homePost)
                }
                _homePosts.value = homePosts
                isEmpty.value = homePosts.isEmpty()
            }.addOnFailureListener() {
                isEmpty.value = true
            }
    }
}