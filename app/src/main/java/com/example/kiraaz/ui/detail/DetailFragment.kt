package com.example.kiraaz.ui.detail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var navArgs: DetailFragmentArgs
    private lateinit var viewModel: DetailViewModel

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap

    //Hide bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navArgs = DetailFragmentArgs.fromBundle(requireArguments())
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.args.value = navArgs
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel.getFavorites()


        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        binding.apply {
            viewModel.args.observe(viewLifecycleOwner) {
                val post = it.post
                val images = post?.home?.images!!
                val adapter = DetailViewPagerAdapter(images)
                viewPager.adapter = adapter
                titleTv.text = post.title
                priceTv.text = post.price.toString()
                val location = post.home.address.district + ", " + post.home.address.city
                locationTv.text = location
                descriptionTv.text = post.description
                Glide.with(requireContext()).load(post.ownerPicture).into(profileIv)
                roommateTv.text = post.roommate.toString()
                roomTv.text = post.home.rooms
                floorTv.text = post.home.floor.toString()
                depositTv.text = post.deposit.toString()
                furnishedTv.text = post.home.isFurnished.toString()
                kitchenTv.text = post.home.isAmericanKitchen.toString()
            }

            viewModel.favorites.observe(viewLifecycleOwner) {
                if (it.contains(viewModel.args.value?.post?.id)) {
                    favBtn.setImageResource(R.drawable.round_favorite_24)
                } else {
                    favBtn.setImageResource(R.drawable.round_favorite_border_24)
                }
            }

            if (navArgs.isMyPost) {
                favBtn.setImageResource(R.drawable.round_create_16)
            }

            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }

            favBtn.setOnClickListener {
                if (navArgs.isMyPost) {
                    //TODO: Edit post
                } else {
                    viewModel.toggleFavorite(viewModel.args.value?.post?.id!!)
                }
            }

            contactBtn.setOnClickListener {
                if (!navArgs.isMyPost && viewModel.args.value?.post?.ownerId != viewModel.uid) {
                    findNavController().navigate(
                        DetailFragmentDirections.actionDetailFragmentToChatFragment(
                            viewModel.args.value?.post?.ownerId!!,
                            viewModel.args.value?.post?.ownerPicture!!,
                            viewModel.args.value?.post?.ownerName!!
                        )
                    )

                }
            }
        }

        return binding.root
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setUpMap()
        //Map interaction is disabled
        map.uiSettings.setAllGesturesEnabled(false)
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return
        }
        map.isMyLocationEnabled = false
        map.clear()
        val currentLatLng = LatLng(
            navArgs.post?.home?.address?.latLng?.latitude!!,
            navArgs.post?.home?.address?.latLng?.longitude!!
        )
        map.addMarker(MarkerOptions().position(currentLatLng))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

    }

    // MapView lifecycle methods
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }
}