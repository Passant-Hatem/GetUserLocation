package com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.domain

import javax.inject.Inject

class GetLasKnownLocation @Inject constructor(
    private val repository: LocationHandlerRepository
) {
    operator fun invoke() = repository.getLastKnownLocation()
}