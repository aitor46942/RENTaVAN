package com.example.rentavan.presentation.ui.screens.reservations

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentavan.R
import com.example.rentavan.presentation.ui.theme.GrisBoton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModificarReservaScreen(
    navController: NavController
) {
    // Estados para los campos de texto
    var numViajeros by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }

    // Estados para controlar si los diálogos de calendario están abiertos
    var mostrarCalendarioInicio by remember { mutableStateOf(false) }
    var mostrarCalendarioFin by remember { mutableStateOf(false) }

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
                    // Botón de menú en la esquina superior derecha
                    IconButton(onClick = { /* TODO: Menú */ }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // CABECERA Y FOTO DE LA CARAVANA
            Text(
                text = "Datos alquiler",
                color = Amarillo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Imagen de la caravana simulada
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Datos:",
                    color = Amarillo,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                // Tarjeta que contiene la imagen representativa de la reserva
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.size(120.dp, 80.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.caravana1), // Usa tu drawable
                        contentDescription = "Caravana seleccionada",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            // CAMPO: NÚMERO DE VIAJEROS
            OutlinedTextField(
                value = numViajeros,
                onValueChange = { nuevoValor ->
                    // Filtro de seguridad: Solo actualiza el estado si todos
                    // los caracteres introducidos son números (dígitos).
                    if (nuevoValor.all { char -> char.isDigit() }) {
                        numViajeros = nuevoValor
                    }
                },
                label = { Text("Numero de Viajeros", color = Color.Gray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Amarillo,
                    unfocusedBorderColor = Amarillo,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                // Despliega el teclado numérico nativo del sistema
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // SECCIÓN: PERIODO DE DISPONIBILIDAD (FECHAS)
            Text(
                text = "Periodo de disponibilidad:",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Fila de Fechas
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp) // <-- Da espacio automático entre los elementos
            ) {
                Text(
                    text = "De:",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 1,
                    softWrap = false
                )

                // Campo simulado de Fecha Inicio
                OutlinedTextField(
                    value = fechaInicio,
                    onValueChange = { },
                    readOnly = true,
                    enabled = false,
                    placeholder = { Text("DD/MM/YYYY", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = Amarillo,
                        disabledTextColor = Color.White,
                        disabledPlaceholderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f) // Ocupa la mitad del espacio disponible
                        .height(56.dp)
                        .clickable { mostrarCalendarioInicio = true }
                )

                // Texto "a" protegido
                Text(
                    text = "a",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    maxLines = 1,
                    softWrap = false
                )

                // Campo simulado de Fecha Fin
                OutlinedTextField(
                    value = fechaFin,
                    onValueChange = { },
                    readOnly = true,
                    enabled = false,
                    placeholder = { Text("DD/MM/YYYY", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledBorderColor = Amarillo,
                        disabledTextColor = Color.White,
                        disabledPlaceholderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f) // Ocupa la otra mitad
                        .height(56.dp)
                        .clickable { mostrarCalendarioFin = true }
                )
            }
            Spacer(modifier = Modifier.height(48.dp))

            // Objeto para dar formato a los milisegundos devueltos por el DatePicker
            val formateador = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            // Calendario para Fecha Inicio
            if (mostrarCalendarioInicio) {
                val datePickerState = rememberDatePickerState()

                DatePickerDialog(
                    onDismissRequest = { mostrarCalendarioInicio = false },
                    confirmButton = {
                        TextButton(onClick = {
                            // Si el usuario seleccionó una fecha, la formateamos y la guardamos
                            datePickerState.selectedDateMillis?.let { milisegundos ->
                                fechaInicio = formateador.format(Date(milisegundos))
                            }
                            mostrarCalendarioInicio = false
                        }) {
                            Text("Aceptar", color = Amarillo)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarCalendarioInicio = false }) {
                            Text("Cancelar", color = Color.Gray)
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
            // Calendario para Fecha Fin
            if (mostrarCalendarioFin) {
                val datePickerState = rememberDatePickerState()

                DatePickerDialog(
                    onDismissRequest = { mostrarCalendarioFin = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { milisegundos ->
                                fechaFin = formateador.format(Date(milisegundos))
                            }
                            mostrarCalendarioFin = false
                        }) {
                            Text("Aceptar", color = Amarillo)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarCalendarioFin = false }) {
                            Text("Cancelar", color = Color.Gray)
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* TODO: Guardar cambios */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.width(130.dp)
                ) {
                    Text("Guardar", color = FondoOscuro, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = GrisBoton),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.width(130.dp)
                ) {
                    Text("Cancelar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModificarReservaScreenPreview() {
    ModificarReservaScreen(
        navController = rememberNavController()
    )
}