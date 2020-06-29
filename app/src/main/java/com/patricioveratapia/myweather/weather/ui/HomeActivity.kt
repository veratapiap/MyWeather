package com.patricioveratapia.myweather.weather.ui

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.patricioveratapia.myweather.R
import java.util.*


class HomeActivity : BaseActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentCity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        requestLocationPermission()
    }

    fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    this,
                    ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getCurrentLocation()
            } else {
                requestPermissions(
                    arrayOf(
                        ACCESS_COARSE_LOCATION,
                        ACCESS_FINE_LOCATION
                    ),
                    LOCATION_PERMISSION
                )
            }
        } else {
            getCurrentLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val addresses: List<Address>

                    val geoCoder = Geocoder(this, Locale.getDefault())

                    try {
                        addresses =
                            geoCoder.getFromLocation(location.latitude, location.longitude, 1)

                        if (addresses.isNotEmpty()) {
                            currentCity = addresses[0].locality

                            replaceFragment(HomeFragment.newInstance(currentCity))

                        }
                    } catch (e: Exception) {

                        replaceFragment(HomeFragment.newInstance())

                    }
                } else {

                    replaceFragment(HomeFragment.newInstance())

                }
            }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu items for use in the action bar
        val inflater = menuInflater

        inflater.inflate(R.menu.action_select_city, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        return when (item.itemId) {
            R.id.action_change_location -> {

                val fragment =
                    supportFragmentManager.findFragmentById(R.id.activity_base_fragment_container)

                (fragment as HomeFragment).showCitySelectionDialog()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getCurrentLocation()
                } else {

                    replaceFragment(HomeFragment.newInstance())

                }
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION = 0

    }
}
