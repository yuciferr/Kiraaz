package com.example.kiraaz.ui.detail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kiraaz.databinding.FragmentDetailBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentDetailBinding
    private val navArgs by navArgs<DetailFragmentArgs>()

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        binding.apply {
            val post = navArgs.post
            val images = post.home.images
            val adapter = DetailViewPagerAdapter(images)
            viewPager.adapter = adapter
            titleTv.text = post.title
            priceTv.text = post.price.toString()
            val location = post.home.address.district + ", " + post.home.address.city
            locationTv.text = location
            descriptionTv.text = post.description
            profileIv.setImageURI(post.ownerPicture.toUri())
            roommateTv.text = post.roommate.toString()
            roomTv.text = post.home.rooms
            floorTv.text = post.home.floor.toString()
            depositTv.text = post.deposit.toString()
            furnishedTv.text = post.home.isFurnished.toString()
            kitchenTv.text = post.home.isAmericanKitchen.toString()

            backBtn.setOnClickListener {
                findNavController().navigateUp()
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
            navArgs.post.home.address.latLng.latitude,
            navArgs.post.home.address.latLng.longitude
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