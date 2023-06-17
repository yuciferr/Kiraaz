package com.example.kiraaz.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentMatchesBinding
import com.example.kiraaz.model.Profile


class MatchesFragment : Fragment() {

    private lateinit var binding: FragmentMatchesBinding
    private lateinit var viewModel: MatchesViewModel
    private lateinit var matches: List<Profile>
    private lateinit var percents: List<Int>

    //Show bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MatchesViewModel::class.java]
        viewModel.updateMatches()
        percents = listOf(100, 90, 80, 70, 60, 50, 40, 30, 20, 10)

        viewModel.matches.observe(viewLifecycleOwner) {
            matches = it
            binding.matchesRv.adapter = MatchesRecyclerAdapter(matches, percents)
            binding.matchesRv.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            binding.matchesRv.visibility = View.VISIBLE
            binding.imageView4.visibility = View.GONE
        }


        return binding.root
    }

}