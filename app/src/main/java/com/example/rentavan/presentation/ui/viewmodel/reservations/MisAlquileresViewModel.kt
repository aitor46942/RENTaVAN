package com.example.rentavan.presentation.ui.viewmodel.reservations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.reservations.Alquiler
import com.example.rentavan.data.repository.reservations.obtenerMisAlquileres
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MisAlquileresViewModel : ViewModel() {

    private val _alquileres = MutableStateFlow<List<Alquiler>>(emptyList())
    val alquileres: StateFlow<List<Alquiler>> = _alquileres.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarAlquileres()
    }

    private fun cargarAlquileres() {
        viewModelScope.launch {
            _isLoading.value = true
            val resultado = obtenerMisAlquileres() // Llama al repositorio[cite: 19]
            resultado.onSuccess { lista ->
                _alquileres.value = lista
            }.onFailure {
                // Manejo de errores
            }
            _isLoading.value = false
        }
    }
}
