package com.example.kiraaz.ui.matches

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiraaz.databinding.ItemMatchesBinding
import com.example.kiraaz.model.Profile

class MatchesRecyclerAdapter(
    private val items: ArrayList<Profile>,
    private val percents: List<Int?>
) :
    RecyclerView.Adapter<MatchesRecyclerAdapter.MatchesViewHolder>() {

    class MatchesViewHolder(private val binding: ItemMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(profile: Profile?, percent: Int?) {
            binding.apply {
                nameTv.text = profile?.name
                Glide.with(root.context).load(profile?.image).into(profileIv3)
                val year = profile?.birthDate?.reversed()?.take(4)?.reversed()?.toInt()
                val age = 2023 - year!!
                genderAgeTv.text = "${profile.gender}, $age "
                profile.problems?.forEach { i ->
                    when (i) {
                        "Different Gender" -> binding.differentGender.isChecked = true
                        "Pets" -> binding.pets.isChecked = true
                        "Guests" -> binding.guests.isChecked = true
                        "Smoking" -> binding.smoking.isChecked = true
                        "Alcohol" -> binding.alcohol.isChecked = true
                        "Different Language" -> binding.language.isChecked = true
                    }
                }
                percentTv.text = "%$percent"

            }
        }
        fun navigateToChat(item: Profile?) {
            binding.root.setOnClickListener{
                val action = MatchesFragmentDirections.actionMatchesFragmentToChatFragment(
                    item?.uid.toString(),
                    item?.image.toString(),
                    item?.name.toString()
                )
                binding.root.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val binding = ItemMatchesBinding.inflate(
            parent.context.getSystemService(android.view.LayoutInflater::class.java),
            parent,
            false
        )
        return MatchesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        holder.bind(items[position], percents[position])
        holder.navigateToChat(items[position])
    }


    inner class SwipeGestures : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

            when (swipeDir) {
                ItemTouchHelper.LEFT -> {
                    items.removeAt(viewHolder.adapterPosition)
                    notifyItemRemoved(viewHolder.adapterPosition)
                }

            }
        }
    }
}