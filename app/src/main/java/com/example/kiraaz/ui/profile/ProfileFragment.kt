package com.example.kiraaz.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

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
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {

            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK){
                Configuration.UI_MODE_NIGHT_NO -> {
                    // Light theme is active, switch to dark
                    binding.themeBtn.text = "Dark Mode"
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    // Dark theme is active, switch to light
                    binding.themeBtn.text = "Light Mode"
                }
                else -> {
                    // Follow system theme
                    binding.themeBtn.text = "System Mode"
                }
            }


            //database get
            viewModel.download()
            viewModel.isDownloaded.observe(viewLifecycleOwner){
                if(it){
                    binding.nameTv.text = viewModel.profile.value?.name
                    binding.profileIv2.setImageURI(viewModel.profile.value?.image?.toUri())
                    binding.genderTv.text = viewModel.profile.value?.gender
                }else{
                    Toast.makeText(context, viewModel.errorDownload.value, Toast.LENGTH_SHORT).show()
                }

            }
            viewModel.isEmpty.observe(viewLifecycleOwner){
                if (it){
                    findNavController().navigate(R.id.action_profileFragment_to_profilingFragment)
                }
            }

            binding.profileInfoBtn.setOnClickListener {
                val action = ProfileFragmentDirections.actionProfileFragmentToProfilingFragment(isNewAccount = false)
                findNavController().navigate(action)
            }

            binding.themeBtn.setOnClickListener{
                //change theme
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        // Light theme is active, switch to dark
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    Configuration.UI_MODE_NIGHT_YES -> {
                        // Dark theme is active, switch to light
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                    }
                    else -> {
                        // Follow system theme
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
            }

            binding.postBtn.setOnClickListener{
                findNavController().navigate(R.id.action_profileFragment_to_myPostsFragment)
            }

            binding.signoutBtn.setOnClickListener{
                viewModel.mAuth.signOut()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                findNavController().clearBackStack(R.id.loginFragment)
            }

        }
        return binding.root
    }
}