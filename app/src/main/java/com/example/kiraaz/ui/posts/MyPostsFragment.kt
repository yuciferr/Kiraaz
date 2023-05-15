package com.example.kiraaz.ui.posts

//import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentMyPostsBinding

@Suppress("DEPRECATION")
class MyPostsFragment : Fragment() {

    //private lateinit var viewModel: MyPostsViewModel
    private lateinit var binding: FragmentMyPostsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewModel = ViewModelProvider(this)[MyPostsViewModel::class.java]
    }

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
        binding = FragmentMyPostsBinding.inflate(inflater, container, false)

        binding.backBtn.setOnClickListener {
           findNavController().navigateUp()
        }

        binding.newPostBtn.setOnClickListener {
            findNavController().navigate(R.id.action_myPostsFragment_to_newPostFragment)
        }

        return binding.root
    }

}