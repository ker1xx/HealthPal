package com.example.healthpal

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import com.example.healthpal.ui.theme.BackgroundDark
import com.example.healthpal.ui.theme.BackgroundLight

object backgroundSetter {
    @Composable
    fun SetBackground(
        darkTheme: Boolean = isSystemInDarkTheme(),
        dynamicColor: Boolean = true,
    ): Brush {
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> return BackgroundDark
            else -> return BackgroundLight
        }
        return BackgroundDark
    }
}