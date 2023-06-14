package com.example.kiraaz.ui.profiling

import android.Manifest
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentProfilingBinding
import com.example.kiraaz.utils.Constants
import com.google.android.material.chip.Chip
import java.util.*

@Suppress("DEPRECATION")
class ProfilingFragment : Fragment() {

    private lateinit var binding: FragmentProfilingBinding
    private lateinit var viewModel: ProfilingViewModel

    private var selectedImage: Uri? = null
    private var isImageChanged = false

    private lateinit var args: ProfilingFragmentArgs
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

        args = ProfilingFragmentArgs.fromBundle(requireArguments())
        isNewAccount = args.isNewAccount
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilingBinding.inflate(inflater, container, false)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        }

        //pick image from gallery
        binding.profileIv.setOnClickListener {
            pickPhotoFromGallery()
            isImageChanged = true
        }


        if (isNewAccount) {
            binding.nextBtn.text = "Save"
            binding.cancelBtn.visibility = View.GONE
            binding.nextBtn.setOnClickListener {
                uploadProfile()
                //findNavController().navigate(R.id.action_profilingFragment_to_profileFragment)
            }
            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        } else {

            viewModel.download()
            viewModel.isDownloaded.observe(viewLifecycleOwner) { bool ->
                if (bool){
                    binding.nameTv.setText(viewModel.profile.value?.name)
                    binding.birthdateTv.text = viewModel.profile.value?.birthDate
                    binding.cityDropdown.setText(viewModel.profile.value?.city)

                    if (viewModel.profile.value?.gender == "Male") {
                        binding.genderMale.isChecked = true
                    } else {
                        binding.genderFemale.isChecked = true
                    }

                    if (viewModel.profile.value?.image != "") {
                        binding.profileIv.setImageURI(Uri.parse(viewModel.profile.value?.image))
                    }

                    viewModel.profile.value?.problems?.forEach { i ->
                        when (i) {
                            "Different Gender" -> binding.differentGender.isChecked = true
                            "Pets" -> binding.pets.isChecked = true
                            "Guests" -> binding.guests.isChecked = true
                            "Smoking" -> binding.smoking.isChecked = true
                            "Alcohol" -> binding.alcohol.isChecked = true
                            "Language" -> binding.language.isChecked = true
                        }
                    }
                }else{
                    Toast.makeText(context, viewModel.errorDownload.value, Toast.LENGTH_SHORT).show()
                }


            }

            binding.nextBtn.text = "Update"
            binding.nextBtn.setOnClickListener {
                uploadProfile()
                //findNavController().navigate(R.id.action_profilingFragment_to_profileFragment)
            }
            binding.backBtn.visibility = View.VISIBLE
            binding.backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
            binding.cancelBtn.visibility = View.VISIBLE
            binding.cancelBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        //city dropdown
        val cityItems = Constants.cities
        val cityAutoComplete: AutoCompleteTextView = binding.cityDropdown
        val cityAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, cityItems)
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

            datePicker.show()
            binding.birthdateEt.visibility = View.VISIBLE
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
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
                    ImageDecoder.createSource(
                        requireActivity().contentResolver,
                        selectedImage!!
                    )
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

    private fun uploadProfile() {
        val name = binding.nameTv.text.toString()
        val gender: String =
            if (binding.genders.checkedRadioButtonId == binding.genderMale.id) {
                binding.genderMale.text.toString()
            } else {
                binding.genderFemale.text.toString()
            }
        val birthDate = binding.birthdateTv.text.toString()
        val city = binding.cityDropdown.text.toString()

        val problems = ArrayList<String>()
        binding.problems.checkedChipIds.forEach {
            problems.add(binding.problems.findViewById<Chip>(it).text.toString())
        }

        viewModel.upload(name, gender, birthDate, city, problems)
        viewModel.isUploaded.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Profile uploaded", Toast.LENGTH_SHORT).show()
                if (isImageChanged) {
                    viewModel.uploadImage(selectedImage!!)
                    viewModel.isImageUploaded.observe(viewLifecycleOwner) { img ->
                        if (img) {
                            Toast.makeText(context, "Image uploaded", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_profilingFragment_to_profileFragment)
                        } else {
                            Toast.makeText(
                                context,
                                "Image upload failed",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    findNavController().navigate(R.id.action_profilingFragment_to_profileFragment)
                }
            } else {
                Toast.makeText(context, viewModel.errorDownload.value, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}