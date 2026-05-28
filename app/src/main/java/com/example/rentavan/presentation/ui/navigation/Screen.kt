package com.example.rentavan.presentation.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object Caravanas : Screen("caravanas")
    data object Perfil : Screen("perfil")
    data object MisCaravanas : Screen("mis_caravanas")
    data object ModificarAlquilerProp : Screen("mod_alquiler_prop")
    data object AddAlquiler : Screen("add_alquiler")
    data object MisAlquileres : Screen("mis_alquileres")

    data object Cancelacion : Screen("cancelacion/{reservaId}") {
        fun createRoute(reservaId: Int) = "cancelacion/$reservaId"
    }

    data object Ajustes : Screen("ajustes")

    data object Disponibilidad : Screen("disponibilidad/{caravanaId}") {
        fun createRoute(caravanaId: String) = "disponibilidad/$caravanaId"
    }

    data object AlquilarCaravana : Screen("alquilar_caravana/{caravanaId}/{fechaInicio}/{fechaFin}") {
        fun createRoute(caravanaId: String, fechaInicio: String, fechaFin: String) =
            "alquilar_caravana/$caravanaId/$fechaInicio/$fechaFin"
    }

    data object ModificarReserva : Screen("mod_reserva/{reservaId}") {
        fun createRoute(reservaId: Int) = "mod_reserva/$reservaId"
    }
}