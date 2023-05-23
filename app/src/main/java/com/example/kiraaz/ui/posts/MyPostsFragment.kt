package com.example.kiraaz.ui.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentMyPostsBinding
import com.example.kiraaz.model.HomePost

@Suppress("DEPRECATION")
class MyPostsFragment : Fragment() {

    private lateinit var viewModel: MyPostsViewModel
    private lateinit var binding: FragmentMyPostsBinding

    private var isEmpty : Boolean = true
    private var homePosts : List<HomePost?> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MyPostsViewModel::class.java]

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

        homePosts = viewModel.homePosts.value ?: listOf()
        isEmpty = viewModel.isEmpty.value ?: true

        if(homePosts.isEmpty()){
            viewModel.getMyPosts()
            viewModel.isEmpty.observe(viewLifecycleOwner) {
                isEmpty = it
                if(isEmpty){
                    binding.group.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }else{
                    viewModel.homePosts.observe(viewLifecycleOwner) { posts ->
                        homePosts = posts
                        binding.recyclerView.adapter = MyPostsRecyclerAdapter(homePosts)
                        binding.recyclerView.layoutManager = LinearLayoutManager(context)
                    }
                    binding.group.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE

                }
            }
        }else{
            if(isEmpty){
                binding.group.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }else{
                binding.group.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.recyclerView.adapter = MyPostsRecyclerAdapter(homePosts)
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.progressBar.visibility = View.GONE

            }
        }


        binding.backBtn.setOnClickListener {
           findNavController().navigateUp()
        }

        binding.newPostBtn.setOnClickListener {
            findNavController().navigate(R.id.action_myPostsFragment_to_postImageFragment)
        }

        return binding.root
    }

}