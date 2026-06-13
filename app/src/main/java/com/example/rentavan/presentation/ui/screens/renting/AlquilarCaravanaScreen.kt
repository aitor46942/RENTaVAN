package com.example.rentavan.presentation.ui.screens.renting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.R
import com.example.rentavan.presentation.ui.navigation.Screen
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.Blanco
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.theme.GrisBoton
import com.example.rentavan.presentation.ui.theme.SuperficieOscura
import com.example.rentavan.presentation.ui.theme.jersey10Family
import com.example.rentavan.presentation.ui.viewmodel.renting.AlquilarCaravanaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlquilarCaravanaScreen(
    navController: NavController,
    caravanaId: String = "",
    fechaInicio: String = "",
    fechaFin: String = "",
    viewModel: AlquilarCaravanaViewModel = viewModel()
) {
    LaunchedEffect(caravanaId) {
        if (caravanaId.isNotBlank()) viewModel.cargarDetalles(caravanaId)
    }

    LaunchedEffect(viewModel.alquilerExitoso) {
        if (viewModel.alquilerExitoso) {
            navController.navigate("mis_alquileres") {
                popUpTo("mis_alquileres") { inclusive = true }
            }
        }
    }

    var menuExpandido by remember { mutableStateOf(false) }

    val dni by viewModel.dni.collectAsState()
    val nTarjeta by viewModel.nTarjeta.collectAsState()
    val nViajeros by viewModel.nViajeros.collectAsState()
    val caravanaDetalle by viewModel.caravanaDetalle.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RENTaVAN",
                        color = Amarillo,
                        fontFamily = jersey10Family,
                        fontSize = 40.sp,
                        letterSpacing = 2.sp,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    )
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Caravana info card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, Amarillo.copy(alpha = 0.25f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = caravanaDetalle?.modelo ?: "Cargando...",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        caravanaDetalle?.marca?.let {
                            Text(it, color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        caravanaDetalle?.precioPorDia?.let {
                            Text(
                                text = "%.2f €/día".format(it),
                                color = Amarillo,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        caravanaDetalle?.plazas?.takeIf { it > 0 }?.let {
                            Text("$it plazas", color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.caravana1),
                        contentDescription = "Imagen de la caravana",
                        modifier = Modifier
                            .size(110.dp, 85.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                if (fechaInicio.isNotBlank()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Amarillo.copy(alpha = 0.08f))
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Amarillo, modifier = Modifier.size(16.dp))
                        Text(
                            text = "Del $fechaInicio al $fechaFin",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Datos del alquiler",
                color = Color.White.copy(alpha = 0.45f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    AlquilarField("DNI", dni) { viewModel.onDniChange(it) }
                    Spacer(modifier = Modifier.height(14.dp))
                    AlquilarField("Nº Tarjeta", nTarjeta) { viewModel.onTarjetaChange(it) }
                    Spacer(modifier = Modifier.height(14.dp))
                    AlquilarField("Nº Viajeros", nViajeros) { viewModel.onViajerosChange(it) }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (viewModel.mensajeError.isNotEmpty()) {
                Text(
                    text = viewModel.mensajeError,
                    color = Color(0xFFFF6B6B),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (viewModel.isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Amarillo)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = SuperficieOscura),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, Amarillo.copy(alpha = 0.4f)),
                        modifier = Modifier.size(56.dp, 50.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Amarillo)
                    }

                    Button(
                        onClick = {
                            if (caravanaId.isNotBlank()) {
                                viewModel.confirmarAlquiler(
                                    caravanaId = caravanaId,
                                    fechaInicio = fechaInicio,
                                    fechaFin = fechaFin
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.weight(1f).height(50.dp)
                    ) {
                        Text(
                            text = "Confirmar alquiler",
                            color = FondoOscuro,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun AlquilarField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, color = Color.White.copy(alpha = 0.55f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo,
                unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                focusedContainerColor = FondoOscuro,
                unfocusedContainerColor = FondoOscuro,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Amarillo
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AlquilarCaravanaScreenPreview() {
    AlquilarCaravanaScreen(navController = rememberNavController())
}