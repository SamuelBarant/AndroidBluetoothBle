package barant.curso.androidbluetoothble.feature.ble.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothAudio
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import barant.curso.androidbluetoothble.R
import barant.curso.androidbluetoothble.core.ui.components.AppTopBar
import barant.curso.androidbluetoothble.core.ui.components.ErrorBox
import barant.curso.androidbluetoothble.core.ui.components.GenericList
import barant.curso.androidbluetoothble.feature.ble.presentation.components.BLEDeviceCard
import barant.curso.androidbluetoothble.feature.ble.domain.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.DeviceType
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BLEListScreen() {

    val viewModel: BleScannerViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startScan()
    }

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
        when {
            uiState.isLoading -> {
                ErrorBox(errorLabel = "Cargando ...")
            }
            uiState.error != null -> {
                ErrorBox(errorLabel = uiState.error!!.message ?: "Error desconocido")
            }
            uiState.data.isNotEmpty() -> {
                GenericList(
                    modifier = Modifier.padding(paddingValues),
                    items = uiState.data,
                    title = "Dispositivos BLE"
                ) { device ->
                    BLEDeviceCard(device)
                }
            }
            else -> {
                ErrorBox(errorLabel = "No se encontraron dispositivos")
            }
        }
    }
}
