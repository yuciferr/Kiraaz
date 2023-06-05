package com.example.kiraaz.ui.favorites

import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiraaz.R
import com.example.kiraaz.databinding.ItemHomePostBinding
import com.example.kiraaz.model.HomePost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoritesRecyclerAdapter(private val items : List<HomePost?>) : RecyclerView.Adapter<FavoritesRecyclerAdapter.FavoritesViewHolder>(){

    class FavoritesViewHolder(private val binding: ItemHomePostBinding) : RecyclerView.ViewHolder(binding.root) {

        private val mAuth = FirebaseAuth.getInstance()
        private val uid = mAuth.currentUser?.uid.toString()
        private val database = FirebaseFirestore.getInstance()

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

        fun navigateToDetailFragment(item : HomePost?) {
            binding.postIv.setOnClickListener {
                val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(item!!)
                binding.root.findNavController().navigate(action)
            }
        }

        fun favoritePost(item : HomePost?) {
            binding.favoriteBtn.setOnClickListener {
                database.collection("Profiles")
                    .document(uid)
                    .collection("Favorites")
                    .whereEqualTo("id", item?.id)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (documents.isEmpty) {
                            database.collection("Profiles")
                                .document(uid)
                                .collection("Favorites")
                                .document(item?.id.toString())
                                .set(item!!)
                                .addOnSuccessListener {
                                    binding.favoriteBtn.setImageResource(R.drawable.round_favorite_24)
                                }
                        } else {
                            database.collection("Profiles")
                                .document(uid)
                                .collection("Favorites")
                                .document(item?.id.toString())
                                .delete()
                                .addOnSuccessListener {
                                    binding.favoriteBtn.setImageResource(R.drawable.round_favorite_border_24)
                                }
                        }
                    }
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

        holder.favoritePost(items[position])
        holder.navigateToDetailFragment(items[position])
    }
}