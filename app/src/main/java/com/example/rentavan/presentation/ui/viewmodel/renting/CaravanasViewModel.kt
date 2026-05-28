package com.example.rentavan.presentation.ui.viewmodel.renting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.profile.CaravanaResponse
import com.example.rentavan.data.repository.renting.obtenerCaravanasBackend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CaravanasViewModel : ViewModel() {

    private val _caravanas = MutableStateFlow<List<CaravanaResponse>>(emptyList())
    val caravanas: StateFlow<List<CaravanaResponse>> = _caravanas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarCaravanas()
    }

    private fun cargarCaravanas() {
        viewModelScope.launch {
            _isLoading.value = true
            obtenerCaravanasBackend()
                .onSuccess { _caravanas.value = it }
                .onFailure { }
            _isLoading.value = false
        }
    }
}