package com.example.yatrimitrasharedmobilityapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class EmergencyContact(val name: String, val number: String, val category: String)

@Composable
fun EmergencyContactsScreen() {
    val contacts = listOf(
        EmergencyContact("Police", "100", "Public Safety"),
        EmergencyContact("Ambulance", "102", "Medical"),
        EmergencyContact("Fire Brigade", "101", "Public Safety"),
        EmergencyContact("Women Helpline", "1091", "Safety"),
        EmergencyContact("Roadside Assistance", "1800-XXX-XXXX", "Automotive"),
        EmergencyContact("Yatri Mitra Support", "080-XXXX-XXXX", "App Support")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFDECEA))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Emergency, contentDescription = null, tint = Color.Red, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Emergency Services",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(contacts) { contact ->
                EmergencyContactItem(contact)
            }
        }
    }
}

@Composable
fun EmergencyContactItem(contact: EmergencyContact) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(contact.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(contact.category, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(contact.number, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
            }
            FilledIconButton(
                onClick = { /* Call intent placeholder */ },
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Call, contentDescription = "Call ${contact.name}")
            }
        }
    }
}
