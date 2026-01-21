package barant.curso.androidbluetoothble.feature.ble.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BLECharacteristicsCard(
    text: String,
    icon: ImageVector,
    label: String
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = label
                )
            }
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}