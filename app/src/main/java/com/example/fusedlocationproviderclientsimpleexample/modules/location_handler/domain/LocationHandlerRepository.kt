package com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.domain

import android.location.Location
import kotlinx.coroutines.flow.Flow


interface LocationHandlerRepository {
    fun getLocationUpdates(): Flow<Location>

    fun getLastKnownLocation(): Location?

    fun hasLocationPermission(): Boolean

    fun getAddress(lat: Double, lng: Double): String
}