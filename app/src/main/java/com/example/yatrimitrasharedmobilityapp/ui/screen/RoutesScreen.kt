package com.example.yatrimitrasharedmobilityapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class SavedRoute(val from: String, val to: String, val duration: String, val traffic: String)

@Composable
fun RoutesScreen() {
    val routes = listOf(
        SavedRoute("Home", "Office", "45 mins", "High Traffic"),
        SavedRoute("Office", "Gym", "15 mins", "Smooth"),
        SavedRoute("Home", "Airport", "1 hr 10 mins", "Moderate"),
        SavedRoute("Gym", "Mall", "10 mins", "Smooth")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Saved Routes", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(routes) { route ->
                RouteItem(route)
            }
        }
    }
}

@Composable
fun RouteItem(route: SavedRoute) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Route, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${route.from} → ${route.to}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(route.duration, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(route.traffic, style = MaterialTheme.typography.bodySmall, color = if(route.traffic == "Smooth") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
                }
            }
            IconButton(onClick = { /* Navigate */ }) {
                Icon(Icons.Default.Navigation, contentDescription = "Start Navigation", tint = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}
