package com.example.rentavan.presentation.ui.viewmodel.renting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.renting.Caravana
import com.example.rentavan.data.repository.renting.obtenerCaravanas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CaravanasViewModel : ViewModel() {

    private val _caravanas = MutableStateFlow<List<Caravana>>(emptyList())
    val caravanas: StateFlow<List<Caravana>> = _caravanas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarCaravanas()
    }

    private fun cargarCaravanas() {
        viewModelScope.launch {
            _isLoading.value = true
            val resultado = obtenerCaravanas() // Llamada al repositorio

            resultado.onSuccess { lista ->
                _caravanas.value = lista
            }.onFailure {
                // Aquí manejaríamos el error (ej. mostrar un mensaje al usuario)
            }
            _isLoading.value = false
        }
    }
}
