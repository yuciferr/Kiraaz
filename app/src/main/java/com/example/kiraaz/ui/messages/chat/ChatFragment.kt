package com.example.kiraaz.ui.messages.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var viewModel: ChatViewModel
    private val navArgs by navArgs<ChatFragmentArgs>()

    //Hide bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        //viewModel.getMessages(navArgs.ownerID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)


        binding.apply {

            profileIv3.setImageURI(navArgs.ownerPhoto.toUri())
            nameTv.text = navArgs.ownerName


            sendBtn.setOnClickListener {
                val message = messageTv.text.toString()
                viewModel.sendMessage(navArgs.ownerID, message)
                messageTv.text?.clear()
            }
        }

        return binding.root
    }

}