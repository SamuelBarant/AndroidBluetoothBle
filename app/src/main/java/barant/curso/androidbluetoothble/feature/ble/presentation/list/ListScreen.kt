package barant.curso.androidbluetoothble.feature.ble.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothAudio
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import barant.curso.androidbluetoothble.R
import barant.curso.androidbluetoothble.core.ui.components.AppTopBar
import barant.curso.androidbluetoothble.core.ui.components.GenericList
import barant.curso.androidbluetoothble.feature.ble.presentation.components.BLEDeviceCard
import barant.curso.androidbluetoothble.feature.ble.presentation.model.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.presentation.model.DeviceType



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BLEListScreen() {

    val mockDevices = listOf(
        BLEDevice("0000180D-0000-1000-8000-00805F9B34FB", "Heart Rate Monitor", "A4:C1:38:9B:2F:01", -42, DeviceType.HEALTH, mtu = 23),
        BLEDevice("0000181A-0000-1000-8000-00805F9B34FB", "Enviro Sensor", "B8:27:EB:45:12:9C", -58, DeviceType.SENSOR, mtu = 23),
        BLEDevice("00001810-0000-1000-8000-00805F9B34FB", "Smart Lock", "C0:98:E5:00:AB:21", -71, DeviceType.SECURITY, mtu = 23),
        BLEDevice("0000110B-0000-1000-8000-00805F9B34FB", "Bluetooth Speaker", "D4:36:39:88:73:FA", -35, DeviceType.SOUND, mtu = 23),
        BLEDevice("F000AA65-0451-4000-B000-000000000000", "Custom BLE Device", "E1:9A:4C:77:90:3D", -89, DeviceType.OTHER, mtu = 23)
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.list_section_title),
                icon = Icons.Default.BluetoothAudio
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("LIST") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = { Icon(Icons.Default.DeviceHub, contentDescription = null) },
                    label = { Text("PANEL") }
                )
            }
        }
    ) { paddingValues ->
        val isLoadingMock = true
        GenericList(
            modifier = Modifier.padding(paddingValues),
            items = mockDevices,
            title = "Dispositivos BLE",
        ) { device ->
            BLEDeviceCard(device)
        }
    }
}
