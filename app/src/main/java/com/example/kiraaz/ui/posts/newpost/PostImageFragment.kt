package com.example.kiraaz.ui.posts.newpost

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentPostImageBinding

@Suppress("DEPRECATION")
class PostImageFragment : Fragment() {

    private lateinit var binding: FragmentPostImageBinding
    private lateinit var viewModel: SharedViewModel

    private var images: ArrayList<Uri?> = ArrayList()
    private var isAddImage: ArrayList<Boolean> =
        ArrayList(arrayListOf(false, false, false, false, false, false, false, false, false))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        isAddImage = viewModel.isAddImage.value!!

    }

    //Hide bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostImageBinding.inflate(inflater, container, false)

        viewModel.images.observe(viewLifecycleOwner) {
            images = it
            for (i in it.indices) {
                when (i) {
                    0 -> binding.addIv1.setImageURI(it[i])
                    1 -> binding.addIv2.setImageURI(it[i])
                    2 -> binding.addIv3.setImageURI(it[i])
                    3 -> binding.addIv4.setImageURI(it[i])
                    4 -> binding.addIv5.setImageURI(it[i])
                    5 -> binding.addIv6.setImageURI(it[i])
                    6 -> binding.addIv7.setImageURI(it[i])
                    7 -> binding.addIv8.setImageURI(it[i])
                    8 -> binding.addIv9.setImageURI(it[i])
                }
            }
        }

        viewModel.isAddImage.observe(viewLifecycleOwner) { bool ->
            for (i in bool.indices) {
                binding.apply {
                    when (i) {
                        0 -> {
                            addIv1.setOnClickListener {
                                if (!bool[i]) {
                                    pickPhotoFromGallery()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press Long to Delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addIv1.setOnLongClickListener {
                                if (bool[i]) {
                                    removeImage(i)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press to Add",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                        }
                        1 -> {
                            addIv2.setOnClickListener {
                                if (!bool[i]) {
                                    pickPhotoFromGallery()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press Long to Delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addIv2.setOnLongClickListener {
                                if (bool[i]) {
                                    removeImage(i)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press to Add",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                        }
                        2 -> {
                            addIv3.setOnClickListener {
                                if (!bool[i]) {
                                    pickPhotoFromGallery()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press Long to Delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addIv3.setOnLongClickListener {
                                if (bool[i]) {
                                    removeImage(i)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press to Add",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                        }
                        3 -> {
                            addIv4.setOnClickListener {
                                if (!bool[i]) {
                                    pickPhotoFromGallery()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press Long to Delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addIv4.setOnLongClickListener {
                                if (bool[i]) {
                                    removeImage(i)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press to Add",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                        }
                        4 -> {
                            addIv5.setOnClickListener {
                                if (!bool[i]) {
                                    pickPhotoFromGallery()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press Long to Delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addIv5.setOnLongClickListener {
                                if (bool[i]) {
                                    removeImage(i)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press to Add",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                        }
                        5 -> {
                            addIv6.setOnClickListener {
                                if (!bool[i]) {
                                    pickPhotoFromGallery()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press Long to Delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addIv6.setOnLongClickListener {
                                if (bool[i]) {
                                    removeImage(i)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press to Add",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                        }
                        6 -> {
                            addIv7.setOnClickListener {
                                if (!bool[i]) {
                                    pickPhotoFromGallery()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press Long to Delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addIv7.setOnLongClickListener {
                                if (bool[i]) {
                                    removeImage(i)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press to Add",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                        }
                        7 -> {
                            addIv8.setOnClickListener {
                                if (!bool[i]) {
                                    pickPhotoFromGallery()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press Long to Delete",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addIv8.setOnLongClickListener {
                                if (bool[i]) {
                                    removeImage(i)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press to Add",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                        }
                        8 -> {
                            addIv9.setOnClickListener {
                                if (!bool[i]) {
                                    pickPhotoFromGallery()
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "You can only add 9 images",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                            addIv9.setOnLongClickListener {
                                if (bool[i]) {
                                    removeImage(i)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Press to Add",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                true
                            }
                        }
                    }
                }
            }
        }

        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
            nextBtn.setOnClickListener {
                if(viewModel.isAddImage.value!![0]){
                    viewModel.uploadImages()
                    findNavController().navigate(R.id.action_postImageFragment_to_postAddressFragment)
                }else{
                    Toast.makeText(requireContext(),"Please add at least one image",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }

    @SuppressLint("IntentReset")
    private fun pickPhotoFromGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 2)

        }

    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickPhotoFromGallery()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount.coerceAtMost(9 - images.size)
                for (i in 0 until count) {
                    val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                    images.add(imageUri)
                    isAddImage(i)
                }
            } else if (data?.data != null) {
                val imageUri: Uri = data.data!!
                images.add(imageUri)
                isAddImage(0)
            }
            viewModel.images.value = images
            viewModel.isAddImage.value = isAddImage
        }
    }

    private fun isAddImage(i: Int) {
        if (!isAddImage[i]) {
            isAddImage[i] = true
            return
        }
        for (j in i until isAddImage.size) {
            if (!isAddImage[j]) {
                isAddImage[j] = true
                return
            }
        }
    }

    private fun removeImage(i: Int) {
        isAddImage.removeAt(i)
        images.removeAt(i)
        isAddImage.add(false)
        viewModel.images.value = images
        viewModel.isAddImage.value = isAddImage
        removeLastImage()

    }

    private fun removeLastImage() {
        viewModel.images.observe(viewLifecycleOwner) {
            when (it.size) {
                0 -> {
                    binding.addIv1.setImageResource(R.drawable.add_icon)

                }
                1 -> {
                    binding.addIv2.setImageResource(R.drawable.add_icon)

                }
                2 -> {
                    binding.addIv3.setImageResource(R.drawable.add_icon)

                }
                3 -> {
                    binding.addIv4.setImageResource(R.drawable.add_icon)

                }
                4 -> {
                    binding.addIv5.setImageResource(R.drawable.add_icon)

                }
                5 -> {
                    binding.addIv6.setImageResource(R.drawable.add_icon)

                }
                6 -> {
                    binding.addIv7.setImageResource(R.drawable.add_icon)

                }
                7 -> {
                    binding.addIv8.setImageResource(R.drawable.add_icon)

                }
                8 -> {
                    binding.addIv9.setImageResource(R.drawable.add_icon)

                }
            }
        }
    }

}