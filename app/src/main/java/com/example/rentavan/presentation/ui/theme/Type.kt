package com.example.rentavan.presentation.ui.theme

import com.example.rentavan.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
val jersey10Family = FontFamily(
    Font(R.font.jersey_10, FontWeight.Normal)
)
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = jersey10Family,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
