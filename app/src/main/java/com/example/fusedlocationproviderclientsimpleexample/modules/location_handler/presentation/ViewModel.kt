package com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.presentation

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.domain.GetAddress
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.domain.GetLasKnownLocation
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.domain.GetLocationUpdates
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.domain.HasLocationPermission
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.presentation.model.LocationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getLocationUpdates: GetLocationUpdates,
    private val getLasKnownLocation: GetLasKnownLocation,
    private val checkLocationPermission: HasLocationPermission,
    private val getAddress: GetAddress
) : ViewModel() {

    private val _locationState = MutableStateFlow<LocationData?>(null)
    val locationState = _locationState.asStateFlow()

    fun fetchLocationUpdates() {
        viewModelScope.launch {
            getLocationUpdates().collect { location ->
                val locationName = getLocationName(location)
                _locationState.value = LocationData(location, locationName)
            }
        }
    }

    fun fetchLastKnownLocation() {
        viewModelScope.launch {
            val location = getLasKnownLocation()
            if (location != null) {
                val locationName = getLocationName(location)
                _locationState.value = LocationData(location, locationName)
            } else _locationState.value = null
        }
    }

    fun hasLocationPermission(): Boolean {
        return checkLocationPermission()
    }

    private fun getLocationName(location: Location): String {
        return getAddress(location.longitude, location.longitude)
    }
}
