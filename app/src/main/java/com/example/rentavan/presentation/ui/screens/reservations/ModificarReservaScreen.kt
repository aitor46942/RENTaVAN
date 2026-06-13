package com.example.rentavan.presentation.ui.screens.reservations

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.runtime.collectAsState
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
import com.example.rentavan.presentation.ui.viewmodel.reservations.ModificarReservaViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModificarReservaScreen(
    navController: NavController,
    reservaId: Int = 0,
    viewModel: ModificarReservaViewModel = viewModel()
) {
    LaunchedEffect(reservaId) {
        viewModel.cargarDetalleReserva(reservaId)
    }

    val numViajeros by viewModel.numViajeros.collectAsState()
    val fechaInicio by viewModel.fechaInicio.collectAsState()
    val fechaFin by viewModel.fechaFin.collectAsState()
    val reservaOriginal by viewModel.reservaOriginal.collectAsState()

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
                        fontSize = 40.sp,
                        fontFamily = jersey10Family,
                        letterSpacing = 2.sp,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú", tint = Amarillo)
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Modificar reserva",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Reserva #$reservaId",
                color = Amarillo.copy(alpha = 0.7f),
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Caravana preview card
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
                        contentDescription = "Caravana seleccionada",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp, 72.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text("Caravana alquilada", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text("Modifica el periodo o el número de viajeros", color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp, lineHeight = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Datos section
            Text(
                "Datos del alquiler",
                color = Color.White.copy(alpha = 0.45f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 10.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Número de viajeros", color = Color.White.copy(alpha = 0.55f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = numViajeros,
                        onValueChange = { viewModel.onViajerosChange(it) },
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dates section
            Text(
                "Periodo de disponibilidad",
                color = Color.White.copy(alpha = 0.45f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 10.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Desde", color = Color.White.copy(alpha = 0.55f), fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = fechaInicio,
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            placeholder = { Text("DD/MM/YYYY", color = Color.Gray) },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledBorderColor = Amarillo.copy(alpha = 0.5f),
                                disabledTextColor = Color.White,
                                disabledPlaceholderColor = Color.Gray,
                                disabledContainerColor = FondoOscuro
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(52.dp).clickable { mostrarCalendarioInicio = true }
                        )
                    }
                    Text("—", color = Color.White.copy(alpha = 0.3f), fontSize = 18.sp, modifier = Modifier.padding(top = 20.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Hasta", color = Color.White.copy(alpha = 0.55f), fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = fechaFin,
                            onValueChange = {},
                            readOnly = true,
                            enabled = false,
                            placeholder = { Text("DD/MM/YYYY", color = Color.Gray) },
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledBorderColor = Amarillo.copy(alpha = 0.5f),
                                disabledTextColor = Color.White,
                                disabledPlaceholderColor = Color.Gray,
                                disabledContainerColor = FondoOscuro
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(52.dp).clickable { mostrarCalendarioFin = true }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            val formateador = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            if (mostrarCalendarioInicio) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = { mostrarCalendarioInicio = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { milisegundos ->
                                viewModel.onFechaInicioChange(formateador.format(Date(milisegundos)))
                            }
                            mostrarCalendarioInicio = false
                        }) { Text("Aceptar", color = Amarillo) }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarCalendarioInicio = false }) {
                            Text("Cancelar", color = Color.Gray)
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        colors = DatePickerDefaults.colors(
                            selectedDayContainerColor = Amarillo,
                            selectedDayContentColor = FondoOscuro,
                            todayDateBorderColor = Amarillo,
                            todayContentColor = Amarillo
                        )
                    )
                }
            }

            if (mostrarCalendarioFin) {
                val datePickerState = rememberDatePickerState()
                DatePickerDialog(
                    onDismissRequest = { mostrarCalendarioFin = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { milisegundos ->
                                viewModel.onFechaFinChange(formateador.format(Date(milisegundos)))
                            }
                            mostrarCalendarioFin = false
                        }) { Text("Aceptar", color = Amarillo) }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarCalendarioFin = false }) {
                            Text("Cancelar", color = Color.Gray)
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        colors = DatePickerDefaults.colors(
                            selectedDayContainerColor = Amarillo,
                            selectedDayContentColor = FondoOscuro,
                            todayDateBorderColor = Amarillo,
                            todayContentColor = Amarillo
                        )
                    )
                }
            }

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
                    onClick = {
                        val idReservaActual = reservaOriginal?.reservaId ?: reservaId
                        viewModel.guardarCambios(idReservaActual) {
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Amarillo),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f).height(50.dp)
                ) {
                    Text("Guardar", color = FondoOscuro, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModificarReservaScreenPreview() {
    ModificarReservaScreen(navController = rememberNavController())
}