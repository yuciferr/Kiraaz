package com.example.kiraaz.ui.posts.newpost

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _images: MutableLiveData<List<Uri?>> = MutableLiveData()
    val images: MutableLiveData<List<Uri?>>
        get() = _images

    private val _isAddImage: MutableLiveData<ArrayList<Boolean>> = MutableLiveData(arrayListOf(false,false,false,false,false,false,false,false,false))
    val isAddImage: MutableLiveData<ArrayList<Boolean>>
        get() = _isAddImage

}