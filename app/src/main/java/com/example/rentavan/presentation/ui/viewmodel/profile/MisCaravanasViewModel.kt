package com.example.rentavan.presentation.ui.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.profile.CaravanaResponse
import com.example.rentavan.data.repository.profile.MisCaravanasRepository
import com.example.rentavan.data.sessions.UserSession
import kotlinx.coroutines.launch

class MisCaravanasViewModel : ViewModel() {
    private val repository = MisCaravanasRepository()

    var listaCaravanas by mutableStateOf<List<CaravanaResponse>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var mensajeError by mutableStateOf("")
        private set

    init {
        cargarCaravanas()
    }

    fun cargarCaravanas() {
        val idUsuario = UserSession.idUsuario ?: run { isLoading = false; return }
        viewModelScope.launch {
            isLoading = true
            repository.obtenerCaravanasPorPropietario(idUsuario)
                .onSuccess { listaCaravanas = it }
                .onFailure { mensajeError = "Error al cargar las caravanas" }
            isLoading = false
        }
    }

    fun eliminarCaravana(idCaravana: Long) {
        viewModelScope.launch {
            repository.eliminarCaravana(idCaravana)
                .onSuccess { cargarCaravanas() }
                .onFailure { mensajeError = "No se pudo eliminar la caravana. Puede que tenga reservas o periodos asociados." }
        }
    }

    fun limpiarError() { mensajeError = "" }
}