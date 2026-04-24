package com.example.rentavan.presentation.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rentavan.presentation.ui.navigation.Screen
import com.example.rentavan.presentation.ui.theme.Amarillo
import com.example.rentavan.presentation.ui.theme.FondoOscuro
import com.example.rentavan.presentation.ui.viewmodel.auth.RegisterViewModel

val SuperficieOscura = Color(0xFF2C2C2C)

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = viewModel() // Inyectamos ViewModel
) {
    // Escuchamos el éxito para navegar
    LaunchedEffect(viewModel.registroExitoso) {
        if (viewModel.registroExitoso) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoOscuro)
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "RENTaVAN",
            color = Amarillo,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campos usando el ViewModel
        OutlinedTextField(
            value = viewModel.usuario,
            onValueChange = { viewModel.onUsuarioChange(it) },
            placeholder = { Text("Usuario", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo, unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura, unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.correo,
            onValueChange = { viewModel.onCorreoChange(it) },
            placeholder = { Text("Correo Electrónico", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo, unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura, unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            placeholder = { Text("Nombre", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo, unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura, unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.apellidos,
            onValueChange = { viewModel.onApellidosChange(it) },
            placeholder = { Text("Apellidos", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo, unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura, unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.contrasena,
            onValueChange = { viewModel.onContrasenaChange(it) },
            placeholder = { Text("Contraseña", color = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo, unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura, unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (viewModel.errorVisible) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = viewModel.mensajeError,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { navController.popBackStack() }) {
            Text("¿Ya tiene cuenta? Inicie Sesión.", color = Amarillo, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (viewModel.isLoading) {
            CircularProgressIndicator(color = Amarillo, modifier = Modifier.padding(bottom = 32.dp))
        } else {
            Button(
                onClick = { viewModel.register() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray, contentColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
            ) {
                Text(text = "Registrarse", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}