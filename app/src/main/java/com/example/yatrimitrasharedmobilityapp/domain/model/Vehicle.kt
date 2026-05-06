package com.example.yatrimitrasharedmobilityapp.domain.model

data class Vehicle(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val speedKph: Double, // km/h
    val type: VehicleType = VehicleType.AUTO
)

enum class VehicleType {
    AUTO, BUS, CAB
}

data class LatLng(val lat: Double, val lng: Double)
