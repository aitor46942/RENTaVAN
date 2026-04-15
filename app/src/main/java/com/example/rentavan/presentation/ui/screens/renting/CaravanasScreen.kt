package com.example.rentavan.presentation.ui.screens.renting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.theme.GrisBoton
import com.example.rentavan.presentation.ui.theme.Blanco

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaravanasScreen(navController: NavController) {
    // Estado para controlar el menú desplegable de ajustes
    var menuExpandido by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Diseño unificado a dos líneas
                    Column {
                        Text(
                            text = "RENTaVAN",
                            color = Amarillo,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { menuExpandido = true }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Blanco.copy(alpha = 0.8f)
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpandido,
                            onDismissRequest = { menuExpandido = false },
                            modifier = Modifier.background(GrisBoton)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Ajustes", color = Blanco) },
                                onClick = {
                                    menuExpandido = false
                                    navController.navigate("ajustes")
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoOscuro)
            )
        },
        containerColor = FondoOscuro
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Nuestras caravanas !!",
                    color = Amarillo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Generación de lista de caravanas. Navega a disponibilidad al pulsar
                for (i in 1..6) {
                    CaravanaCardList(
                        nombre = "Caravana $i",
                        onClick = { navController.navigate("disponibilidad") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Espacio de seguridad para el botón flotante
                Spacer(modifier = Modifier.height(80.dp))
            }

            // Botón de retroceso flotante inferior
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 24.dp, bottom = 24.dp)
                    .size(56.dp, 45.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = FondoOscuro
                )
            }
        }
    }
}

@Composable
private fun CaravanaCardList(nombre: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .clickable { onClick() },
        color = Blanco,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = FondoOscuro
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = nombre,
                color = FondoOscuro,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

// --- Preview ---
@Preview(showBackground = true, name = "Vista Previa Caravanas")
@Composable
private fun CaravanasScreenPreview() {
    CaravanasScreen(navController = rememberNavController())
}