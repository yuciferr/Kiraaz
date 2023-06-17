package com.example.kiraaz.ui.messages.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kiraaz.databinding.ItemChatReceiveBinding
import com.example.kiraaz.databinding.ItemChatSendBinding
import com.example.kiraaz.model.Message
import com.google.firebase.auth.FirebaseAuth

class ChatRecyclerAdapter(private val items: List<Message?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SenderViewHolder(private val binding: ItemChatSendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message?) {
            binding.apply {
                messageTv.text = message?.message
            }
        }

    }

    class ReceiverViewHolder(private val binding: ItemChatReceiveBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message?) {
            binding.apply {
                messageTv.text = message?.message
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> SenderViewHolder(
                ItemChatSendBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ReceiverViewHolder(
                ItemChatReceiveBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            1 -> (holder as SenderViewHolder).bind(items[position])
            else -> (holder as ReceiverViewHolder).bind(items[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]?.senderId) {
            FirebaseAuth.getInstance().currentUser?.uid -> 1
            else -> 2
        }
    }
}