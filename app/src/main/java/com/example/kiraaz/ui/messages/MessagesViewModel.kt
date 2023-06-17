package com.example.kiraaz.ui.messages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiraaz.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MessagesViewModel : ViewModel() {
    private val _mAuth = FirebaseAuth.getInstance()
    val mAuth: FirebaseAuth
        get() = _mAuth

    private val _uid = _mAuth.currentUser?.uid
    val uid: String
        get() = _uid!!

    private val database = FirebaseFirestore.getInstance()

    private val _chats: MutableLiveData<List<Chat>> = MutableLiveData()
    val chats: MutableLiveData<List<Chat>>
        get() = _chats

    private val lastMessage : MutableLiveData<String> = MutableLiveData()


    fun getChats(){
        database.collection("Profiles")
            .document(uid)
            .collection("Messages")
            .get()
            .addOnSuccessListener { documents ->
                val chatIds: ArrayList<String> = ArrayList()
                for (document in documents) {
                    chatIds.add(document.id)
                }
                getChats(chatIds)
            }
    }

    private fun getChats(chatIds: List<String>) {
        val chats: ArrayList<Chat> = ArrayList()
        for (chatId in chatIds) {
            database.collection("Profiles")
                .document(chatId)
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val name = it.get("name") as String
                        val profileImage = it.get("image") as String
                        getLastMessage(chatId)
                        val lastMessage = lastMessage.value.toString()
                        chats.add(Chat(chatId, name, profileImage, lastMessage))
                        _chats.value = chats
                    }
                }
        }
    }



   private fun getLastMessage(chatId: String){

        database.collection("Profiles").document(uid).collection("Messages")
            .document(chatId).get().addOnSuccessListener {
                if (it.exists()) {
                    val messages = it.get("messages") as java.util.ArrayList<HashMap<String, Any>>
                    lastMessage.value = messages[messages.size - 1]["message"] as String

                }
            }

    }
}