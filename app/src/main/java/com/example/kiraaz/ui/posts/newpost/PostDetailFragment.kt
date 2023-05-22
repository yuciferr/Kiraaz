package com.example.kiraaz.ui.posts.newpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentPostDetailBinding
import com.example.kiraaz.utils.Constants

class PostDetailFragment : Fragment() {

    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var viewModel: SharedViewModel


    //Hide bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val roomItems = Constants.rooms
        val roomAutoComplete : AutoCompleteTextView = binding.roomDropdown
        val roomAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_items, roomItems)
        roomAutoComplete.setAdapter(roomAdapter)

        //Back button
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

}