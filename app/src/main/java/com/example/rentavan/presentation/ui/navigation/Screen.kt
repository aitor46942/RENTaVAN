package com.example.rentavan.presentation.ui.navigation

// Dentro de la sealed class definimos un object por cada ruta existente
sealed class Screen(val route: String) {
    data object Login : Screen("login")

    data object Register : Screen("register")
    data object Home : Screen("home")
    data object Caravanas : Screen("caravanas")
    data object Perfil : Screen("perfil")
    data object MisCaravanas : Screen("mis_caravanas")
    data object ModificarAlquilerProp : Screen("mod_alquiler_prop")
    data object AddAlquiler : Screen("add_alquiler")
    data object ModificarReserva : Screen("mod_reserva")

    data object Disponibilidad : Screen("disponibilidad")

    data object AlquilarCaravana : Screen("alquilar_caravana")

    data object MisAlquileres : Screen("mis_alquileres")

    data object Cancelacion : Screen("cancelacion")

    data object Ajustes : Screen("ajustes")
}