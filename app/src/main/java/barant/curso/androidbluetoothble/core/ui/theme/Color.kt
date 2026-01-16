package barant.curso.androidbluetoothble.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

// Backgrounds
val DarkBackground = Color(0xFF121418)
val SurfaceColor = Color(0xFF1C2026)

// Accents
val PrimaryCyan = Color(0xFF00E5FF)
val DisconnectRed = Color(0xFFFF5252)

// Text
val TextWhite = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFF9BA1A6)

// Color Scheme
val DarkColorScheme = darkColorScheme(
    primary = PrimaryCyan,
    onPrimary = Color.Black,

    secondary = PrimaryCyan,

    background = DarkBackground,
    onBackground = TextWhite,

    surface = SurfaceColor,
    onSurface = TextWhite,

    error = DisconnectRed
)