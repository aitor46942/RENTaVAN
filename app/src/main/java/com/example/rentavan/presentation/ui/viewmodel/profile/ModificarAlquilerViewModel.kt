package com.example.rentavan.presentation.ui.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rentavan.data.model.profile.ActualizarCaravanaRequest
import com.example.rentavan.data.repository.profile.ModificarAlquilerRepository
import kotlinx.coroutines.launch

class ModificarAlquilerViewModel : ViewModel() {
    private val repository = ModificarAlquilerRepository()

    // Control del Menú Superior
    var menuExpandido by mutableStateOf(false)
        private set

    fun abrirMenu() { menuExpandido = true }
    fun cerrarMenu() { menuExpandido = false }

    // Estados de Carga y Éxito
    var isLoadingInicial by mutableStateOf(true)
        private set
    var isGuardando by mutableStateOf(false)
        private set
    var guardadoExitoso by mutableStateOf(false)
        private set

    // Estados del Formulario
    var modelo by mutableStateOf("")
    var anio by mutableStateOf("")
    var peso by mutableStateOf("")
    var matricula by mutableStateOf("")
    var nombreTitular by mutableStateOf("")
    var telefono by mutableStateOf("")
    var email by mutableStateOf("")
    var fechaInicio by mutableStateOf("")
    var fechaFin by mutableStateOf("")
    var darDeAlta by mutableStateOf(true)
    var numPlazas by mutableStateOf("")
    var precio by mutableStateOf("")

    init {
        // En un proyecto real, el ID vendría pasado por navegación. Aquí usamos uno fijo.
        cargarDatosCaravana("id_simulado_1")
    }

    private fun cargarDatosCaravana(id: String) {
        viewModelScope.launch {
            isLoadingInicial = true
            val result = repository.obtenerDetallesCaravana(id)

            result.onSuccess { datos ->
                modelo = datos.modelo
                anio = datos.anio
                peso = datos.peso
                matricula = datos.matricula
                nombreTitular = datos.nombreTitular
                telefono = datos.telefono
                email = datos.email
                fechaInicio = datos.fechaInicio
                fechaFin = datos.fechaFin
                darDeAlta = datos.darDeAlta
                numPlazas = datos.numPlazas
                precio = datos.precio

                isLoadingInicial = false
            }
        }
    }

    fun guardarCambios() {
        viewModelScope.launch {
            isGuardando = true
            val request = ActualizarCaravanaRequest(
                idCaravana = "id_simulado_1",
                modelo = modelo, anio = anio, peso = peso, matricula = matricula,
                nombreTitular = nombreTitular, telefono = telefono, email = email,
                fechaInicio = fechaInicio, fechaFin = fechaFin,
                darDeAlta = darDeAlta, numPlazas = numPlazas, precio = precio
            )

            val result = repository.guardarCambios(request)

            result.onSuccess {
                isGuardando = false
                guardadoExitoso = true
            }
        }
    }
}