package com.example.rentavan.presentation.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.presentation.ui.navigation.Screen
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro

@OptIn(ExperimentalMaterial3Api::class) // Aceptamos usar componentes modernos de Material 3
@Composable
fun PerfilScreen(navController: NavController) {
    var menuExpandido by remember { mutableStateOf(false) }
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

        // LAYOUT PRINCIPAL: BOX
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // CONTENIDO CENTRAL (TÍTULO Y BOTÓN GRANDE)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(48.dp))

                // Título de la sección
                Text(
                    text = "PERFIL",
                    color = Amarillo,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Botón principal de acceso a "Mis caravanas"
                Button(
                    onClick = {navController.navigate(Screen.MisCaravanas.route)},
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp) // Le damos una altura generosa como en el diseño
                ) {
                    Text(
                        text = "Mis caravanas en\nalquiler", // \n fuerza el salto de línea
                        color = FondoOscuro,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }
            }

            // BOTÓN FLOTANTE DE RETROCESO
            Button(
                onClick = {navController.popBackStack()},
                colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .align(Alignment.BottomStart) // Anclado abajo a la izquierda
                    .padding(start = 24.dp, bottom = 24.dp)
                    .size(56.dp), // Lo hacemos perfectamente cuadrado
                contentPadding = PaddingValues(0.dp) // Quitamos el padding interno por defecto
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

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen(navController = rememberNavController())
}