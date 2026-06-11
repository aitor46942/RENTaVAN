package com.example.rentavan.presentation.ui.screens.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
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