package com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.domain

import javax.inject.Inject

class GetAddress @Inject constructor(
    private val repository: LocationHandlerRepository
) {
    operator fun invoke(
        lat: Double, lng: Double
    ) = repository.getAddress(lat, lng)
}