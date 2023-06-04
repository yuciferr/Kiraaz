package com.example.kiraaz.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentFavoritesBinding
import com.example.kiraaz.model.HomePost


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: FavoritesViewModel

    private var homePosts: List<HomePost?> = listOf()
    private var isEmpty : Boolean = true

    //Show bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        homePosts = viewModel.homePosts.value ?: listOf()
        isEmpty = viewModel.isEmpty.value ?: true

        if (isEmpty) {
            viewModel.getFavorites()
            binding.recyclerview.visibility = View.GONE
            binding.group.visibility = View.VISIBLE
        }
        viewModel.homePosts.observe(viewLifecycleOwner) { posts ->
            homePosts = posts
            binding.group.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE
            binding.recyclerview.adapter = FavoritesRecyclerAdapter(homePosts)
            binding.recyclerview.layoutManager = LinearLayoutManager(context)
        }


        return binding.root
    }


}