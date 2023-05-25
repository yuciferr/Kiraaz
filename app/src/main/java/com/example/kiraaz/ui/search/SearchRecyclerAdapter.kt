package com.example.kiraaz.ui.search

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiraaz.databinding.HomePostItemBinding
import com.example.kiraaz.model.HomePost

class SearchRecyclerAdapter(private val items : List<HomePost?>) : RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder>(){

    class SearchViewHolder(private val binding: HomePostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(homePost: HomePost?) {
            binding.apply {
                titleTv.text = homePost?.title
                priceTv.text = homePost?.price.toString()
                val location = homePost?.home?.address?.district + ", " + homePost?.home?.address?.city
                locationTv.text = location
                profileIv.setImageURI(homePost?.ownerPicture?.toUri())
                Glide.with(root.context).load(homePost?.home?.images?.get(0)?.toUri()).into(postIv)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = HomePostItemBinding.inflate(
            parent.context.getSystemService(android.view.LayoutInflater::class.java),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(items[position]!!)
            holder.itemView.findNavController().navigate(action)
        }
    }
}