package com.example.yatrimitrasharedmobilityapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class PetrolPump(val name: String, val distance: String, val rating: Double, val address: String)

@Composable
fun PetrolPumpsScreen() {
    val pumps = listOf(
        PetrolPump("Indian Oil - Koramangala", "0.8 km", 4.2, "100ft Road, Koramangala"),
        PetrolPump("Bharat Petroleum - HSR", "2.1 km", 4.5, "27th Main, HSR Layout"),
        PetrolPump("Shell - Bellandur", "3.5 km", 4.8, "Outer Ring Road"),
        PetrolPump("HP Petrol - Indiranagar", "5.2 km", 4.1, "12th Main Road"),
        PetrolPump("Jio-bp Pulse - Whitefield", "7.8 km", 4.6, "ITPL Main Road")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(pumps) { pump ->
                PetrolPumpItem(pump)
            }
        }
    }
}

@Composable
fun PetrolPumpItem(pump: PetrolPump) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocalGasStation, contentDescription = null, tint = Color(0xFFFF9800))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(pump.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Text(pump.distance, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            Text(pump.address, style = MaterialTheme.typography.bodySmall)
            
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(pump.rating.toString(), style = MaterialTheme.typography.bodyMedium)
                }
                Button(
                    onClick = { /* Navigate placeholder */ },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Navigate")
                }
            }
        }
    }
}
