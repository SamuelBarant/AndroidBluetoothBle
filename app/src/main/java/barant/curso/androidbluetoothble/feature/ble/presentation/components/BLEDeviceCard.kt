package barant.curso.androidbluetoothble.feature.ble.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.models.DeviceType

@Composable
fun BLEDeviceCard(
    device: BLEDevice,
    modifier: Modifier = Modifier,
    onClick: (BLEDevice) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(0.5.dp, Color.White, MaterialTheme.shapes.small)
            .clickable {onClick(device)},
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono del tipo de dispositivo
            Icon(
                imageVector = when (device.type) {
                    DeviceType.SOUND -> Icons.Default.Speaker
                    DeviceType.SECURITY -> Icons.Default.Security
                    DeviceType.HEALTH -> Icons.Default.HealthAndSafety
                    DeviceType.SENSOR -> Icons.Default.Sensors
                    DeviceType.OTHER -> Icons.Default.DevicesOther
                },
                contentDescription = device.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Columna de nombre y MAC
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = device.mac,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Señal
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = when (device.signal) {
                        in -40..0 -> Icons.Default.SignalCellularAlt2Bar
                        in -60..-41 -> Icons.Default.SignalCellularAlt1Bar
                        in -80..-61 -> Icons.Default.SignalCellularAlt
                        else -> Icons.Default.SignalCellularOff
                    },
                    contentDescription = "${device.signal} dBm"
                )
                Text(
                    text = "${device.signal} dBm",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
