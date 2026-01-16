package barant.curso.androidbluetoothble.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun AndroidBluetoothBleTheme(
    darkTheme: Boolean = true, // diseño forzado a dark
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = GATTTypography,
        shapes = GATTShapes,
        content = content
    )
}