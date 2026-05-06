package com.example.yatrimitrasharedmobilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yatrimitrasharedmobilityapp.ui.navigation.Screen
import com.example.yatrimitrasharedmobilityapp.ui.screen.*
import com.example.yatrimitrasharedmobilityapp.ui.theme.YatriMitraSharedMobilityAppTheme
import com.example.yatrimitrasharedmobilityapp.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YatriMitraSharedMobilityAppTheme {
                MainApp()
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(authViewModel: AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    val canNavigateBack = navController.previousBackStackEntry != null
    
    // Improved route detection for type-safe navigation
    val currentRouteName = currentDestination?.route?.split(".")?.lastOrNull()?.split("?")?.firstOrNull() ?: ""

    Scaffold(
        topBar = {
            if (isLoggedIn || currentRouteName == "Registration") {
                TopAppBar(
                    title = {
                        Text(
                            text = when (currentRouteName) {
                                "Dashboard" -> "Yatri Mitra"
                                "Tracking" -> "Real-time Tracking"
                                "Profile" -> "My Profile"
                                "Settings" -> "Settings"
                                "EmergencyContacts" -> "Emergency Contacts"
                                "PetrolPumps" -> "Nearby Petrol Pumps"
                                "Routes" -> "Routes"
                                "BookRide" -> "Book a Ride"
                                "Registration" -> "Create Account"
                                else -> "Yatri Mitra"
                            },
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    navigationIcon = {
                        if (canNavigateBack && currentRouteName != "Dashboard") {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        },
        bottomBar = {
            if (isLoggedIn && shouldShowBottomBar(currentDestination)) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    val items = listOf(
                        NavigationItem("Home", Screen.Dashboard, Icons.Default.Home),
                        NavigationItem("Track", Screen.Tracking, Icons.Default.LocationOn),
                        NavigationItem("Emergency", Screen.EmergencyContacts, Icons.Default.Emergency),
                        NavigationItem("Profile", Screen.Profile, Icons.Default.Person)
                    )
                    items.forEach { item ->
                        val isSelected = currentDestination?.hierarchy?.any {
                            it.route?.contains(item.screen::class.simpleName ?: "") == true
                        } == true

                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.screen) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isLoggedIn) Screen.Dashboard else Screen.Login,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.Login> {
                LoginScreen(
                    onLoginSuccess = { email, password -> 
                        authViewModel.login(email, "User")
                    },
                    onNavigateToRegistration = { navController.navigate(Screen.Registration) }
                )
            }
            composable<Screen.Registration> {
                RegistrationScreen(
                    onRegistrationSuccess = { email, name -> 
                        authViewModel.login(email, name)
                    },
                    onNavigateToLogin = { navController.popBackStack() }
                )
            }
            composable<Screen.Dashboard> {
                DashboardScreen(
                    onNavigateToTracking = { navController.navigate(Screen.Tracking) },
                    onNavigateToProfile = { navController.navigate(Screen.Profile) },
                    onNavigateToSettings = { navController.navigate(Screen.Settings) },
                    onNavigateToEmergency = { navController.navigate(Screen.EmergencyContacts) },
                    onNavigateToPetrolPumps = { navController.navigate(Screen.PetrolPumps) },
                    onNavigateToRoutes = { navController.navigate(Screen.Routes) },
                    onNavigateToBookRide = { navController.navigate(Screen.BookRide) }
                )
            }
            composable<Screen.Tracking> { TrackingScreen() }
            composable<Screen.Profile> { ProfileScreen() }
            composable<Screen.Settings> { 
                SettingsScreen(onLogout = { authViewModel.logout() }) 
            }
            composable<Screen.EmergencyContacts> { EmergencyContactsScreen() }
            composable<Screen.PetrolPumps> { PetrolPumpsScreen() }
            composable<Screen.Routes> { RoutesScreen() }
            composable<Screen.BookRide> { BookRideScreen() }
        }
    }
}

private fun shouldShowBottomBar(destination: androidx.navigation.NavDestination?): Boolean {
    return destination?.hierarchy?.any {
        it.route?.contains("Login") == false && it.route?.contains("Registration") == false
    } ?: false
}

data class NavigationItem(val label: String, val screen: Screen, val icon: ImageVector)
