package com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.presentation

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.presentation.model.LocationData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(viewModel: LocationViewModel) {

    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val locationState = viewModel.locationState.collectAsState()

    LaunchedEffect(Unit) {
        if (viewModel.hasLocationPermission()) {
            viewModel.fetchLocationUpdates()
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        locationState.value?.let { locationData ->
            LocationDisplay(locationData)
        } ?: run {
            Text("Waiting for location...")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.fetchLastKnownLocation()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Get Last Known Location")
        }
    }
}

@Composable
fun LocationDisplay(locationData: LocationData) {
    Column (verticalArrangement = Arrangement.spacedBy(8.dp)){
        BasicText(text = "Latitude: ${locationData.location.latitude}")
        BasicText(text = "Longitude: ${locationData.location.longitude}")
        BasicText(text = "Address: ${locationData.name}")
    }
}
