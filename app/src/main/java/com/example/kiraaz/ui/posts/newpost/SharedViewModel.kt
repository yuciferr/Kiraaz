package com.example.kiraaz.ui.posts.newpost

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _images: MutableLiveData<ArrayList<Uri?>> = MutableLiveData()
    val images: MutableLiveData<ArrayList<Uri?>>
        get() = _images

    private val _isAddImage: MutableLiveData<ArrayList<Boolean>> =
        MutableLiveData(arrayListOf(false, false, false, false, false, false, false, false, false))
    val isAddImage: MutableLiveData<ArrayList<Boolean>>
        get() = _isAddImage

}