package com.example.rentavan.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.presentation.ui.screens.profile.AnadirAlquilerScreen
import com.example.rentavan.presentation.ui.screens.home.HomeScreen
import com.example.rentavan.presentation.ui.screens.profile.MisCaravanasScreen
import com.example.rentavan.presentation.ui.screens.profile.ModificarAlquilerPropietarioScreen
import com.example.rentavan.presentation.ui.screens.reservations.ModificarReservaScreen
import com.example.rentavan.presentation.ui.screens.profile.PerfilScreen
import com.example.rentavan.presentation.ui.screens.renting.AlquilarCaravanaScreen
import com.example.rentavan.presentation.ui.screens.renting.CaravanasScreen
import com.example.rentavan.presentation.ui.screens.renting.DisponibilidadScreen
import com.example.rentavan.presentation.ui.screens.reservations.MisAlquileresScreen
import com.example.rentavan.presentation.ui.screens.settings.AjustesScreen

// El startDestination define la pantalla que se cargará cuando se abre la aplicación
@Composable
fun NavGraph(startDestination: String = Screen.Home.route) {
    // Cargamos el navController
    val navController = rememberNavController()

    // Creamos un NavHost que arranque con la pantalla de inicio
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Caravanas.route) { CaravanasScreen(navController) }
        composable(Screen.Perfil.route) { PerfilScreen(navController) }
        composable(Screen.MisCaravanas.route) { MisCaravanasScreen(navController) }
        composable(Screen.ModificarAlquilerProp.route) { ModificarAlquilerPropietarioScreen(navController) }
        composable(Screen.AddAlquiler.route) { AnadirAlquilerScreen(navController) }
        composable(Screen.ModificarReserva.route) { ModificarReservaScreen(navController) }
        composable(Screen.Disponibilidad.route) { DisponibilidadScreen(navController) }
        composable(Screen.AlquilarCaravana.route) { AlquilarCaravanaScreen(navController) }
        composable(Screen.MisAlquileres.route) { MisAlquileresScreen(navController) }
        composable(Screen.Ajustes.route) { AjustesScreen(navController) }
    }
}