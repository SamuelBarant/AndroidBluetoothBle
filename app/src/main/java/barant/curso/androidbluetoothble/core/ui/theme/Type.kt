package barant.curso.androidbluetoothble.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val GATTTypography = Typography(

    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        letterSpacing = 2.sp,
        color = TextWhite
    ),

    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = TextSecondary
    ),

    labelLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = TextWhite
    )
)
