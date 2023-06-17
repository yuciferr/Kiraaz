package com.example.kiraaz.ui.matches

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiraaz.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MatchesViewModel : ViewModel() {

    private val _mAuth = FirebaseAuth.getInstance()
    val mAuth: FirebaseAuth
        get() = _mAuth

    private val _uid = _mAuth.currentUser?.uid
    val uid: String
        get() = _uid!!

    private val database = FirebaseFirestore.getInstance()

    private val _matches: MutableLiveData<List<Profile>> = MutableLiveData()
    val matches: MutableLiveData<List<Profile>>
        get() = _matches

    private val _percents: MutableLiveData<List<Int>> = MutableLiveData()
    val percents: MutableLiveData<List<Int>>
        get() = _percents

    private val profile: MutableLiveData<Profile> = MutableLiveData()

    fun updateMatches() {
        getProfile()
        getAllProfiles()
    }

    private fun getProfile() {
        database.collection("Profiles").document(_uid!!).addSnapshotListener { data, error ->

            if (error != null) {
                println("Error: $error")
            } else {

                if (data != null) {
                    val name = _mAuth.currentUser?.displayName
                    val email = _mAuth.currentUser?.email
                    val image = _mAuth.currentUser?.photoUrl.toString()
                    val gender = data["gender"].toString()
                    val birthDate = data["birthDate"].toString()
                    val city = data["city"].toString()
                    val problems = data["problems"] as ArrayList<String>?

                    profile.value = Profile(name, gender, birthDate, city, email, image, problems)
                }
            }

        }
    }

    private fun getAllProfiles() {
        database.collection("Profiles").get().addOnSuccessListener { documents ->
            val list = ArrayList<Profile>()
            val list2 = ArrayList<Int>()
            for (document in documents) {
                if (document["email"].toString() != profile.value?.email) {
                    val name = document["name"].toString()
                    val email = document["email"].toString()
                    val image = document["image"].toString()
                    val gender = document["gender"].toString()
                    val birthDate = document["birthDate"].toString()
                    val city = document["city"].toString()
                    val problems = document["problems"] as ArrayList<String>?

                    val profile = Profile(name, gender, birthDate, city, email, image, problems)
                    val percent = compareProfiles(profile)
                    if (percent > 49) {
                        list2.add(percent)
                        list.add(profile)
                    }
                }

            }
            _matches.value = list
            _percents.value = list2
        }
    }

    private fun compareProfiles(profile: Profile): Int {
        var count = 0
        count += compareCities(profile)
        count += compareGenders(profile)
        count += compareProblems(profile)
        return count
    }

    private fun compareCities(profile: Profile): Int {
        if (profile.city == this.profile.value?.city) {
            return 20
        }
        return 0
    }

    private fun compareProblems(profile: Profile): Int {
        val pList =
            listOf("Different Gender", "Pets", "Smoking", "Alcohol", "Guests", "Different Language")
        var count = 0
        for (problem in profile.problems!!) {
            if (this.profile.value?.problems?.contains(problem) == true) {
                count++
            }
        }
        for (problem in pList) {
            if (this.profile.value?.problems?.contains(problem) == false && profile.problems?.contains(
                    problem
                ) == false
            ) {
                count++
            }
        }

        return count * 10
    }

    private fun compareGenders(profile: Profile): Int {
        if (profile.gender == this.profile.value?.gender) {
            return 20
        }
        return 0
    }
}