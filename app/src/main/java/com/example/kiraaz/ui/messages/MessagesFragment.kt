package com.example.kiraaz.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentMessagesBinding
import com.example.kiraaz.model.Chat


class MessagesFragment : Fragment() {

    private lateinit var binding: FragmentMessagesBinding
    private lateinit var viewModel: MessagesViewModel
    private lateinit var chats : List<Chat>


    //Show bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MessagesViewModel::class.java]
        viewModel.getChats()

        viewModel.chats.observe(viewLifecycleOwner) {
            chats = it
            binding.messagesRv.adapter = MessagesRecyclerAdapter(chats)
            binding.messagesRv.layoutManager = LinearLayoutManager(requireContext())
        }


        return binding.root
    }


}