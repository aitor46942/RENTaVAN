package com.example.rentavan.presentation.ui.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rentavan.data.model.profile.CaravanaResponse
import com.example.rentavan.data.model.profile.PropietarioResumen
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.theme.RENTaVANTheme
import com.example.rentavan.presentation.ui.theme.jersey10Family
import com.example.rentavan.presentation.ui.viewmodel.map.MapaCaravanasViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

// Centro de España como posición inicial del mapa
private val ESPANA_CENTER = LatLng(40.4168, -3.7038)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaCaravanasScreen(
    navController: NavController,
    viewModel: MapaCaravanasViewModel = viewModel()
) {
    val caravanas by viewModel.caravanas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(ESPANA_CENTER, 6f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mapa",
                        color = Amarillo,
                        fontWeight = FontWeight.Bold,
                        fontFamily = jersey10Family,
                        fontSize = 32.sp,
                        letterSpacing = 2.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Amarillo
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FondoOscuro
                )
            )
        },
        containerColor = FondoOscuro
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        color = Amarillo,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                error != null -> {
                    Text(
                        text = "Error al cargar caravanas",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        caravanas
                            .filter { it.latitud != null && it.longitud != null }
                            .forEach { caravana ->
                                Marker(
                                    state = MarkerState(
                                        position = LatLng(caravana.latitud!!, caravana.longitud!!)
                                    ),
                                    title = caravana.modelo,
                                    snippet = "${caravana.precioPorDia}€/día"
                                )
                            }
                    }

                    if (caravanas.isNotEmpty() && caravanas.none { it.latitud != null }) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp)
                                .background(Color(0xCC222222))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Sin ubicación asignada aún",
                                color = Color.White,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// ── PREVIEWS ────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, name = "Mapa – Cargando")
@Composable
private fun MapaCaravanasLoadingPreview() {
    RENTaVANTheme(dynamicColor = false) {
        Scaffold(
            topBar = { PreviewTopBar() },
            containerColor = FondoOscuro
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Amarillo)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, name = "Mapa – Error")
@Composable
private fun MapaCaravanasErrorPreview() {
    RENTaVANTheme(dynamicColor = false) {
        Scaffold(
            topBar = { PreviewTopBar() },
            containerColor = FondoOscuro
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error al cargar caravanas", color = Color.Red)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, name = "Mapa – Con caravanas")
@Composable
private fun MapaCaravanasContentPreview() {
    val fakeCaravanas = listOf(
        CaravanaResponse(
            idCaravana = 1,
            modelo = "Mercedes Sprinter",
            descripcion = "Caravana de lujo",
            propietario = PropietarioResumen(1, "Carlos García", "600123456"),
            marca = "Mercedes",
            precioPorDia = 120.0,
            plazas = 4,
            latitud = 40.4168,
            longitud = -3.7038
        ),
        CaravanaResponse(
            idCaravana = 2,
            modelo = "Ford Transit",
            descripcion = "Caravana familiar",
            propietario = PropietarioResumen(2, "Ana López", "600654321"),
            marca = "Ford",
            precioPorDia = 85.0,
            plazas = 6,
            latitud = 41.3851,
            longitud = 2.1734
        )
    )

    RENTaVANTheme(dynamicColor = false) {
        Scaffold(
            topBar = { PreviewTopBar() },
            containerColor = FondoOscuro
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFBDD3C7))
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "[ Google Map ]",
                        color = Color(0xFF2E5E4E),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "${fakeCaravanas.size} caravanas en el mapa",
                        color = Color(0xFF1A1A1A),
                        fontSize = 14.sp
                    )
                    fakeCaravanas.forEach { caravana ->
                        Text(
                            text = "• ${caravana.modelo}  —  ${caravana.precioPorDia}€/día",
                            color = Color(0xFF333333),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreviewTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Mapa",
                color = Amarillo,
                fontWeight = FontWeight.Bold,
                fontFamily = jersey10Family,
                fontSize = 32.sp,
                letterSpacing = 2.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Amarillo
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoOscuro)
    )
}