package com.example.rentavan.presentation.ui.screens.renting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
fun DisponibilidadScreen(
    navController: NavController
) {
    // Estados para los campos de texto y el menú desplegable
    var menuExpandido by remember { mutableStateOf(false) }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Título unificado a dos líneas para coherencia visual
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FondoOscuro
                )
            )
        },
        containerColor = FondoOscuro
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Disponibilidad",
                color = Amarillo,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(60.dp))

            // --- Inputs funcionales ---
            CustomInputField(
                label = "Fecha inicio",
                value = fechaInicio,
                onValueChange = { fechaInicio = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomInputField(
                label = "Fecha finalización",
                value = fechaFin,
                onValueChange = { fechaFin = it }
            )

            Spacer(modifier = Modifier.height(60.dp))

            // --- Botón Siguiente: Navega a la pantalla 6 ---
            Button(
                onClick = { navController.navigate("alquilar_caravana") },
                colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.width(160.dp).height(50.dp)
            ) {
                Text("Siguiente", color = FondoOscuro, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- Botón Volver inferior ---
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomStart) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(60.dp, 45.dp),
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
}

@Composable
private fun CustomInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, color = Blanco, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .background(GrisBoton, shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Blanco, fontSize = 16.sp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Blanco
            )
        )
    }
}

// --- Preview ---
@Preview(showBackground = true, name = "Vista Previa Disponibilidad")
@Composable
private fun DisponibilidadScreenPreview() {
    DisponibilidadScreen(
        navController = rememberNavController()
    )
}