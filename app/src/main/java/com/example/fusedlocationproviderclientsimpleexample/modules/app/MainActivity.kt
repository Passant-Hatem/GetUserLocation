package com.example.fusedlocationproviderclientsimpleexample.modules.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fusedlocationproviderclientsimpleexample.modules.core.ui.theme.FusedLocationProviderClientSimpleExampleTheme
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.presentation.LocationScreen
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.presentation.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: LocationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FusedLocationProviderClientSimpleExampleTheme {
                LocationScreen(viewModel)
            }
        }
    }
}