package com.example.kiraaz.ui.messages

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiraaz.databinding.ItemMessageBinding
import com.example.kiraaz.model.Chat

class MessagesRecyclerAdapter(private val items: List<Chat?>) :
    RecyclerView.Adapter<MessagesRecyclerAdapter.MessagesViewHolder>() {

    class MessagesViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: Chat?) {
            binding.apply {
                nameTv.text = chat?.name
                lastMessageTv.text = chat?.lastMessage
                Glide.with(root.context).load(chat?.profileImage?.toUri()).into(profileIv3)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val binding = ItemMessageBinding.inflate(
            parent.context.getSystemService(android.view.LayoutInflater::class.java),
            parent,
            false
        )
        return MessagesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            val action = MessagesFragmentDirections.actionMessagesFragmentToChatFragment(
                items[position]?.id!!,
                items[position]?.profileImage!!,
                items[position]?.name!!
            )
            holder.itemView.findNavController().navigate(action)
        }
    }


}