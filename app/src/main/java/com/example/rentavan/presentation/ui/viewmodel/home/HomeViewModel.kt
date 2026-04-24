package com.example.rentavan.presentation.ui.viewmodel.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    // Estado para el menú de la esquina superior derecha
    var menuExpandido by mutableStateOf(false)
        private set

    // Funciones para abrir y cerrar el menú
    fun abrirMenu() { menuExpandido = true }
    fun cerrarMenu() { menuExpandido = false }
}