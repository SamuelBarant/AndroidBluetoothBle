package barant.curso.androidbluetoothble.feature.ble.presentation.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.BluetoothConnected
import androidx.compose.material.icons.filled.BluetoothDisabled
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import barant.curso.androidbluetoothble.R
import barant.curso.androidbluetoothble.core.ui.components.AppTopBar
import barant.curso.androidbluetoothble.feature.ble.presentation.components.CharacteristicsList
import barant.curso.androidbluetoothble.feature.ble.domain.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.DeviceType

@Composable
fun DetailScreen() {
    val mock = BLEDevice(
        uuid = "0000181A-0000-1000-8000-00805F9B34FB",
        name = "Xiaomi Temp Sensor",
        mac = "A4:C1:38:7D:91:EF",
        signal = -55,
        type = DeviceType.SENSOR,
        mtu = 165,
        isConnected = false
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.detail_section_title),
                navigationIcon = Icons.Default.ArrowBackIosNew,
                onNavigationClick = {}
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("LIST") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.DeviceHub, contentDescription = null) },
                    label = { Text("PANEL") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
        ) {

            // 🔹 Contenido principal
            CharacteristicsList(
                item = mock,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            // 🔹 Acción inferior
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                onClick = {},
                border = if (mock.isConnected)
                    BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                else
                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary),

            ) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    imageVector = if (mock.isConnected)
                        Icons.Default.BluetoothDisabled
                    else
                        Icons.Default.BluetoothConnected,
                    contentDescription = null,
                    tint = if (mock.isConnected)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )
                Text(
                    text = stringResource(
                        id = if (mock.isConnected)
                            R.string.detail_section_detail_action2
                        else
                            R.string.detail_section_detail_action1
                    ),
                    color = if (mock.isConnected)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
