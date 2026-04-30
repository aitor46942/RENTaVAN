package com.example.rentavan.presentation.ui.viewmodel.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.repository.settings.cerrarSesionUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AjustesViewModel : ViewModel() {

    // Estado para notificar a la vista que la sesión ha terminado
    private val _logoutSuccess = MutableStateFlow(false)
    val logoutSuccess: StateFlow<Boolean> = _logoutSuccess.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun procesarCierreSesion() {
        viewModelScope.launch {
            _isLoading.value = true
            val resultado = cerrarSesionUsuario()

            resultado.onSuccess {
                _logoutSuccess.value = true
            }.onFailure {
                // Manejar error si el cierre falla
                _isLoading.value = false
            }
        }
    }
}