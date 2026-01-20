package barant.curso.androidbluetoothble.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun ErrorBox(
    errorLabel: String
){
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorLabel,
            style = MaterialTheme.typography.titleLarge
        )
    }
}