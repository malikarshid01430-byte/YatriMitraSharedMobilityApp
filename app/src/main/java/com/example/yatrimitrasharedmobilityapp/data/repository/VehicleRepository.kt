package com.example.yatrimitrasharedmobilityapp.data.repository

import com.example.yatrimitrasharedmobilityapp.domain.model.LatLng
import com.example.yatrimitrasharedmobilityapp.domain.model.Vehicle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.*

@Singleton
class VehicleRepository @Inject constructor() {
    private val vehicles = mutableListOf(
        Vehicle("1", "Shared Auto 1", 12.9716, 77.5946, 30.0),
        Vehicle("2", "Shared Auto 2", 12.9750, 77.6000, 25.0),
        Vehicle("3", "Shared Auto 3", 12.9600, 77.5800, 35.0)
    )

    // Destination for simulation
    private val destination = LatLng(12.9800, 77.6100)

    fun getVehicles(): Flow<List<Vehicle>> = flow {
        while (true) {
            // Update vehicle positions
            for (i in vehicles.indices) {
                val v = vehicles[i]
                val newPos = moveTowards(v.latitude, v.longitude, destination.lat, destination.lng, v.speedKph / 3600.0)
                vehicles[i] = v.copy(latitude = newPos.lat, longitude = newPos.lng)
            }
            emit(vehicles.toList())
            delay(1000) // Update every second
        }
    }

    private fun moveTowards(lat1: Double, lon1: Double, lat2: Double, lon2: Double, distanceKm: Double): LatLng {
        val dLat = lat2 - lat1
        val dLon = lon2 - lon1
        val distance = sqrt(dLat * dLat + dLon * dLon)
        if (distance < 0.0001) return LatLng(lat1, lon1)
        
        val ratio = (distanceKm / 111.0) / distance // very rough conversion
        val moveLat = dLat * min(1.0, ratio)
        val moveLon = dLon * min(1.0, ratio)
        
        return LatLng(lat1 + moveLat, lon1 + moveLon)
    }

    fun calculateETA(vehicle: Vehicle): Int {
        val distance = haversine(vehicle.latitude, vehicle.longitude, destination.lat, destination.lng)
        return (distance / vehicle.speedKph * 3600).toInt() // returns seconds
    }

    private fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }
}
