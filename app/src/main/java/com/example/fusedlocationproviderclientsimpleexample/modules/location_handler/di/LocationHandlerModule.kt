package com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.di

import android.content.Context
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.data.DefaultLocationHandlerRepository
import com.example.fusedlocationproviderclientsimpleexample.modules.location_handler.domain.LocationHandlerRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationHandlerModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        @ApplicationContext context: Context,
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationHandlerRepository {
        return DefaultLocationHandlerRepository(context, fusedLocationProviderClient)
    }
}
