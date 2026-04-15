package com.example.rentavan.presentation.ui.screens.renting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.R
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.GrisBoton
import com.example.rentavan.presentation.ui.theme.Blanco

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlquilarCaravanaScreen(
    navController: NavController
) {
    var menuExpandido by remember { mutableStateOf(false) }
    var dni by remember { mutableStateOf("") }
    var nTarjeta by remember { mutableStateOf("") }
    var nViajeros by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "RENTaVAN",
                            color = Amarillo,
                            fontSize = 28.sp,
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Alquilar caravana",
                color = Amarillo,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    CustomInfoText("Modelo:")
                    CustomInfoText("Año:")
                    CustomInfoText("Peso:")
                    CustomInfoText("Matricula: (Dependiendo del peso tiene o no)")
                    CustomInfoText("Información adicional:")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.caravana1),
                    contentDescription = "Imagen de la caravana",
                    modifier = Modifier.size(130.dp, 100.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Datos:",
                color = Amarillo,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomAlquilarInputField(placeholder = "DNI", value = dni, onValueChange = { dni = it })
            Spacer(modifier = Modifier.height(16.dp))
            CustomAlquilarInputField(placeholder = "Nº Tarjeta", value = nTarjeta, onValueChange = { nTarjeta = it })
            Spacer(modifier = Modifier.height(16.dp))
            CustomAlquilarInputField(placeholder = "Nº Viajeros", value = nViajeros, onValueChange = { nViajeros = it })

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(60.dp, 45.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("←", color = FondoOscuro, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { navController.navigate("mis_alquileres") },
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.width(160.dp).height(50.dp)
                ) {
                    Text(
                        text = "Alquilar",
                        color = FondoOscuro,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun CustomInfoText(text: String) {
    Text(
        text = text,
        color = Blanco.copy(alpha = 0.8f),
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
private fun CustomAlquilarInputField(placeholder: String, value: String, onValueChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(GrisBoton, shape = RoundedCornerShape(12.dp)) // Usamos GrisBoton aquí
            .border(2.dp, Amarillo, shape = RoundedCornerShape(12.dp))
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxSize(),
            textStyle = TextStyle(color = Blanco, fontSize = 16.sp),
            placeholder = {
                Text(text = placeholder, color = Blanco.copy(alpha = 0.5f), fontSize = 16.sp)
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                cursorColor = Blanco
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AlquilarCaravanaScreenPreview() {
    AlquilarCaravanaScreen(
        navController = rememberNavController()
    )
}