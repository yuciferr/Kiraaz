package com.example.kiraaz.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel: ViewModel() {
    private val _mAuth = FirebaseAuth.getInstance()
    val mAuth: FirebaseAuth
        get() = _mAuth

    val isNewUser = MutableLiveData<Boolean>()

    private val _isGoogleSignInSuccessful = MutableLiveData<Boolean>()
    val isGoogleSignInSuccessful: MutableLiveData<Boolean>
        get() = _isGoogleSignInSuccessful

    fun signInWithGoogle(googleSignInAccount: GoogleSignInAccount) {
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        _mAuth.signInWithCredential(googleAuthCredential)
            .addOnCompleteListener { task ->
                isNewUser.value = task.result?.additionalUserInfo?.isNewUser ?: false
                _isGoogleSignInSuccessful.value = task.isSuccessful
            }
    }


    private val _isRegisterSuccessful = MutableLiveData<Boolean>()
    val isRegisterSuccessful: MutableLiveData<Boolean> = _isRegisterSuccessful

    val errorRegister= MutableLiveData<String>()

    fun register(email: String, password: String) {
        _mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _isRegisterSuccessful.value = true
                }
            }.addOnFailureListener {
                errorRegister.value = it.localizedMessage
                _isRegisterSuccessful.value = false
            }
    }

    private val _isLoginSuccessful = MutableLiveData<Boolean>()
    val isLoginSuccessful: MutableLiveData<Boolean> = _isLoginSuccessful

    val errorLogin= MutableLiveData<String>()

    fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _isLoginSuccessful.value = true
                }
            }.addOnFailureListener {
                errorLogin.value = it.localizedMessage
                _isLoginSuccessful.value = false
            }
    }



}