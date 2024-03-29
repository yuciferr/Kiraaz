package com.example.kiraaz.ui.posts.newpost

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentPostAddressBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import java.util.*

@Suppress("DEPRECATION")
class PostAddressFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentPostAddressBinding
    private lateinit var viewModel: SharedViewModel

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
        binding = FragmentPostAddressBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.nextBtn.setOnClickListener {
            val address = binding.addressTv.text.toString()
            val city = binding.cityTv.text.toString()
            val district = binding.districtTv.text.toString()
            val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)

            viewModel.setAddress(address, city, district, latLng)

            findNavController().navigate(R.id.action_postAddressFragment_to_postDetailFragment)
        }
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setUpMap()
        map.setOnMapLongClickListener(listener)

    }

    private fun setUpMap(){
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
        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                addMarker(currentLatLng)
            }
        }
    }

    private fun addMarker(currentLatLng: LatLng){
        map.clear()
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        lastLocation.latitude = currentLatLng.latitude
        lastLocation.longitude = currentLatLng.longitude

        var address = ""
        var city = ""
        var district = ""
        try {
            val addressList = geocoder.getFromLocation(currentLatLng.latitude, currentLatLng.longitude, 1)
            if (addressList?.size!! > 0) {

                if (addressList[0].subAdminArea != null) {
                    district = addressList[0].subAdminArea
                }
                if (addressList[0].adminArea != null) {
                    city = addressList[0].adminArea
                }

                if (addressList[0].thoroughfare != null) {
                    address += addressList[0].thoroughfare
                    if (addressList[0].subThoroughfare != null) {
                        address += addressList[0].subThoroughfare

                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.addressTv.setText(address)
        binding.cityTv.setText(city)
        binding.districtTv.setText(district)

        map.addMarker(MarkerOptions().position(currentLatLng).title(address))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
    }

    private val listener = GoogleMap.OnMapLongClickListener { p0 ->
        addMarker(p0)
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