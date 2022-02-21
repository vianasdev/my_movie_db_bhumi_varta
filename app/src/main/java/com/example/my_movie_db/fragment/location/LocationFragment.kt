package com.example.my_movie_db.fragment.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.my_movie_db.databinding.FragmentLocationBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import dagger.android.support.DaggerFragment
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MinimapOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import timber.log.Timber
import javax.inject.Inject


class LocationFragment : DaggerFragment() {

    companion object {
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 0
        const val REQUEST_CODE_CHECK_LOCATION_SETTINGS = 1
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LocationViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: FragmentLocationBinding

    private var locationManager: LocationManager? = null
    private lateinit var locationListener: LocationListener
    private var location: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Configuration.getInstance().userAgentValue =
            requireContext().applicationContext.packageName

        viewDataBinding = FragmentLocationBinding.inflate(inflater)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewModel = viewModel

        observeViewModel()
        if (isPermissionsGranted()) {
            checkGps()
        }

        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewDataBinding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewDataBinding.map.onPause()
    }

    private fun observeViewModel() {

    }

    private fun setupMap(map: MapView) {
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), map)
        map.overlays.add(locationOverlay)

        val rotationGestureOverlay = RotationGestureOverlay(requireContext(), map)
        rotationGestureOverlay.isEnabled = true
        map.setMultiTouchControls(true)
        map.overlays.add(rotationGestureOverlay)

        val dm: DisplayMetrics = requireContext().resources.displayMetrics
        val minimapOverlay = MinimapOverlay(context, map.tileRequestCompleteHandler)
        minimapOverlay.width = dm.widthPixels / 5
        minimapOverlay.height = dm.heightPixels / 5
        map.overlays.add(minimapOverlay)

        val mapController = map.controller
        mapController.setZoom(15)
        if (location != null) {
            mapController.setCenter(GeoPoint(location!!.latitude, location!!.longitude))
            viewModel.setLocation(location!!)
            locationManager!!.removeUpdates(locationListener)
        }

    }

    private fun checkGps() {
        val manager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER).not()) {
            turnOnGPS()
        } else {
            findLocation()
            setupMap(viewDataBinding.map)
        }
    }

    private fun turnOnGPS() {
        val request = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(request)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(
                        requireActivity(),
                        REQUEST_CODE_CHECK_LOCATION_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    Timber.i(sendEx)
                }
            }
        }.addOnSuccessListener {
            findLocation()
            setupMap(viewDataBinding.map)
        }
    }


    private fun isPermissionsGranted(): Boolean {
        var result = true
        if (
            (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
            or
            (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
            or
            (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
            or
            (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
            or
            (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                ),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
            result = false
        }
        return result
    }

    @SuppressLint("MissingPermission")
    private fun findLocation() {
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        locationListener = LocationListener {
            location = it
            setupMap(viewDataBinding.map)
        }
        locationManager!!.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,
            10f,
            locationListener
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            onLocationPermissionResult(grantResults)
        }
    }


    private fun onLocationPermissionResult(grantResults: IntArray) {
        var grants = true
        for (granted in grantResults) {
            if (granted != PackageManager.PERMISSION_GRANTED) {
                grants = false
            }
        }

        if (grants)
            checkGps()
        else Toast.makeText(requireContext(), "We need permissions", Toast.LENGTH_SHORT).show()
    }
}