package com.example.healthpal.utility

import android.os.Build
import android.util.DisplayMetrics
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.healthpal.ui.theme.BackgroundDark
import com.example.healthpal.ui.theme.BackgroundLight


object backgroundSetter {

    var IsLightTheme: Boolean = false

    @Composable
    fun BackgroundAnimation(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = true,
    ) {
        var colors = listOf<Color>()
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme ->{
                IsLightTheme = false
                colors = BackgroundDark
            }
            else -> {
                IsLightTheme = true
                colors = BackgroundLight
            }
        }
        val infiniteTransition = rememberInfiniteTransition(label = "")
        val color1 by infiniteTransition.animateColor(
            initialValue = colors[0],
            targetValue = colors[1],
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        val color2 by infiniteTransition.animateColor(
            initialValue = colors[1],
            targetValue = colors[2],
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        val color3 by infiniteTransition.animateColor(
            initialValue = colors[2],
            targetValue = colors[0],
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 5000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(color1, color2, color3),
                        radius = 500f
                    )
                )
                .wrapContentSize(Alignment.Center)
        )
    }
}

