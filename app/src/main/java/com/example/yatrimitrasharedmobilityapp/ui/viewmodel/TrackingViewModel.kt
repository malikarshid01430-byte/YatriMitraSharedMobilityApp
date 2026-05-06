package com.example.yatrimitrasharedmobilityapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yatrimitrasharedmobilityapp.data.repository.VehicleRepository
import com.example.yatrimitrasharedmobilityapp.domain.model.Vehicle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {
    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles: StateFlow<List<Vehicle>> = _vehicles.asStateFlow()

    private val _etaMap = MutableStateFlow<Map<String, Int>>(emptyMap())
    val etaMap: StateFlow<Map<String, Int>> = _etaMap.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getVehicles().collect { vehicleList ->
                _vehicles.value = vehicleList
                val newEtas = vehicleList.associate { it.id to repository.calculateETA(it) }
                _etaMap.value = newEtas
            }
        }
    }
}
