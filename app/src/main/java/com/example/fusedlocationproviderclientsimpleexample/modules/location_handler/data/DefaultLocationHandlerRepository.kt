package com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.domain.LocationHandlerRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import java.util.Locale


class DefaultLocationHandlerRepository(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationHandlerRepository {

    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(): Flow<Location> = callbackFlow {

        val locationRequest = LocationRequest.Builder(
            /* priority = */  Priority.PRIORITY_HIGH_ACCURACY,
            /* intervalMillis = */ 10000L,
        ).build()

        val locationCallback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                for (location in locationResult.locations) {
                    trySend(location).isSuccess
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    @SuppressLint("MissingPermission")
    override fun getLastKnownLocation(): Location? {
        return try {
            fusedLocationClient.lastLocation.result
        } catch (e: Exception) {
            null
        }
    }

    // Handle permission checking
    override fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    override fun getAddress(lat: Double, lng: Double) : String {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            // Get a list of addresses from the Geocoder
            val addresses: MutableList<Address>? = geocoder.getFromLocation(lat, lng, 1)

            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                // Build a string with address details
                val fullAddress = StringBuilder().apply {
                    appendLine(address.getAddressLine(0)) // Full address
                    appendLine(address.locality) // City
                    appendLine(address.adminArea) // State
                    appendLine(address.countryName) // Country
                    appendLine(address.postalCode) // Postal code
                }.toString().trim()

                fullAddress
            } else {
                "No address found"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Unable to retrieve address"
        }
    }

}
