package com.example.rentavan.presentation.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rentavan.presentation.ui.navigation.Screen
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjustesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ajustes", color = Amarillo, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Amarillo)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoOscuro)
            )
        },
        containerColor = FondoOscuro
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Aquí puedes añadir opciones de ajustes en el futuro
            Text(
                text = "Opciones de Configuración",
                color = Color.White,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Ejemplo de un botón para cerrar sesión
            Button(
                onClick = {
                    navController.navigate(Screen.Login.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), // Rojo para salir
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Cerrar Sesión", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}