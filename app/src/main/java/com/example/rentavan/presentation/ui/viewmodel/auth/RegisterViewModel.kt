package com.example.rentavan.presentation.ui.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.auth.RegisterRequest
import com.example.rentavan.data.repository.auth.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = AuthRepository()

    // Estados de la UI
    var usuario by mutableStateOf("")
        private set
    var correo by mutableStateOf("")
        private set
    var nombre by mutableStateOf("")
        private set
    var apellidos by mutableStateOf("")
        private set
    var contrasena by mutableStateOf("")
        private set

    // Estados de control
    var errorVisible by mutableStateOf(false)
        private set
    var mensajeError by mutableStateOf("")
        private set
    var isLoading by mutableStateOf(false)
        private set
    var registroExitoso by mutableStateOf(false)
        private set

    // Funciones de actualización
    fun onUsuarioChange(it: String) { usuario = it }
    fun onCorreoChange(it: String) { correo = it }
    fun onNombreChange(it: String) { nombre = it }
    fun onApellidosChange(it: String) { apellidos = it }
    fun onContrasenaChange(it: String) { contrasena = it }

    fun register() {
        if (usuario.isBlank() || correo.isBlank() || nombre.isBlank() ||
            apellidos.isBlank() || contrasena.isBlank()) {
            errorVisible = true
            mensajeError = "Todos los campos son obligatorios"
            return
        }

        errorVisible = false
        isLoading = true

        viewModelScope.launch {
            val request = RegisterRequest(usuario, correo, nombre, apellidos, contrasena)
            val result = repository.register(request)

            result.onSuccess {
                isLoading = false
                registroExitoso = true // Lanza la navegación
            }.onFailure { exception ->
                isLoading = false
                errorVisible = true
                mensajeError = exception.message ?: "Error al registrar"
            }
        }
    }
}