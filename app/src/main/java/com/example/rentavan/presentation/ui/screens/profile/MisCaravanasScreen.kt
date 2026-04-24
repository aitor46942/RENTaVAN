package com.example.rentavan.presentation.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.rentavan.data.model.profile.Caravana
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Caravanas", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Amarillo)
                    }
                },
                actions = {
                    // Botón de "+" para ir a la pantalla de añadir alquiler
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
                // Pantalla de carga central
                CircularProgressIndicator(
                    color = Amarillo,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (viewModel.listaCaravanas.isEmpty()) {
                // Mensaje si no tiene caravanas
                Text(
                    text = "No tienes ninguna caravana registrada todavía.",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // LAZY COLUMN: La forma profesional de hacer listas en Compose
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(24.dp), // Margen exterior de la lista
                    verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre tarjetas
                ) {
                    // Le pasamos la lista de caravanas y creamos un Card por cada una
                    items(viewModel.listaCaravanas) { caravana ->
                        ItemCaravana(caravana = caravana) {
                            // Al hacer clic en "Editar", viajamos a la pantalla de Modificar
                            navController.navigate(Screen.ModificarAlquilerProp.route)
                        }
                    }
                }
            }
        }
    }
}

// Sub-componente para dibujar cada tarjeta individual
@Composable
fun ItemCaravana(caravana: Caravana, onEditarClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Un icono simulando la foto de la furgo
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
                    text = "${caravana.marca} ${caravana.modelo}",
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

            // Botón para editar
            OutlinedButton(
                onClick = onEditarClick,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Amarillo),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(Amarillo))
            ) {
                Text("Editar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MisCaravanasPreview() {
    MisCaravanasScreen(navController = rememberNavController())
}