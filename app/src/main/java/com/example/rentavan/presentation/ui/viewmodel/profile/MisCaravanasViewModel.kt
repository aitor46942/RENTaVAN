package com.example.rentavan.presentation.ui.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.profile.Caravana
import com.example.rentavan.data.repository.profile.MisCaravanasRepository
import kotlinx.coroutines.launch

class MisCaravanasViewModel : ViewModel() {
    private val repository = MisCaravanasRepository()

    // Estado que guarda la lista de caravanas. Empieza vacía.
    var listaCaravanas by mutableStateOf<List<Caravana>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        cargarCaravanas()
    }

    private fun cargarCaravanas() {
        viewModelScope.launch {
            isLoading = true
            val result = repository.obtenerMisCaravanas()

            result.onSuccess { vehiculos ->
                listaCaravanas = vehiculos
                isLoading = false
            }.onFailure {
                isLoading = false
            }
        }
    }
}