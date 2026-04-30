package com.example.rentavan.presentation.ui.viewmodel.renting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.renting.Caravana
import com.example.rentavan.data.repository.renting.obtenerCaravanas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CaravanasViewModel : ViewModel() {

    // Si es null, interpretamos que está cargando
    private val _caravanas = MutableStateFlow<List<Caravana>?>(null)
    val caravanas: StateFlow<List<Caravana>?> = _caravanas

    init {
        cargarCaravanas()
    }

    fun cargarCaravanas() {
        viewModelScope.launch {
            // Resetear a null para mostrar carga si se vuelve a llamar
            _caravanas.value = null

            val resultado = obtenerCaravanas()

            // Si tiene éxito, guardamos la lista; si falla, una lista vacía (o manejas el error)
            _caravanas.value = resultado.getOrDefault(emptyList())
        }
    }
}