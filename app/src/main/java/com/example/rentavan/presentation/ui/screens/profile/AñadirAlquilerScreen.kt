package com.example.rentavan.presentation.ui.screens.profile
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
fun AnadirAlquilerScreen(
    navController: NavController
) {
    var menuExpandido by remember { mutableStateOf(false) }
    // ESTADOS INICIALES (Vacíos para una nueva entrada)
    var modelo by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var nombreTitular by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var darDeAlta by remember { mutableStateOf(false) } // Por defecto no publicada
    var numPlazas by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    // Estados para los diálogos de fecha
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

            // Título de la pantalla
            Text(
                text = "Añadir alquiler",
                color = Amarillo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // SECCIÓN: FOTO Y DATOS PRIMARIOS
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Datos:", color = Amarillo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo Modelo
                    CustomInputAdd(value = modelo, onValueChange = { modelo = it }, label = "Modelo")
                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo Año
                    CustomInputAdd(
                        value = anio,
                        onValueChange = { if (it.all { c -> c.isDigit() }) anio = it },
                        label = "Año",
                        isNumber = true
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Área de Imagen (Placeholder hasta que se suba una)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.size(120.dp, 80.dp),
                        colors = CardDefaults.cardColors(containerColor = GrisBoton)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(40.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Botones de gestión de imagen
                    Row {
                        Button(
                            onClick = { /* Lógica para abrir galería */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(30.dp).width(55.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Añadir", color = FondoOscuro, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // BLOQUE DE CAMPOS TÉCNICOS Y CONTACTO
            Spacer(modifier = Modifier.height(12.dp))
            CustomInputAdd(value = peso, onValueChange = { if (it.all { c -> c.isDigit() }) peso = it }, label = "Peso (Kg)", isNumber = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomInputAdd(value = matricula, onValueChange = { matricula = it }, label = "Matrícula")
            Spacer(modifier = Modifier.height(12.dp))
            CustomInputAdd(value = nombreTitular, onValueChange = { nombreTitular = it }, label = "Nombre del titular")
            Spacer(modifier = Modifier.height(12.dp))
            CustomInputAdd(value = telefono, onValueChange = { if (it.all { c -> c.isDigit() }) telefono = it }, label = "Teléfono", isNumber = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomInputAdd(value = email, onValueChange = { email = it }, label = "Correo Electrónico")

            // DISPONIBILIDAD
            Spacer(modifier = Modifier.height(24.dp))
            Text("Periodo de disponibilidad:", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.fillMaxWidth())

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                Text("De:", color = Color.Gray, fontSize = 14.sp)
                Box(modifier = Modifier.weight(1f).padding(horizontal = 8.dp).clickable { mostrarCalInicio = true }) {
                    OutlinedTextField(
                        value = fechaInicio, onValueChange = {}, readOnly = true, enabled = false,
                        placeholder = { Text("DD/MM/YYYY", fontSize = 12.sp) },
                        colors = OutlinedTextFieldDefaults.colors(disabledBorderColor = Amarillo, disabledTextColor = Color.White),
                        shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()
                    )
                }
                Text("a", color = Color.Gray, fontSize = 14.sp)
                Box(modifier = Modifier.weight(1f).padding(start = 8.dp).clickable { mostrarCalFin = true }) {
                    OutlinedTextField(
                        value = fechaFin, onValueChange = {}, readOnly = true, enabled = false,
                        placeholder = { Text("DD/MM/YYYY", fontSize = 12.sp) },
                        colors = OutlinedTextFieldDefaults.colors(disabledBorderColor = Amarillo, disabledTextColor = Color.White),
                        shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // CONFIGURACIÓN DE OFERTA
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(checked = darDeAlta, onCheckedChange = { darDeAlta = it }, colors = CheckboxDefaults.colors(checkedColor = Amarillo))
                Text("Dar de alta", color = Color.Gray)
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    CustomInputAdd(value = numPlazas, onValueChange = { if (it.all { c -> c.isDigit() }) numPlazas = it }, label = "Número de Plazas", isNumber = true)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    CustomInputAdd(value = precio, onValueChange = { if (it.all { c -> c.isDigit() }) precio = it }, label = "Precio (€/noche)", isNumber = true)
                }
            }

            // BOTONES DE ACCIÓN
            Spacer(modifier = Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    onClick = { /* Validar y Guardar en BD */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(12.dp), modifier = Modifier.width(130.dp)
                ) {
                    Text("Guardar", color = FondoOscuro, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick =  { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = GrisBoton),
                    shape = RoundedCornerShape(12.dp), modifier = Modifier.width(130.dp)
                ) {
                    Text("Cancelar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Diálogos de calendario
        if (mostrarCalInicio || mostrarCalFin) {
            val state = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { mostrarCalInicio = false; mostrarCalFin = false },
                confirmButton = {
                    TextButton(onClick = {
                        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(state.selectedDateMillis ?: 0L))
                        if (mostrarCalInicio) fechaInicio = format else fechaFin = format
                        mostrarCalInicio = false; mostrarCalFin = false
                    }) { Text("Aceptar", color = Amarillo) }
                }
            ) { DatePicker(state = state) }
        }
    }
}

@Composable
fun CustomInputAdd(value: String, onValueChange: (String) -> Unit, label: String, isNumber: Boolean = false) {
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
fun AnadirAlquilerScreenPreview() {
    AnadirAlquilerScreen(navController = rememberNavController())
}