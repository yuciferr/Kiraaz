package com.example.kiraaz.ui.posts.newpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentPostImageBinding

class PostImageFragment : Fragment() {

    private lateinit var binding: FragmentPostImageBinding

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
        binding = FragmentPostImageBinding.inflate(inflater, container, false)

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_postImageFragment_to_postAddressFragment)
        }
        return binding.root
    }

}