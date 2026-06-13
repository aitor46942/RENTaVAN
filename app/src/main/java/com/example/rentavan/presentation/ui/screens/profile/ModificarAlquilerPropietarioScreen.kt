package com.example.rentavan.presentation.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.rentavan.presentation.ui.theme.SuperficieOscura
import com.example.rentavan.presentation.ui.theme.jersey10Family
import com.example.rentavan.presentation.ui.viewmodel.profile.ModificarAlquilerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModificarAlquilerPropietarioScreen(
    navController: NavController,
    viewModel: ModificarAlquilerViewModel = viewModel()
) {
    LaunchedEffect(viewModel.guardadoExitoso) {
        if (viewModel.guardadoExitoso) {
            navController.popBackStack()
        }
    }

    var mostrarCalInicio by remember { mutableStateOf(false) }
    var mostrarCalFin by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "RENTaVAN",
                        color = Amarillo,
                        fontFamily = jersey10Family,
                        fontSize = 40.sp,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    )
                },
                actions = {
                    Box {
                        IconButton(onClick = { viewModel.abrirMenu() }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú", tint = Amarillo)
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
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Amarillo)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Header with image
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Amarillo.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.caravana1),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(90.dp, 70.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Modificar caravana", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("Edita los datos de tu vehículo", color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
                        }
                        Column {
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp).width(60.dp),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                            ) {
                                Text("Editar", color = FondoOscuro, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedButton(
                                onClick = {},
                                border = BorderStroke(1.dp, Color(0xFFFF6B6B).copy(alpha = 0.5f)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF6B6B)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(32.dp).width(60.dp),
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                            ) {
                                Text("Borrar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Datos del vehículo
                PropSectionLabel("Datos del vehículo")
                PropFormCard {
                    PropField("Modelo", viewModel.modelo) { viewModel.modelo = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    PropField("Año", viewModel.anio, isNumber = true) {
                        if (it.all { c -> c.isDigit() }) viewModel.anio = it
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    PropField("Peso (Kg)", viewModel.peso, isNumber = true) {
                        if (it.all { c -> c.isDigit() }) viewModel.peso = it
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    PropField("Matrícula", viewModel.matricula) { viewModel.matricula = it }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Datos del titular
                PropSectionLabel("Datos del titular")
                PropFormCard {
                    PropField("Nombre del titular", viewModel.nombreTitular) { viewModel.nombreTitular = it }
                    Spacer(modifier = Modifier.height(12.dp))
                    PropField("Teléfono", viewModel.telefono, isNumber = true) {
                        if (it.all { c -> c.isDigit() }) viewModel.telefono = it
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    PropField("Correo Electrónico", viewModel.email) { viewModel.email = it }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Disponibilidad
                PropSectionLabel("Periodo de disponibilidad")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Desde", color = Color.White.copy(alpha = 0.55f), fontSize = 11.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(modifier = Modifier.fillMaxWidth().clickable { mostrarCalInicio = true }) {
                                    OutlinedTextField(
                                        value = viewModel.fechaInicio,
                                        onValueChange = {},
                                        readOnly = true,
                                        enabled = false,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            disabledBorderColor = Amarillo.copy(alpha = 0.5f),
                                            disabledTextColor = Color.White,
                                            disabledContainerColor = FondoOscuro
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                            Text("—", color = Color.White.copy(alpha = 0.3f), fontSize = 18.sp, modifier = Modifier.padding(top = 18.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Hasta", color = Color.White.copy(alpha = 0.55f), fontSize = 11.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(modifier = Modifier.fillMaxWidth().clickable { mostrarCalFin = true }) {
                                    OutlinedTextField(
                                        value = viewModel.fechaFin,
                                        onValueChange = {},
                                        readOnly = true,
                                        enabled = false,
                                        colors = OutlinedTextFieldDefaults.colors(
                                            disabledBorderColor = Amarillo.copy(alpha = 0.5f),
                                            disabledTextColor = Color.White,
                                            disabledContainerColor = FondoOscuro
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = viewModel.darDeAlta,
                                onCheckedChange = { viewModel.darDeAlta = it },
                                colors = CheckboxDefaults.colors(checkedColor = Amarillo)
                            )
                            Text("Dar de alta", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Precios y plazas
                PropSectionLabel("Precio y capacidad")
                PropFormCard {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            PropField("Nº Plazas", viewModel.numPlazas, isNumber = true) {
                                if (it.all { c -> c.isDigit() }) viewModel.numPlazas = it
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            PropField("Precio (€/noche)", viewModel.precio, isNumber = true) {
                                if (it.all { c -> c.isDigit() }) viewModel.precio = it
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                if (viewModel.isGuardando) {
                    CircularProgressIndicator(color = Amarillo, modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { navController.popBackStack() },
                            border = BorderStroke(1.dp, Amarillo.copy(alpha = 0.4f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Amarillo),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f).height(50.dp)
                        ) {
                            Text("Cancelar", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { viewModel.guardarCambios() },
                            colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f).height(50.dp)
                        ) {
                            Text("Guardar", color = FondoOscuro, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            if (mostrarCalInicio || mostrarCalFin) {
                val state = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = { mostrarCalInicio = false; mostrarCalFin = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                Date(state.selectedDateMillis ?: 0L)
                            )
                            if (mostrarCalInicio) viewModel.fechaInicio = fecha else viewModel.fechaFin = fecha
                            mostrarCalInicio = false; mostrarCalFin = false
                        }) { Text("OK", color = Amarillo) }
                    }
                ) {
                    DatePicker(
                        state = state,
                        colors = DatePickerDefaults.colors(
                            selectedDayContainerColor = Amarillo,
                            selectedDayContentColor = FondoOscuro,
                            todayDateBorderColor = Amarillo,
                            todayContentColor = Amarillo
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun PropSectionLabel(text: String) {
    Text(
        text = text,
        color = Color.White.copy(alpha = 0.45f),
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
    )
}

@Composable
private fun PropFormCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun CustomModTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isNumber: Boolean = false
) {
    PropField(label = label, value = value, isNumber = isNumber, onValueChange = onValueChange)
}

@Composable
private fun PropField(label: String, value: String, isNumber: Boolean = false, onValueChange: (String) -> Unit) {
    Column {
        Text(label, color = Color.White.copy(alpha = 0.55f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo,
                unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                focusedContainerColor = FondoOscuro,
                unfocusedContainerColor = FondoOscuro,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Amarillo
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = if (isNumber) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions.Default
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ModificarAlquilerPropietarioScreenPreview() {
    ModificarAlquilerPropietarioScreen(navController = rememberNavController())
}