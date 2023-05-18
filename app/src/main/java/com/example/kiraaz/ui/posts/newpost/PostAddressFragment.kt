package com.example.kiraaz.ui.posts.newpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kiraaz.R
import com.example.kiraaz.databinding.FragmentPostAddressBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*

class PostAddressFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentPostAddressBinding

    private lateinit var mapView: MapView

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

        binding.nextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_postAddressFragment_to_postDetailFragment)
        }
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val location = LatLng(41.0082, 28.9784)
        googleMap.addMarker(MarkerOptions().position(location).title("İstanbul"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))
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

}