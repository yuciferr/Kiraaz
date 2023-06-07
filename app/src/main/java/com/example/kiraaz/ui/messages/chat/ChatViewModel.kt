package com.example.kiraaz.ui.messages.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kiraaz.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

@Suppress("NAME_SHADOWING")
class ChatViewModel : ViewModel() {

    private val _mAuth = FirebaseAuth.getInstance()
    val mAuth: FirebaseAuth
        get() = _mAuth

    private val _uid = _mAuth.currentUser?.uid
    val uid: String
        get() = _uid!!

    private val database = FirebaseFirestore.getInstance()

    private val _messages = MutableLiveData<List<Message>>()
    val messages: MutableLiveData<List<Message>>
        get() = _messages

    fun getMessages(chatId: String) {
        database.collection("Profiles").document(uid).collection("Messages")
            .document(chatId).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
            }
    }

    fun sendMessage(chatId: String, message: String) {
        val message = Message(
            id = UUID.randomUUID().toString(),
            senderId = uid,
            message = message,
            timestamp = System.currentTimeMillis()
        )
        val messages = ArrayList<Message>()
        database.collection("Profiles").document(uid).collection("Messages").document(chatId).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    messages.addAll(it.get("messages") as ArrayList<Message>)
                }
                messages.add(message)
                database.collection("Profiles").document(uid).collection("Messages")
                    .document(chatId).set(hashMapOf("messages" to messages))
            }
    }

    fun deleteMessage(chatId: String, messageId: String) {
        database.collection("Profiles").document("Messages").collection(chatId).document(messageId)
            .delete()
    }
}