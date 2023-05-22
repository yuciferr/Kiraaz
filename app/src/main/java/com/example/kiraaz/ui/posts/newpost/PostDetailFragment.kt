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
        //Post button
        binding.postBtn.setOnClickListener {

            if (binding.floorTv.text.isNullOrBlank()){
                binding.floor.error = "Floor cannot be empty"
                return@setOnClickListener
            }
            if(binding.roomDropdown.text.isNullOrBlank()){
                binding.room.error = "Room cannot be empty"
                return@setOnClickListener
            }
            if (binding.titleTv.text.isNullOrBlank()){
                binding.title.error = "Title cannot be empty"
                return@setOnClickListener
            }
            if (binding.descriptionTv.text.isNullOrBlank()){
                binding.description.error = "Description cannot be empty"
                return@setOnClickListener
            }
            if (binding.rentTv.text.isNullOrBlank()){
                binding.rent.error = "Rent cannot be empty"
                return@setOnClickListener
            }
            if (binding.roommateTv.text.isNullOrBlank()){
                binding.roommate.error = "Roommate cannot be empty"
                return@setOnClickListener
            }
            viewModel.uploadHomePost(
                binding.titleTv.text.toString(),
                binding.descriptionTv.text.toString(),
                binding.rentTv.text.toString().toInt(),
                binding.depositTv.text.toString().toInt(),
                binding.roommateTv.text.toString().toInt(),
                binding.floorTv.text.toString().toInt(),
                binding.roomDropdown.text.toString(),
                binding.isAmerican.isChecked,
                binding.isFurnished.isChecked
            )

            // Unload the images and data from memory
            viewModel.unLoad()

            // post fragments -> my post fragment
            findNavController().navigateUp()
            findNavController().navigateUp()
            findNavController().navigate(R.id.action_postImageFragment_to_myPostsFragment)
            findNavController().navigateUp()

        }

        return binding.root
    }

}