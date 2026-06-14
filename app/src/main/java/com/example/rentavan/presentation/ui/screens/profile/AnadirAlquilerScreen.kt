package com.example.rentavan.presentation.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.rentavan.presentation.ui.theme.SuperficieOscura
import com.example.rentavan.presentation.ui.theme.jersey10Family
import com.example.rentavan.presentation.ui.viewmodel.profile.AnadirAlquilerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnadirAlquilerScreen(
    navController: NavController,
    viewModel: AnadirAlquilerViewModel = viewModel()
) {
    LaunchedEffect(viewModel.subidaExitosa) {
        if (viewModel.subidaExitosa) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "RENTaVAN",
                        color = Amarillo,
                        fontFamily = jersey10Family,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Amarillo)
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Añadir caravana",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Publica tu caravana para alquilar",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Información básica
            SectionLabel("Información básica")
            FormCard {
                FormField("Modelo", viewModel.modelo) { viewModel.onModeloChange(it) }
                Spacer(modifier = Modifier.height(14.dp))
                FormField("Matrícula", viewModel.matricula) { viewModel.onMatriculaChange(it) }
                Spacer(modifier = Modifier.height(14.dp))
                FormField("Descripción", viewModel.descripcion, maxLines = 3) { viewModel.onDescripcionChange(it) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Detalles
            SectionLabel("Detalles")
            FormCard {
                FormField("Precio por día (€)", viewModel.precioPorDia) { viewModel.onPrecioPorDiaChange(it) }
                Spacer(modifier = Modifier.height(14.dp))
                FormField("Plazas (personas)", viewModel.plazas) { viewModel.onPlazasChange(it) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Disponibilidad
            SectionLabel("Disponibilidad")
            FormCard {
                FormField("Disponible desde (ej: 2026-06-01)", viewModel.fechaInicio) { viewModel.onFechaInicioChange(it) }
                Spacer(modifier = Modifier.height(14.dp))
                FormField("Disponible hasta (ej: 2026-09-30)", viewModel.fechaFin) { viewModel.onFechaFinChange(it) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ubicación
            SectionLabel("Ubicación")
            FormCard {
                FormField("Latitud (ej: 40.4168)", viewModel.latitud) { viewModel.onLatitudChange(it) }
                Spacer(modifier = Modifier.height(14.dp))
                FormField("Longitud (ej: -3.7038)", viewModel.longitud) { viewModel.onLongitudChange(it) }
            }

            Spacer(modifier = Modifier.height(28.dp))

            if (viewModel.isLoading) {
                CircularProgressIndicator(color = Amarillo, modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Button(
                    onClick = { viewModel.publicar() },
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Publicar caravana", color = FondoOscuro, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                if (viewModel.mensajeError.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = viewModel.mensajeError,
                        color = Color(0xFFFF6B6B),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = Color.White.copy(alpha = 0.45f),
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
    )
}

@Composable
private fun FormCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
private fun FormField(label: String, value: String, maxLines: Int = 1, onValueChange: (String) -> Unit) {
    Column {
        Text(label, color = Color.White.copy(alpha = 0.55f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            maxLines = maxLines,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo,
                unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                focusedContainerColor = FondoOscuro,
                unfocusedContainerColor = FondoOscuro,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Amarillo
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AnadirAlquilerScreenPreview() {
    AnadirAlquilerScreen(navController = rememberNavController())
}