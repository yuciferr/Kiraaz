package com.example.kiraaz.ui.posts.newpost

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
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

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_postAddressFragment_to_postDetailFragment)
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

        var address = ""
        try {
            val addressList = geocoder.getFromLocation(currentLatLng.latitude, currentLatLng.longitude, 1)
            if (addressList?.size!! > 0) {

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