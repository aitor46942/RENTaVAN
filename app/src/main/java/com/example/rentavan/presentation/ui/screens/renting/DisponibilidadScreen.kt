package com.example.rentavan.presentation.ui.screens.renting

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.presentation.ui.navigation.Screen
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.theme.GrisBoton
import com.example.rentavan.presentation.ui.theme.Blanco
import com.example.rentavan.presentation.ui.theme.jersey10Family
import com.example.rentavan.presentation.ui.viewmodel.renting.DisponibilidadViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisponibilidadScreen(
    navController: NavController,
    caravanaId: String = "",
    viewModel: DisponibilidadViewModel = viewModel()
) {

    var menuExpandido by remember { mutableStateOf(false) }

    val fechaInicio by viewModel.fechaInicio.collectAsState()
    val fechaFin by viewModel.fechaFin.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {

                    Column {
                        Text(
                            text = "RENTaVAN",
                            color = Amarillo,
                            fontFamily = jersey10Family,
                            fontSize = 40.sp,
                            letterSpacing = 2.sp,
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


            CustomInputField(
                label = "Fecha inicio",
                value = fechaInicio,
                onValueChange = { viewModel.onFechaInicioChange(it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomInputField(
                label = "Fecha finalización",
                value = fechaFin,
                onValueChange = { viewModel.onFechaFinChange(it) }
            )

            Spacer(modifier = Modifier.height(60.dp))


            Button(
                onClick = {
                    viewModel.comprobarDisponibilidad(caravanaId)
                    navController.navigate(
                        Screen.AlquilarCaravana.createRoute(
                            caravanaId,
                            Uri.encode(fechaInicio),
                            Uri.encode(fechaFin)
                        )
                    )
                },
                enabled = fechaInicio.isNotBlank() && fechaFin.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(160.dp)
                    .height(50.dp)
            ) {
                Text("Siguiente", color = FondoOscuro, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.weight(1f))

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

@Preview(showBackground = true, name = "Vista Previa Disponibilidad")
@Composable
private fun DisponibilidadScreenPreview() {
    DisponibilidadScreen(
        navController = rememberNavController(),
        viewModel = DisponibilidadViewModel() // Inyección para la previsualización
    )
}
