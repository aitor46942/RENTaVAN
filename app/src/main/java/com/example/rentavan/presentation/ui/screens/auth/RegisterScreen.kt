package com.example.rentavan.presentation.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onRegistrarseClick: () -> Unit,
    onIniciarSesionClick: () -> Unit
) {
    // Estado local de cada campo — mutableStateOf del tema 1
    var usuario by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoOscuro)
            .padding(horizontal = 32.dp)
            .verticalScroll(rememberScrollState()),  // scroll si el teclado empuja
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

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            placeholder = { Text("Usuario", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo,
                unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura,
                unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            placeholder = { Text("Correo Electrónico", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo,
                unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura,
                unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = { Text("Nombre", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo,
                unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura,
                unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            placeholder = { Text("Apellidos", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo,
                unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura,
                unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            placeholder = { Text("Contraseña", color = Color.Gray) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarillo,
                unfocusedBorderColor = Amarillo,
                focusedContainerColor = SuperficieOscura,
                unfocusedContainerColor = SuperficieOscura,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorVisible) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Todos los campos son obligatorios",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Enlace "¿Ya tiene cuenta?"
        TextButton(onClick = onIniciarSesionClick) {
            Text(
                text = "¿Ya tiene cuenta? Inicie Sesión.",
                color = Amarillo,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            onClick = {
                if (usuario.isBlank() || correo.isBlank() || nombre.isBlank()
                    || apellidos.isBlank() || contrasena.isBlank()
                ) {
                    errorVisible = true
                } else {
                    errorVisible = false
                    onRegistrarseClick()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text(text = "Registrarse", fontWeight = FontWeight.Bold)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onIniciarSesionClick = {},
        onRegistrarseClick = {}
    )
}
