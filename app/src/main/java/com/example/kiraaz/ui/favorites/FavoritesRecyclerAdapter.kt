package com.example.kiraaz.ui.favorites

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiraaz.R
import com.example.kiraaz.databinding.ItemHomePostBinding
import com.example.kiraaz.model.HomePost

class FavoritesRecyclerAdapter(private val items : List<HomePost?>) : RecyclerView.Adapter<FavoritesRecyclerAdapter.FavoritesViewHolder>(){

    class FavoritesViewHolder(private val binding: ItemHomePostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(homePost: HomePost?) {
            binding.apply {
                titleTv.text = homePost?.title
                priceTv.text = homePost?.price.toString()
                val location = homePost?.home?.address?.district + ", " + homePost?.home?.address?.city
                locationTv.text = location
                Glide.with(root.context).load(homePost?.ownerPicture?.toUri()).into(profileIv)
                Glide.with(root.context).load(homePost?.home?.images?.get(0)?.toUri()).into(postIv)
                favoriteBtn.setImageResource(R.drawable.round_favorite_24)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding = ItemHomePostBinding.inflate(
            parent.context.getSystemService(android.view.LayoutInflater::class.java),
            parent,
            false
        )
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(items[position]!!)
            holder.itemView.findNavController().navigate(action)
        }
    }
}