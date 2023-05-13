package com.example.kiraaz.ui.profiling

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentProfilingBinding
import com.example.kiraaz.utils.Constants
import java.util.*

class ProfilingFragment : Fragment() {

    private lateinit var binding: FragmentProfilingBinding
    private lateinit var viewModel: ProfilingViewModel

    private var selectedImage: Uri? = null
    private var isImageChanged = false

    //private lateinit var args : ProfileInfoFragmentArgs
    private var isNewAccount = false


    //Hide bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.GONE
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfilingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilingBinding.inflate(inflater, container, false)

        //pick image from gallery
        binding.profileIv.setOnClickListener {
            Toast.makeText(requireContext(), "Image clicked", Toast.LENGTH_SHORT).show()
            pickPhotoFromGallery()
            isImageChanged = true
        }

        //city dropdown
        val cityItems = Constants.cities
        val cityAutoComplete: AutoCompleteTextView = binding.cityDropdown
        val cityAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_items, cityItems)
        cityAutoComplete.setAdapter(cityAdapter)

        //date picker
        binding.birthdateTv.setOnClickListener {
            val getDate = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val date = "$dayOfMonth/${month + 1}/$year"
                    binding.birthdateTv.text = date
                },
                getDate.get(Calendar.YEAR),
                getDate.get(Calendar.MONTH),
                getDate.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.datePicker.maxDate = System.currentTimeMillis()

            if (binding.birthdateTv.text != "Birthdate") {
                val date = binding.birthdateTv.text.toString().split("/")
                datePicker.updateDate(date[2].toInt(), date[1].toInt() - 1, date[0].toInt())
            }

            datePicker.show()
            binding.birthdateEt.visibility = View.VISIBLE
        }


        return binding.root
    }

    @SuppressLint("IntentReset")
    private fun pickPhotoFromGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
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

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data
            if (selectedImage != null && Build.VERSION.SDK_INT >= 28) {
                val source =
                    ImageDecoder.createSource(requireActivity().contentResolver, selectedImage!!)
                val bitmap = ImageDecoder.decodeBitmap(source)
                binding.profileIv.setImageBitmap(bitmap)
            } else {
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    selectedImage
                )
                binding.profileIv.setImageBitmap(bitmap)

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}