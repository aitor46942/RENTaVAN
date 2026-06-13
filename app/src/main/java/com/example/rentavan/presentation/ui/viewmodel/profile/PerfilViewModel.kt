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

    var modoEdicion by mutableStateOf(false)
        private set

    var nombreEditado by mutableStateOf("")
    var telefonoEditado by mutableStateOf("")

    var isSaving by mutableStateOf(false)
        private set

    var errorGuardado by mutableStateOf("")
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

    fun activarEdicion() {
        nombreEditado = perfilUsuario?.nombre ?: ""
        telefonoEditado = perfilUsuario?.telefono ?: ""
        errorGuardado = ""
        modoEdicion = true
    }

    fun cancelarEdicion() {
        modoEdicion = false
        errorGuardado = ""
    }

    fun guardarCambios() {
        viewModelScope.launch {
            isSaving = true
            errorGuardado = ""
            val result = repository.actualizarPerfil(
                nombre = nombreEditado.trim(),
                telefono = telefonoEditado.trim()
            )
            result.onSuccess { datos ->
                perfilUsuario = datos
                modoEdicion = false
                isSaving = false
            }.onFailure { e ->
                errorGuardado = e.message ?: "Error al guardar"
                isSaving = false
            }
        }
    }
}