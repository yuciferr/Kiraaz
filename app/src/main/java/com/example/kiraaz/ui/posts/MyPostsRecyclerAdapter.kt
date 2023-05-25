package com.example.kiraaz.ui.posts

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiraaz.R
import com.example.kiraaz.databinding.HomePostItemBinding
import com.example.kiraaz.model.HomePost

class MyPostsRecyclerAdapter(private val items : List<HomePost?>) : RecyclerView.Adapter<MyPostsRecyclerAdapter.MyPostsViewHolder>(){

    class MyPostsViewHolder(private val binding: HomePostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(homePost: HomePost?) {
            binding.apply {
                titleTv.text = homePost?.title
                priceTv.text = homePost?.price.toString()
                val location = homePost?.home?.address?.district + ", " + homePost?.home?.address?.city
                locationTv.text = location
                Glide.with(root.context).load(homePost?.ownerPicture?.toUri()).into(profileIv)
                Glide.with(root.context).load(homePost?.home?.images?.get(0)?.toUri()).into(postIv)
                favoriteBtn.setImageResource(R.drawable.round_create_16)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostsViewHolder {
        val binding = HomePostItemBinding.inflate(
            parent.context.getSystemService(android.view.LayoutInflater::class.java),
            parent,
            false
        )
        return MyPostsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyPostsViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            val action = MyPostsFragmentDirections.actionMyPostsFragmentToDetailFragment(items[position]!!, true)
            holder.itemView.findNavController().navigate(action)
        }
    }
}