package com.example.rentavan.presentation.ui.screens.reservations

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.R
import com.example.rentavan.data.model.reservations.Alquiler
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.theme.GrisBoton
import com.example.rentavan.presentation.ui.theme.Blanco
import com.example.rentavan.presentation.ui.theme.jersey10Family
import com.example.rentavan.presentation.ui.viewmodel.reservations.MisAlquileresViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisAlquileresScreen(
    navController: NavController,
    viewModel: MisAlquileresViewModel = viewModel()
) {
    // Estado para controlar el menú desplegable de ajustes
    var menuExpandido by remember { mutableStateOf(false) }

    // Estados reactivos desde el ViewModel
    val listaAlquileres by viewModel.alquileres.collectAsState()
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Mis alquileres",
                    color = Amarillo,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(30.dp))

                if (isLoading) {
                    CircularProgressIndicator(color = Amarillo)
                } else {
                    // Generación de la lista dinámica de alquileres
                    listaAlquileres.forEach { alquiler ->
                        CardAlquilerFiel(
                            alquiler = alquiler,
                            onModificar = { navController.navigate("mod_reserva/${alquiler.reservaId}") },
                            onCancelar = { navController.navigate("cancelacion/${alquiler.reservaId}") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

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
private fun CardAlquilerFiel(
    alquiler: Alquiler,
    onModificar: () -> Unit,
    onCancelar: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = Blanco,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Alquiler #${alquiler.reservaId}", fontWeight = FontWeight.ExtraBold, color = FondoOscuro, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Modelo: ${alquiler.modelo}", color = FondoOscuro, fontSize = 13.sp)
                    Text("Año: ${alquiler.anio}", color = FondoOscuro, fontSize = 13.sp)
                    Text("Peso: ${alquiler.peso}", color = FondoOscuro, fontSize = 13.sp)
                    Text("Matricula: ${alquiler.matricula}", color = FondoOscuro, fontSize = 13.sp)
                    Text("Precio: ${alquiler.precio}€", color = FondoOscuro, fontSize = 13.sp)
                    Text("De: ${alquiler.fechaInicio} a ${alquiler.fechaFin}", color = FondoOscuro, fontSize = 13.sp)
                }

                Image(
                    painter = painterResource(id = R.drawable.caravana1),
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp, 95.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onModificar,
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(38.dp)
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text("Modificar", color = FondoOscuro, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onCancelar,
                    colors = ButtonDefaults.buttonColors(containerColor = GrisBoton),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(38.dp)
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text("Cancelar", color = Blanco, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Vista Previa Mis Alquileres")
@Composable
private fun MisAlquileresScreenPreview() {
    MisAlquileresScreen(
        navController = rememberNavController()
    )
}
