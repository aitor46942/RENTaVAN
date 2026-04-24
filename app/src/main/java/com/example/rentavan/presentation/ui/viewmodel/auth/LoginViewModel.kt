package com.example.rentavan.presentation.ui.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.auth.LoginRequest
import com.example.rentavan.data.repository.auth.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = AuthRepository()

    // Estados que antes estaban en la UI
    var usuario by mutableStateOf("")
        private set
    var contrasena by mutableStateOf("")
        private set
    var errorVisible by mutableStateOf(false)
        private set
    var mensajeError by mutableStateOf("")
        private set
    var isLoading by mutableStateOf(false)
        private set
    var loginExitoso by mutableStateOf(false)
        private set

    // Funciones para que la UI actualice el estado
    fun onUsuarioChange(nuevoUsuario: String) { usuario = nuevoUsuario }
    fun onContrasenaChange(nuevaContrasena: String) { contrasena = nuevaContrasena }

    // Lógica de negocio
    fun login() {
        if (usuario.isBlank() || contrasena.isBlank()) {
            errorVisible = true
            mensajeError = "Usuario y contraseña son obligatorios"
            return
        }
        errorVisible = false
        isLoading = true

        // Lanzamos una corrutina para hacer la llamada asíncrona
        viewModelScope.launch {
            val request = LoginRequest(usuario, contrasena)
            val result = repository.login(request)

            result.onSuccess {
                isLoading = false
                loginExitoso = true // Esto avisará a la UI para que navegue
            }.onFailure { exception ->
                isLoading = false
                errorVisible = true
                mensajeError = exception.message ?: "Error de conexión"
            }
        }
    }
}