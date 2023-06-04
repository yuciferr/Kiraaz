package com.example.kiraaz.ui.detail


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DetailViewModel : ViewModel(){
    private val _mAuth = FirebaseAuth.getInstance()
    val mAuth: FirebaseAuth
        get() = _mAuth

    private val _uid = _mAuth.currentUser?.uid
    val uid: String
        get() = _uid!!

    private val database = FirebaseFirestore.getInstance()

    private val _favorites: MutableLiveData<List<String>> = MutableLiveData()
    val favorites: MutableLiveData<List<String>>
        get() = _favorites

    private fun addFavorite(postId: String) {
        _favorites.value?.let {
            val favorites = it.toMutableList()
            favorites.add(postId)
            _favorites.value = favorites
        }
        database.collection("Profiles")
            .document(uid)
            .collection("Favorites")
            .document(postId)
            .set(hashMapOf("postId" to postId))
    }

    private fun removeFavorite(postId: String) {
        _favorites.value?.let {
            val favorites = it.toMutableList()
            favorites.remove(postId)
            _favorites.value = favorites

            database.collection("Profiles")
                .document(uid)
                .collection("Favorites")
                .document(postId)
                .delete()
        }
    }

    fun getFavorites() {
        database.collection("Profiles")
            .document(uid)
            .collection("Favorites")
            .get()
            .addOnSuccessListener { documents ->
                val favorites: ArrayList<String> = ArrayList()
                for (document in documents) {
                    favorites.add(document.id)
                }
                _favorites.value = favorites
            }
    }

    private fun isFavorite(postId: String): Boolean {
        return _favorites.value?.contains(postId) ?: false
    }

    fun toggleFavorite(postId: String) {
        if (isFavorite(postId)) {
            removeFavorite(postId)
        } else {
            addFavorite(postId)
        }
    }
}