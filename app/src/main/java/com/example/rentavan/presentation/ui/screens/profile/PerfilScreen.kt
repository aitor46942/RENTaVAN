package com.example.rentavan.presentation.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.presentation.ui.navigation.Screen
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.theme.SuperficieOscura
import com.example.rentavan.presentation.ui.viewmodel.profile.PerfilViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    viewModel: PerfilViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (viewModel.modoEdicion) "Editar perfil" else "Mi Perfil",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (viewModel.modoEdicion) viewModel.cancelarEdicion()
                        else navController.popBackStack()
                    }) {
                        Icon(
                            if (viewModel.modoEdicion) Icons.Default.Close else Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Amarillo
                        )
                    }
                },
                actions = {
                    if (!viewModel.modoEdicion && viewModel.perfilUsuario != null) {
                        IconButton(onClick = { viewModel.activarEdicion() }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Amarillo)
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.isLoading) {
                Box(modifier = Modifier.fillMaxSize().padding(top = 80.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Amarillo)
                }
            } else {
                viewModel.perfilUsuario?.let { perfil ->

                    // Avatar header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SuperficieOscura)
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Surface(
                                modifier = Modifier.size(110.dp),
                                shape = CircleShape,
                                color = Amarillo
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = perfil.nombre.firstOrNull()?.toString()?.uppercase() ?: "?",
                                        fontSize = 52.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FondoOscuro
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = perfil.nombre,
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = perfil.email,
                                color = Color.White.copy(alpha = 0.45f),
                                fontSize = 13.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {

                        if (viewModel.modoEdicion) {
                            // ── MODO EDICIÓN ──
                            Text(
                                "Información personal",
                                color = Color.White.copy(alpha = 0.45f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
                            )

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    EditField(
                                        label = "Nombre",
                                        value = viewModel.nombreEditado,
                                        icon = Icons.Default.Person,
                                        onValueChange = { viewModel.nombreEditado = it }
                                    )
                                    Spacer(modifier = Modifier.height(14.dp))
                                    EditField(
                                        label = "Teléfono",
                                        value = viewModel.telefonoEditado,
                                        icon = Icons.Default.Phone,
                                        onValueChange = { viewModel.telefonoEditado = it }
                                    )
                                }
                            }

                            // Campo email (solo lectura)
                            Spacer(modifier = Modifier.height(10.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Email, contentDescription = null, tint = Color.White.copy(alpha = 0.3f), modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("Correo electrónico (no editable)", color = Color.White.copy(alpha = 0.3f), fontSize = 11.sp)
                                        Text(perfil.email, color = Color.White.copy(alpha = 0.4f), fontSize = 14.sp)
                                    }
                                }
                            }

                            if (viewModel.errorGuardado.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = viewModel.errorGuardado,
                                    color = Color(0xFFFF6B6B),
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            if (viewModel.isSaving) {
                                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator(color = Amarillo)
                                }
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = { viewModel.cancelarEdicion() },
                                        border = BorderStroke(1.dp, Amarillo.copy(alpha = 0.4f)),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Amarillo),
                                        shape = RoundedCornerShape(14.dp),
                                        modifier = Modifier.weight(1f).height(52.dp)
                                    ) {
                                        Text("Cancelar", fontWeight = FontWeight.Bold)
                                    }
                                    Button(
                                        onClick = { viewModel.guardarCambios() },
                                        colors = ButtonDefaults.buttonColors(containerColor = Amarillo, contentColor = FondoOscuro),
                                        shape = RoundedCornerShape(14.dp),
                                        modifier = Modifier.weight(1f).height(52.dp)
                                    ) {
                                        Text("Guardar", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }

                        } else {
                            // ── MODO VISTA ──
                            Text(
                                "Información",
                                color = Color.White.copy(alpha = 0.45f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
                            )

                            InfoCard(icono = Icons.Default.Email, titulo = "Correo Electrónico", valor = perfil.email)

                            Spacer(modifier = Modifier.height(10.dp))

                            InfoCard(
                                icono = Icons.Default.Phone,
                                titulo = "Teléfono",
                                valor = perfil.telefono.ifEmpty { "Sin teléfono — pulsa editar para añadir" },
                                valorOpaco = perfil.telefono.isEmpty()
                            )

                            Spacer(modifier = Modifier.height(28.dp))

                            Button(
                                onClick = { navController.navigate(Screen.MisCaravanas.route) },
                                colors = ButtonDefaults.buttonColors(containerColor = Amarillo, contentColor = FondoOscuro),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.fillMaxWidth().height(54.dp)
                            ) {
                                Icon(Icons.Default.DirectionsCar, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Gestionar Mis Caravanas", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedButton(
                                onClick = {
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF6B6B)),
                                border = BorderStroke(1.dp, Color(0xFFFF6B6B).copy(alpha = 0.6f)),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier.fillMaxWidth().height(54.dp)
                            ) {
                                Text("Cerrar Sesión", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun InfoCard(
    icono: ImageVector,
    titulo: String,
    valor: String,
    valorOpaco: Boolean = false
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SuperficieOscura),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Amarillo.copy(alpha = 0.2f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = Amarillo.copy(alpha = 0.12f), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = Amarillo, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(titulo, color = Color.White.copy(alpha = 0.45f), fontSize = 11.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    valor,
                    color = if (valorOpaco) Color.White.copy(alpha = 0.3f) else Color.White,
                    fontSize = 15.sp,
                    fontWeight = if (valorOpaco) FontWeight.Normal else FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun EditField(
    label: String,
    value: String,
    icon: ImageVector,
    onValueChange: (String) -> Unit
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = Amarillo, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(label, color = Color.White.copy(alpha = 0.55f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo,
                unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
                focusedContainerColor = FondoOscuro,
                unfocusedContainerColor = FondoOscuro,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Amarillo
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen(navController = rememberNavController())
}