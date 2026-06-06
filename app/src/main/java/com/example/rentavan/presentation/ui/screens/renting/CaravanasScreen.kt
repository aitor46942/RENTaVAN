package com.example.rentavan.presentation.ui.screens.renting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.theme.GrisBoton
import com.example.rentavan.presentation.ui.theme.Blanco
import com.example.rentavan.presentation.ui.theme.jersey10Family
import com.example.rentavan.presentation.ui.viewmodel.renting.CaravanasViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaravanasScreen(
    navController: NavController,
    viewModel: CaravanasViewModel = viewModel() // Inyección de la lógica de negocio
) {
    // Estado para controlar el menú desplegable (estado de UI puramente visual)
    var menuExpandido by remember { mutableStateOf(false) }

    // Observadores reactivos del estado alojado en el ViewModel
    val listaCaravanas by viewModel.caravanas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoOscuro)
            )
        },
        containerColor = FondoOscuro
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
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

                // Renderizado condicional basado en el estado del ViewModel
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Amarillo,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                } else {
                    listaCaravanas.forEach { caravana ->
                        CaravanaCardExpandable(
                            nombre = caravana.modelo,
                            descripcion = caravana.descripcion,
                            onAlquilarClick = {
                                navController.navigate(Screen.Disponibilidad.createRoute(caravana.idCaravana.toString()))
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
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
private fun CaravanaCardExpandable(
    nombre: String,
    descripcion: String,
    onAlquilarClick: () -> Unit
) {
    var expandida by remember { mutableStateOf(false) }
    val rotacion by animateFloatAsState(targetValue = if (expandida) 180f else 0f, label = "chevron")

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Blanco,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandida = !expandida }
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = nombre,
                    color = FondoOscuro,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expandida) "Colapsar" else "Expandir",
                    tint = FondoOscuro,
                    modifier = Modifier.rotate(rotacion)
                )
            }

            AnimatedVisibility(
                visible = expandida,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.caravana1),
                        contentDescription = "Foto de $nombre",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = descripcion,
                        color = FondoOscuro,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onAlquilarClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Alquiler",
                            color = FondoOscuro,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

// --- Preview ---
@Preview(showBackground = true, name = "Vista Previa Caravanas")
@Composable
private fun CaravanasScreenPreview() {
    CaravanasScreen(navController = rememberNavController(), viewModel = CaravanasViewModel())
}
