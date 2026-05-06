package com.example.yatrimitrasharedmobilityapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object Login : Screen()
    @Serializable
    object Registration : Screen()
    @Serializable
    object Dashboard : Screen()
    @Serializable
    object Tracking : Screen()
    @Serializable
    object Profile : Screen()
    @Serializable
    object Settings : Screen()
    @Serializable
    object EmergencyContacts : Screen()
    @Serializable
    object PetrolPumps : Screen()
    @Serializable
    object Routes : Screen()
    @Serializable
    object BookRide : Screen()
}
