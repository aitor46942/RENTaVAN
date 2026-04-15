package com.example.rentavan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.rentavan.presentation.ui.navigation.NavGraph
import com.example.rentavan.presentation.ui.theme.RENTaVANTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RENTaVANTheme {
                NavGraph()
            }
        }
    }
}