package com.example.kiraaz.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiraaz.databinding.ImageItemBinding
import com.example.kiraaz.ui.detail.DetailViewPagerAdapter.ViewPagerViewHolder

class DetailViewPagerAdapter(private val items : ArrayList<String>) : RecyclerView.Adapter<ViewPagerViewHolder>(){
    class ViewPagerViewHolder(private val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image : String) {
            binding.apply {
                Glide.with(root.context).load(image).into(homeIv)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ImageItemBinding.inflate(
            parent.context.getSystemService(android.view.LayoutInflater::class.java),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(items[position])
    }

}