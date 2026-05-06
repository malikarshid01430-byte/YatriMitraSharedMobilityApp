package com.example.yatrimitrasharedmobilityapp.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.yatrimitrasharedmobilityapp.ui.viewmodel.TrackingViewModel

@Composable
fun TrackingScreen(viewModel: TrackingViewModel = hiltViewModel()) {
    val vehicles by viewModel.vehicles.collectAsState()
    val etas by viewModel.etaMap.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
        // Map Simulation Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Color(0xFFE0F2F1))
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Draw grid lines for map feel
                val step = 100f
                for (i in 0..(size.width / step).toInt()) {
                    drawLine(Color.White, Offset(i * step, 0f), Offset(i * step, size.height), 2f)
                }
                for (i in 0..(size.height / step).toInt()) {
                    drawLine(Color.White, Offset(0f, i * step), Offset(size.width, i * step), 2f)
                }

                // Draw main route
                drawLine(
                    color = Color.DarkGray,
                    start = Offset(50f, 50f),
                    end = Offset(size.width - 50f, size.height - 50f),
                    strokeWidth = 8f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
                )
                
                // Draw vehicles
                vehicles.forEach { vehicle ->
                    val x = ((vehicle.longitude - 77.58) / 0.04 * size.width).toFloat()
                    val y = ((vehicle.latitude - 12.96) / 0.04 * size.height).toFloat()
                    
                    drawCircle(
                        color = if (vehicle.speedKph > 0) Color(0xFF4CAF50) else Color.Red,
                        radius = 20f,
                        center = Offset(x, y)
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 8f,
                        center = Offset(x, y)
                    )
                }
            }
            
            // Legend
            Card(
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).background(Color(0xFF4CAF50), RoundedCornerShape(5.dp)))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Moving", style = MaterialTheme.typography.labelSmall)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).background(Color.Red, RoundedCornerShape(5.dp)))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Stopped", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
        
        // Active Vehicles List
        Column(modifier = Modifier.weight(0.6f).padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Active Vehicles",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${vehicles.size} found",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(vehicles) { vehicle ->
                    val eta = etas[vehicle.id] ?: 0
                    ProfessionalVehicleItem(vehicle, eta)
                }
            }
        }
    }
}

@Composable
fun ProfessionalVehicleItem(vehicle: com.example.yatrimitrasharedmobilityapp.domain.model.Vehicle, etaSeconds: Int) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Navigation,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(vehicle.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Current Speed: ${vehicle.speedKph} km/h", style = MaterialTheme.typography.bodySmall)
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${etaSeconds / 60}m ${etaSeconds % 60}s",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Bold
                )
                Text("ETA", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
