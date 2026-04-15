package com.example.rentavan.presentation.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rentavan.R
import com.example.rentavan.presentation.ui.navigation.Screen
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCaravanasScreen(
    navController: NavController
) {
    var menuExpandido by remember { mutableStateOf(false) }
    // Scaffold nos permite estructurar la pantalla de forma estándar
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RENTaVAN",
                        color = Amarillo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        letterSpacing = 2.sp
                    )
                },
                actions = {
                    Box {
                        IconButton(onClick = { menuExpandido = true }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Amarillo
                            )
                        }

                        DropdownMenu(
                            expanded = menuExpandido,
                            onDismissRequest = { menuExpandido = false },
                            modifier = Modifier.background(Color(0xFF333333)) // Gris oscuro
                        ) {
                            DropdownMenuItem(
                                text = { Text("Ajustes", color = Color.White) },
                                onClick = {
                                    menuExpandido = false // Cerramos el menú
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
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // CABECERA (TÍTULOS)
            Text(
                text = "PERFIL",
                color = Amarillo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Etiqueta amarilla simulando el botón anterior pero como título
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Amarillo)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Mis caravanas en\nalquiler",
                    color = FondoOscuro,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // PANEL BLANCO CENTRAL CON LISTA Y BOTÓN FLOTANTE
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Ocupa todo el espacio vertical restante
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {

                // 1. LISTA DE CARAVANAS (Desplazable)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Item 1
                    CaravanaListItem(
                        nombre = "Mi caravana 1",
                        imagenRes = R.drawable.caravana1, // Asegúrate de tener esta imagen en res/drawable
                        onClick = { navController.navigate(Screen.ModificarAlquilerProp.route) }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Item 2
                    CaravanaListItem(
                        nombre = "Mi caravana 2",
                        imagenRes = R.drawable.caravana1,
                        onClick = { navController.navigate(Screen.ModificarAlquilerProp.route) }
                    )

                    // Espacio extra al final para que la lista no quede oculta por el botón flotante
                    Spacer(modifier = Modifier.height(80.dp))
                }

                // 2. BOTÓN FLOTANTE (FAB)
                // Anclado en la esquina inferior derecha gracias al Box
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddAlquiler.route) },
                    containerColor = Amarillo,
                    contentColor = FondoOscuro,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(26.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Añadir Caravana"
                    )
                }
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 24.dp, bottom = 24.dp)
                        .size(56.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = FondoOscuro,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

// COMPONENTE REUTILIZABLE PARA CADA FILA DE LA LISTA
@Composable
fun CaravanaListItem(nombre: String, imagenRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Hace que toda la fila sea pulsable
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = nombre,
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = imagenRes),
            contentDescription = "Imagen de $nombre",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp, 40.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MisCaravanasScreenPreview() {
    MisCaravanasScreen(
        navController = rememberNavController()
    )
}