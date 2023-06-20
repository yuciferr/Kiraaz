package com.example.kiraaz.ui.profiling

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiraaz.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class ProfilingViewModel : ViewModel() {

    private val _mAuth = FirebaseAuth.getInstance()
    val mAuth: FirebaseAuth
        get() = _mAuth

    private val _uid = _mAuth.currentUser?.uid
    val uid: String
        get() = _uid!!

    private val database = FirebaseFirestore.getInstance()

    private val storage = FirebaseStorage.getInstance()

    //Upload Profile
    private val _isUploaded = MutableLiveData<Boolean>()
    val isUploaded: MutableLiveData<Boolean>
        get() = _isUploaded

    private val _profile = MutableLiveData<Profile>()
    val profile: MutableLiveData<Profile>
        get() = _profile

    fun upload(name : String, gender : String, birthdate : String, city: String, problems: ArrayList<String>){

        _mAuth.currentUser?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
        )

        _profile.value = Profile(uid,name,gender,birthdate,city,
            _mAuth.currentUser?.email,_mAuth.currentUser?.photoUrl.toString(),problems)

        database.collection("Profiles").document(_uid!!).set(_profile.value!!).addOnSuccessListener {
            _isUploaded.value = true
        }.addOnFailureListener {
            _isUploaded.value = false
        }

    }

    //Upload Profile Image
    private val _isImageUploaded = MutableLiveData<Boolean>()
    val isImageUploaded: MutableLiveData<Boolean>
        get() = _isImageUploaded

    fun uploadImage(selectedImage : Uri){
        val imageName = _uid
        val imageRef = storage.reference.child("images/${imageName}.jpg")

        imageRef.putFile(selectedImage).addOnSuccessListener {

            //set firebase user display photo
            _mAuth.currentUser?.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setPhotoUri(selectedImage)
                    .build()
            )

            _profile.value?.image = selectedImage.toString()

            imageRef.downloadUrl.addOnSuccessListener {
                database.collection("Profiles").document(_uid!!).update("image", it.toString())
            }

            _isImageUploaded.value = true
        }.addOnFailureListener {
            _isImageUploaded.value = false
        }
    }

    //New User
    private val _isNewUser = MutableLiveData<Boolean>()
    val isNewUser: MutableLiveData<Boolean>
        get() = _isNewUser

    fun checkNewUser(){
        database.collection("Profiles").document(_uid!!).get().addOnSuccessListener {
            _isNewUser.value = !it.exists()
        }
    }

    //Get Profile
    private val _isDownloaded = MutableLiveData<Boolean>()
    val isDownloaded: MutableLiveData<Boolean>
        get() = _isDownloaded

    val errorDownload = MutableLiveData<String>()

    fun download(){
        database.collection("Profiles").document(_uid!!).addSnapshotListener { data, error ->

            if(error != null){
                _isDownloaded.value = false
                errorDownload.value = error.message
            }else{

                if(data != null){
                    val name = _mAuth.currentUser?.displayName
                    val email = _mAuth.currentUser?.email
                    val image = _mAuth.currentUser?.photoUrl.toString()
                    val gender = data["gender"].toString()
                    val birthDate = data["birthDate"].toString()
                    val city = data["city"].toString()
                    val problems = data["problems"] as ArrayList<String>?

                    _profile.value = Profile(uid,name,gender, birthDate, city, email, image, problems)

                    _isDownloaded.value = true
                }
            }

        }
    }
}