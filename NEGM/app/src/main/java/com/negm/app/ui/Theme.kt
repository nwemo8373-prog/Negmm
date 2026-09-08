package com.negm.app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val NegmBackground = Color(0xFF08090D)
val NegmPanel = Color(0xFF101218)
val NegmPanel2 = Color(0xFF171922)
val NegmAccent = Color(0xFF7C5CFF)
val NegmText = Color(0xFFFFFFFF)
val NegmMuted = Color(0xFF9DA2B1)
val NegmGreen = Color(0xFF35D07F)

private val NegmColors = darkColorScheme(
    primary = NegmAccent,
    background = NegmBackground,
    surface = NegmPanel,
    onBackground = NegmText,
    onSurface = NegmText
)

@Composable
fun NegmTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = NegmColors,
        content = content
    )
}
