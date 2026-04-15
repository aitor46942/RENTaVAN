package com.example.rentavan.presentation.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.rentavan.R
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.theme.GrisBoton
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModificarAlquilerPropietarioScreen(
    navController: NavController
) {
    // ESTADOS DE LOS CAMPOS
    var menuExpandido by remember { mutableStateOf(false) }
    var modelo by remember { mutableStateOf("Volkswagen Grand California") }
    var anio by remember { mutableStateOf("2022") }
    var peso by remember { mutableStateOf("3500") }
    var matricula by remember { mutableStateOf("1234-ABC") }
    var nombreTitular by remember { mutableStateOf("Juan Pérez") }
    var telefono by remember { mutableStateOf("600123456") }
    var email by remember { mutableStateOf("juan@mail.com") }
    var fechaInicio by remember { mutableStateOf("01/06/2026") }
    var fechaFin by remember { mutableStateOf("31/08/2026") }
    var darDeAlta by remember { mutableStateOf(true) }
    var numPlazas by remember { mutableStateOf("4") }
    var precio by remember { mutableStateOf("63") }

    // Estados para control de calendarios
    var mostrarCalInicio by remember { mutableStateOf(false) }
    var mostrarCalFin by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RENTaVAN", color = Amarillo, fontWeight = FontWeight.Bold) },
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = FondoOscuro)
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

            Text(
                text = "Modificar alquiler",
                color = Amarillo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // SECCIÓN SUPERIOR: DATOS BÁSICOS Y FOTO
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Datos:", color = Amarillo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo Modelo
                    CustomModTextField(value = modelo, onValueChange = { modelo = it }, label = "Modelo")
                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo Año (Numérico)
                    CustomModTextField(
                        value = anio,
                        onValueChange = { if (it.all { c -> c.isDigit() }) anio = it },
                        label = "Año",
                        isNumber = true
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Gestión de Imagen
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.size(120.dp, 80.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.caravana1),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(
                            onClick = { /* Editar foto [cite: 833] */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(30.dp).width(55.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Editar", color = FondoOscuro, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Button(
                            onClick = { /* Borrar foto [cite: 836] */ },
                            colors = ButtonDefaults.buttonColors(containerColor = GrisBoton),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(30.dp).width(55.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Borrar", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // RESTO DE CAMPOS TÉCNICOS
            Spacer(modifier = Modifier.height(12.dp))
            CustomModTextField(value = peso, onValueChange = { if (it.all { c -> c.isDigit() }) peso = it }, label = "Peso (Kg)", isNumber = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomModTextField(value = matricula, onValueChange = { matricula = it }, label = "Matrícula")
            Spacer(modifier = Modifier.height(12.dp))
            CustomModTextField(value = nombreTitular, onValueChange = { nombreTitular = it }, label = "Nombre del titular")
            Spacer(modifier = Modifier.height(12.dp))
            CustomModTextField(value = telefono, onValueChange = { if (it.all { c -> c.isDigit() }) telefono = it }, label = "Teléfono", isNumber = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomModTextField(value = email, onValueChange = { email = it }, label = "Correo Electrónico")

            // PERIODO DE DISPONIBILIDAD (Calendarios)
            Spacer(modifier = Modifier.height(24.dp))
            Text("Periodo de disponibilidad:", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("De:", color = Color.Gray, fontSize = 14.sp)
                Box(modifier = Modifier.weight(1f).padding(horizontal = 8.dp).clickable { mostrarCalInicio = true }) {
                    OutlinedTextField(
                        value = fechaInicio, onValueChange = {}, readOnly = true, enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(disabledBorderColor = Amarillo, disabledTextColor = Color.White),
                        shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()
                    )
                }
                Text("a", color = Color.Gray, fontSize = 14.sp)
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp).clickable { mostrarCalFin = true }) {
                    OutlinedTextField(
                        value = fechaFin, onValueChange = {}, readOnly = true, enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(disabledBorderColor = Amarillo, disabledTextColor = Color.White),
                        shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // GESTIÓN DE ALTA Y PRECIO
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = darDeAlta, onCheckedChange = { darDeAlta = it },
                    colors = CheckboxDefaults.colors(checkedColor = Amarillo)
                )
                Text("Dar de alta", color = Color.Gray)
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    CustomModTextField(value = numPlazas, onValueChange = { if (it.all { c -> c.isDigit() }) numPlazas = it }, label = "Número de Plazas", isNumber = true)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    CustomModTextField(value = precio, onValueChange = { if (it.all { c -> c.isDigit() }) precio = it }, label = "Precio (€/noche)", isNumber = true)
                }
            }

            // BOTONES FINALES
            Spacer(modifier = Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = { /* Guardar  */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(12.dp), modifier = Modifier.width(130.dp)
                ) {
                    Text("Guardar", color = FondoOscuro, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = GrisBoton),
                    shape = RoundedCornerShape(12.dp), modifier = Modifier.width(130.dp)
                ) {
                    Text("Cancelar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Lógica de DatePickers (abreviada para claridad)
        if (mostrarCalInicio || mostrarCalFin) {
            val state = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { mostrarCalInicio = false; mostrarCalFin = false },
                confirmButton = {
                    TextButton(onClick = {
                        val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(state.selectedDateMillis ?: 0L))
                        if (mostrarCalInicio) fechaInicio = fecha else fechaFin = fecha
                        mostrarCalInicio = false; mostrarCalFin = false
                    }) { Text("OK", color = Amarillo) }
                }
            ) { DatePicker(state = state) }
        }
    }
}

// Componente auxiliar para reducir repetición de código
@Composable
fun CustomModTextField(value: String, onValueChange: (String) -> Unit, label: String, isNumber: Boolean = false) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray, fontSize = 12.sp) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Amarillo, unfocusedBorderColor = Amarillo,
            focusedTextColor = Color.White, unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth(),
        keyboardOptions = if (isNumber) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions.Default
    )
}

@Preview(showBackground = true)
@Composable
fun ModificarAlquilerPropietarioScreenPreview() {
    ModificarAlquilerPropietarioScreen(
        navController = rememberNavController()
    )
}