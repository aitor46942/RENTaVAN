package com.example.rentavan.presentation.ui.viewmodel.auth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.auth.RegisterRequest // Asegúrate de tener este modelo
import com.example.rentavan.data.network.RetrofitClient
import com.example.rentavan.data.repository.auth.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = AuthRepository(RetrofitClient.apiService)


    var usuario by mutableStateOf("")
        private set
    var nombre by mutableStateOf("")
        private set
    var apellidos by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var telefono by mutableStateOf("")
        private set
    var contrasena by mutableStateOf("")
        private set

    // Estados de control de la UI
    var isLoading by mutableStateOf(false)
        private set
    var errorVisible by mutableStateOf(false)
        private set
    var mensajeError by mutableStateOf("")
        private set
    var registroExitoso by mutableStateOf(false)
        private set

    // Funciones de actualización de estado (Unresolved references corregidas)
    fun onNombreChange(newValue: String) { nombre = newValue }
    fun onUsuarioChange(newValue: String) { usuario = newValue }
    fun onApellidosChange(newValue: String) { apellidos = newValue }
    fun onEmailChange(newValue: String) { email = newValue }
    fun onTelefonoChange(newValue: String) { telefono = newValue }
    fun onContrasenaChange(newValue: String) { contrasena = newValue }

    // Lógica de Registro
    fun realizarRegistro() {
        Log.d("DEBUG_APP", "Botón pulsado, iniciando llamada a red...")
        if (nombre.isBlank() || email.isBlank() || contrasena.isBlank()) {
            mensajeError = "Por favor, completa los campos obligatorios"
            errorVisible = true
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorVisible = false

            // Enviamos los datos al backend para el cifrado con BCrypt
            val request = RegisterRequest(nombre, email, telefono, contrasena)
            val resultado = repository.registrar(request)

            resultado.onSuccess {
                isLoading = false
                registroExitoso = true
            }.onFailure { error ->
                isLoading = false
                mensajeError = error.message ?: "Error al conectar con el servidor"
                errorVisible = true
            }
        }
    }
}
