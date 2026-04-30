package com.example.rentavan.presentation.ui.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.auth.LoginRequest
import com.example.rentavan.data.network.RetrofitClient
import com.example.rentavan.data.repository.auth.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val repository = AuthRepository(RetrofitClient.apiService)

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

    fun onUsuarioChange(nuevoUsuario: String) { usuario = nuevoUsuario }
    fun onContrasenaChange(nuevaContrasena: String) { contrasena = nuevaContrasena }

    fun realizarLogin() {
        // Usamos los valores actuales de las variables 'usuario' y 'contrasena'[cite: 15]
        val email = usuario
        val pass = contrasena

        if (email.isBlank() || pass.isBlank()) {
            mensajeError = "Por favor, rellena todos los campos"
            errorVisible = true
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorVisible = false

            val request = LoginRequest(email, pass)
            // Llamada al repositorio que conecta con el BCrypt del backend[cite: 12, 15]
            val resultado = repository.login(request)

            resultado.onSuccess { response ->
                isLoading = false
                if (response.exito) {
                    loginExitoso = true
                    // El ID del usuario nos servirá para gestionar sus futuras reservas
                } else {
                    mensajeError = response.mensaje
                    errorVisible = true
                }
            }.onFailure { error ->
                isLoading = false
                mensajeError = "Error de conexión con el servidor"
                errorVisible = true
            }
        }
    }
}
