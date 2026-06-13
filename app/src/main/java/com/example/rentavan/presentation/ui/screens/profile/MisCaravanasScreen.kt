package com.example.rentavan.presentation.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.data.model.profile.CaravanaResponse
import com.example.rentavan.presentation.ui.navigation.Screen
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.viewmodel.profile.MisCaravanasViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCaravanasScreen(
    navController: NavController,
    viewModel: MisCaravanasViewModel = viewModel()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry?.destination?.route) {
        if (navBackStackEntry?.destination?.route == Screen.MisCaravanas.route) {
            viewModel.cargarCaravanas()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Caravanas", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Amarillo)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.AddAlquiler.route) }) {
                        Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Amarillo)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoOscuro)
            )
        },
        containerColor = FondoOscuro
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    color = Amarillo,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (viewModel.listaCaravanas.isEmpty()) {
                Text(
                    text = "No tienes ninguna caravana registrada todavía.",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(viewModel.listaCaravanas) { caravana ->
                        ItemCaravana(
                            caravana = caravana,
                            onEditarClick = { navController.navigate(Screen.ModificarAlquilerProp.route) },
                            onEliminarClick = { viewModel.eliminarCaravana(caravana.idCaravana) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCaravana(
    caravana: CaravanaResponse,
    onEditarClick: () -> Unit,
    onEliminarClick: () -> Unit
) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("Eliminar caravana", color = Color.White) },
            text = { Text("¿Seguro que quieres eliminar esta caravana?", color = Color.Gray) },
            confirmButton = {
                TextButton(onClick = {
                    mostrarConfirmacion = false
                    onEliminarClick()
                }) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacion = false }) {
                    Text("Cancelar", color = Amarillo)
                }
            },
            containerColor = Color(0xFF2C2C2C)
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF444444)
            ) {
                Icon(
                    Icons.Default.DirectionsCar,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = listOfNotNull(caravana.marca, caravana.modelo).joinToString(" "),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "${caravana.plazas} plazas",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "${caravana.precioPorDia}€ / día",
                    color = Amarillo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            OutlinedButton(
                onClick = onEditarClick,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Amarillo),
                border = androidx.compose.foundation.BorderStroke(1.dp, Amarillo)
            ) {
                Text("Editar")
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { mostrarConfirmacion = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MisCaravanasPreview() {
    MisCaravanasScreen(navController = rememberNavController())
}