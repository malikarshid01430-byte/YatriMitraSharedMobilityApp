package com.example.yatrimitrasharedmobilityapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yatrimitrasharedmobilityapp.data.model.UserEntity
import com.example.yatrimitrasharedmobilityapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val user: StateFlow<UserEntity?> = userRepository.getUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun updateUser(name: String, email: String, phone: String) {
        viewModelScope.launch {
            user.value?.let {
                userRepository.saveUser(it.copy(name = name, email = email, phone = phone))
            }
        }
    }
}
