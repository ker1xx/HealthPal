package com.example.healthpal.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.healthpal.R

// Set of Material typography styles to start with

val PtSans = FontFamily(
    Font(R.font.pt_sans_regular, FontWeight.Normal),
    Font(R.font.pt_sans_bold, FontWeight.Bold),
)

val PtSansCaption = FontFamily(
    Font(R.font.pt_sans_caption_regular, FontWeight.Normal),
    Font(R.font.pt_sans_caption_bold, FontWeight.Bold)
)

val Typography = Typography(
    labelMedium = TextStyle(
        fontFamily = PtSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)