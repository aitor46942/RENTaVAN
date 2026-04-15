package com.example.rentavan.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Amarillo,          // Color principal (botones, iconos destacados)
    secondary = GrisBoton,       // Botones secundarios (como "Cancelar")
    background = FondoOscuro,    // Fondo general de las pantallas (Scaffold)
    surface = FondoOscuro,       // Fondo de tarjetas o menús
    onPrimary = FondoOscuro,     // Color del texto que va encima del color amarillo
    onBackground = Blanco,       // Color del texto que va encima del fondo oscuro
    onSurface = Blanco
)

private val LightColorScheme = lightColorScheme(
    primary = Amarillo,
    secondary = GrisBoton,
    background = FondoOscuro,
    surface = FondoOscuro,
    onPrimary = FondoOscuro,
    onBackground = Blanco,
    onSurface = Blanco
)

@Composable
fun RENTaVANTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}