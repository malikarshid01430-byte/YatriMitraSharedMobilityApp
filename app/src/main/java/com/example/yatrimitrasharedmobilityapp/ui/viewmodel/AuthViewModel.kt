package com.example.yatrimitrasharedmobilityapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yatrimitrasharedmobilityapp.data.model.UserEntity
import com.example.yatrimitrasharedmobilityapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getUser().collect { user ->
                _user.value = user
                _isLoggedIn.value = user != null
            }
        }
    }

    fun login(email: String, name: String) {
        viewModelScope.launch {
            val newUser = UserEntity(
                id = "user_123",
                name = name,
                email = email,
                phone = "+91 9876543210"
            )
            userRepository.saveUser(newUser)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}
