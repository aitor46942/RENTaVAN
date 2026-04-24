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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.rentavan.presentation.ui.viewmodel.profile.ModificarAlquilerViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModificarAlquilerPropietarioScreen(
    navController: NavController,
    viewModel: ModificarAlquilerViewModel = viewModel() // Inyectamos ViewModel
) {
    // Escucha para navegar atrás al terminar de guardar
    LaunchedEffect(viewModel.guardadoExitoso) {
        if (viewModel.guardadoExitoso) {
            navController.popBackStack()
        }
    }

    // Estados de UI locales (para el calendario)
    var mostrarCalInicio by remember { mutableStateOf(false) }
    var mostrarCalFin by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RENTaVAN", color = Amarillo, fontWeight = FontWeight.Bold) },
                actions = {
                    Box {
                        IconButton(onClick = { viewModel.abrirMenu() }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Amarillo
                            )
                        }

                        DropdownMenu(
                            expanded = viewModel.menuExpandido,
                            onDismissRequest = { viewModel.cerrarMenu() },
                            modifier = Modifier.background(Color(0xFF333333))
                        ) {
                            DropdownMenuItem(
                                text = { Text("Ajustes", color = Color.White) },
                                onClick = {
                                    viewModel.cerrarMenu()
                                    navController.navigate(Screen.Ajustes.route)
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

        if (viewModel.isLoadingInicial) {
            // Pantalla de carga mientras trae los datos de la caravana
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Amarillo)
            }
        } else {
            // FORMULARIO CON LOS DATOS
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

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Datos:", color = Amarillo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))

                        CustomModTextField(value = viewModel.modelo, onValueChange = { viewModel.modelo = it }, label = "Modelo")
                        Spacer(modifier = Modifier.height(8.dp))
                        CustomModTextField(
                            value = viewModel.anio,
                            onValueChange = { if (it.all { c -> c.isDigit() }) viewModel.anio = it },
                            label = "Año",
                            isNumber = true
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.size(120.dp, 80.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.caravana1), // Asegúrate de tener esta imagen
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            Button(
                                onClick = { },
                                colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(30.dp).width(55.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text("Editar", color = FondoOscuro, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Button(
                                onClick = { },
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

                Spacer(modifier = Modifier.height(12.dp))
                CustomModTextField(value = viewModel.peso, onValueChange = { if (it.all { c -> c.isDigit() }) viewModel.peso = it }, label = "Peso (Kg)", isNumber = true)
                Spacer(modifier = Modifier.height(12.dp))
                CustomModTextField(value = viewModel.matricula, onValueChange = { viewModel.matricula = it }, label = "Matrícula")
                Spacer(modifier = Modifier.height(12.dp))
                CustomModTextField(value = viewModel.nombreTitular, onValueChange = { viewModel.nombreTitular = it }, label = "Nombre del titular")
                Spacer(modifier = Modifier.height(12.dp))
                CustomModTextField(value = viewModel.telefono, onValueChange = { if (it.all { c -> c.isDigit() }) viewModel.telefono = it }, label = "Teléfono", isNumber = true)
                Spacer(modifier = Modifier.height(12.dp))
                CustomModTextField(value = viewModel.email, onValueChange = { viewModel.email = it }, label = "Correo Electrónico")

                Spacer(modifier = Modifier.height(24.dp))
                Text("Periodo de disponibilidad:", color = Color.Gray, fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("De:", color = Color.Gray, fontSize = 14.sp)
                    Box(modifier = Modifier.weight(1f).padding(horizontal = 8.dp).clickable { mostrarCalInicio = true }) {
                        OutlinedTextField(
                            value = viewModel.fechaInicio, onValueChange = {}, readOnly = true, enabled = false,
                            colors = OutlinedTextFieldDefaults.colors(disabledBorderColor = Amarillo, disabledTextColor = Color.White),
                            shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Text("a", color = Color.Gray, fontSize = 14.sp)
                    Box(modifier = Modifier.weight(1f).padding(start = 8.dp).clickable { mostrarCalFin = true }) {
                        OutlinedTextField(
                            value = viewModel.fechaFin, onValueChange = {}, readOnly = true, enabled = false,
                            colors = OutlinedTextFieldDefaults.colors(disabledBorderColor = Amarillo, disabledTextColor = Color.White),
                            shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(
                        checked = viewModel.darDeAlta, onCheckedChange = { viewModel.darDeAlta = it },
                        colors = CheckboxDefaults.colors(checkedColor = Amarillo)
                    )
                    Text("Dar de alta", color = Color.Gray)
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        CustomModTextField(value = viewModel.numPlazas, onValueChange = { if (it.all { c -> c.isDigit() }) viewModel.numPlazas = it }, label = "Número de Plazas", isNumber = true)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        CustomModTextField(value = viewModel.precio, onValueChange = { if (it.all { c -> c.isDigit() }) viewModel.precio = it }, label = "Precio (€/noche)", isNumber = true)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (viewModel.isGuardando) {
                    CircularProgressIndicator(color = Amarillo)
                } else {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(
                            onClick = { viewModel.guardarCambios() },
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
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Lógica de DatePickers
            if (mostrarCalInicio || mostrarCalFin) {
                val state = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = { mostrarCalInicio = false; mostrarCalFin = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(state.selectedDateMillis ?: 0L))
                            if (mostrarCalInicio) viewModel.fechaInicio = fecha else viewModel.fechaFin = fecha
                            mostrarCalInicio = false; mostrarCalFin = false
                        }) { Text("OK", color = Amarillo) }
                    }
                ) { DatePicker(state = state) }
            }
        }
    }
}

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
    ModificarAlquilerPropietarioScreen(navController = rememberNavController())
}