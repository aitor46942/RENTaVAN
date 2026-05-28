package com.example.rentavan.presentation.ui.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.profile.UsuarioPerfil
import com.example.rentavan.data.repository.profile.ProfileRepository
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {
    private val repository = ProfileRepository()

    var perfilUsuario by mutableStateOf<UsuarioPerfil?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        cargarPerfil()
    }

    private fun cargarPerfil() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.obtenerPerfilUsuario()
            result.onSuccess { datos ->
                perfilUsuario = datos
                isLoading = false
            }.onFailure {
                isLoading = false
            }
        }
    }
}