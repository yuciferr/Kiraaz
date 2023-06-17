package com.example.kiraaz.ui.matches

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kiraaz.databinding.ItemMatchesBinding
import com.example.kiraaz.model.Profile

class MatchesRecyclerAdapter(private val items: List<Profile?>, private val percents: List<Int?>) :
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
    }
}