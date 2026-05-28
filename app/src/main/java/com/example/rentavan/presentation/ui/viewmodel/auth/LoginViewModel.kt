package com.example.rentavan.presentation.ui.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.auth.LoginRequest
import com.example.rentavan.data.network.RetrofitClient
import com.example.rentavan.data.repository.auth.AuthRepository
import com.example.rentavan.data.sessions.UserSession
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
        if (usuario.isBlank() || contrasena.isBlank()) {
            mensajeError = "Por favor, rellena todos los campos"
            errorVisible = true
            return
        }
        viewModelScope.launch {

            isLoading = true; errorVisible = false
            val resultado = repository.login(LoginRequest(email = usuario.trim(), password = contrasena))
            resultado.onSuccess { response ->
                isLoading = false
                if (response.exito) {
                    UserSession.idUsuario = response.idUsuario
                    UserSession.nombre    = response.nombre
                    UserSession.email     = usuario.trim()
                    loginExitoso = true
                } else {
                    mensajeError = response.mensaje; errorVisible = true
                }
            }.onFailure {
                isLoading = false
                mensajeError = "Error de conexión con el servidor"; errorVisible = true
            }
        }
    }
}
