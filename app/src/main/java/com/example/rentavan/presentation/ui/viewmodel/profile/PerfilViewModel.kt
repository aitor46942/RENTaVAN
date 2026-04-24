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

    // Estado que guarda los datos del usuario (puede ser nulo mientras carga)
    var perfilUsuario by mutableStateOf<UsuarioPerfil?>(null)
        private set

    // Estado para mostrar el círculo de carga
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
                // Manejo de errores básico
                isLoading = false
            }
        }
    }
}