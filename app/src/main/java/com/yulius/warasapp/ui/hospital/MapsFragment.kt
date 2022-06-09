package com.yulius.warasapp.ui.hospital

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.yulius.warasapp.R
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.databinding.FragmentMapsBinding
import com.yulius.warasapp.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


class MapsFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    private lateinit var currentLocation: LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: MapsViewModel
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var placesClient : PlacesClient

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.uiSettings.apply   {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
            isIndoorLevelPickerEnabled = true
        }

        getMyLocation()

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        currentLocation = LatLng(-6.748673,111.037923)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if(!Places.isInitialized()){
            Places.initialize(requireContext(),getString(R.string.google_maps_key))
        }

        placesClient = Places.createClient(requireContext())

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
            LatLng(-6.748673,111.037923),
            LatLng(-6.748673,111.037923)
        ))

        autocompleteFragment.setCountries("ID")

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                if(place.latLng != null){
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng!!, 20f))
                    mMap.addMarker(MarkerOptions().position(place.latLng!!).title(place.name))

                }
            }

            override fun onError(status: Status) {
                Toast.makeText(requireContext(), "An error occurred: $status", Toast.LENGTH_SHORT).show()
            }
        })

        setupViewModel()

    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(
                    UserPreference.getInstance(
                        requireContext().dataStore
                    )
                )
            )[MapsViewModel::class.java]
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
                    viewModel.setMaps(currentLocation)
                    viewModel.getMaps().observe(viewLifecycleOwner){
                        for(i in it.indices){
                            val position = LatLng(it[i].geometry.location.lat, it[i].geometry.location.lng)
                            mMap.addMarker(MarkerOptions().position(position).title(it[i].name))
                        }
                    }
                } else {
                    Toast.makeText(context, "Need Permission", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }


    }
}