package barant.curso.androidbluetoothble.feature.ble.presentation.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import barant.curso.androidbluetoothble.R
import barant.curso.androidbluetoothble.core.ui.components.AppTopBar
import barant.curso.androidbluetoothble.feature.ble.presentation.components.CharacteristicsList
import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BLEDetailScreen(
    device: BLEDevice?,
    onBack: () -> Unit
) {
    val viewModel: BleGattViewModel = koinViewModel()
    val scope = rememberCoroutineScope()
    var ledOn by remember { mutableStateOf(false) }

    LaunchedEffect(device) {
        device?.let {
            viewModel.connect(device)
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(id = R.string.detail_section_title),
                navigationIcon = Icons.Default.ArrowBackIosNew,
                onNavigationClick = {
                    viewModel.disconnect()
                    onBack()
                },
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        viewModel.disconnect()
                        onBack()
                    },
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

            if (uiState.isLoading) {
                Text("Conectando al dispositivo...")
            } else if (uiState.error != null) {
                Text("Error: ${uiState.error!!.message}")
            } else {
                val connected = uiState.data != null
                val services = uiState.data?.services ?: emptyList()
                Text(
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    text = if (connected) "Conectado" else "Desconectado"
                )

                CharacteristicsList(
                    item = device?.copy(isConnected = connected) ?: return@Column,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )

                val ledCharacteristic = uiState.data?.services
                    ?.find { it.uuid.toString() == "12345678-1234-1234-1234-1234567890ab" }
                    ?.characteristics
                    ?.find { it.uuid.toString() == "87654321-4321-4321-4321-ba0987654321" }

                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(vertical = 14.dp)
                            .align(Alignment.Center),
                        onClick = {
                            ledCharacteristic?.let { characteristic ->
                                scope.launch {
                                    val value = if (ledOn) 0.toByte() else 1.toByte()
                                    val success = viewModel.writeCharacteristic(characteristic, byteArrayOf(value))
                                    if (success) {
                                        ledOn = !ledOn
                                    }
                                }
                            }
                        },
                        border = if (ledOn)
                            BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                        else
                            BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = if (ledOn) "OFF" else "ON",
                            color = if (ledOn)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.primary
                        )
                    }
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    onClick = {
                        if (connected) viewModel.disconnect()
                        else device?.let { viewModel.connect(it) }
                    },
                    border = if (connected)
                        BorderStroke(1.dp, MaterialTheme.colorScheme.error)
                    else
                        BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        imageVector = if (connected)
                            Icons.Default.BluetoothDisabled
                        else
                            Icons.Default.BluetoothConnected,
                        contentDescription = null,
                        tint = if (connected)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = if (connected)
                            stringResource(id = R.string.detail_section_detail_action2)
                        else
                            stringResource(id = R.string.detail_section_detail_action1),
                        color = if (connected)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}