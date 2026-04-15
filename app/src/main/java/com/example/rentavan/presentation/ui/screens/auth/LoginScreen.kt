package com.example.rentavan.presentation.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//colores usados
val Amarillo = Color(0xFFFFB800)
val FondoOscuro = Color(0xFF1A1A1A)
val SuperficieOscura = Color(0xFF2C2C2C)

@Composable
fun LoginScreen(
    onEntrarClick: () -> Unit,
    onRegistrarseClick: () -> Unit
) {

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoOscuro)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Texto arriba
        Text(
            text = "RENTaVAN",
            color = Amarillo,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(48.dp))

        //usuario
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

        //contraseña
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

        //error datos
        if (errorVisible) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Usuario y contraseña son obligatorios",
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    if (usuario.isBlank() || contrasena.isBlank()) {
                        errorVisible = true
                    } else {
                        errorVisible = false
                        onEntrarClick()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Amarillo,
                    contentColor = FondoOscuro
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Entrar", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onRegistrarseClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF555555),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Registrarse", fontWeight = FontWeight.Bold)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onEntrarClick = {},
        onRegistrarseClick = {}
    )
}
