package barant.curso.androidbluetoothble.feature.ble.presentation.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import barant.curso.androidbluetoothble.core.ui.components.ErrorBox
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PermissionScreen(){
    val viewModel: BlePermissionViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkPermissions()
    }

    when {
        uiState.isLoading -> {
            ErrorBox(errorLabel = "Cargando...")
        }
        uiState.error.isNotEmpty() -> {
            ErrorBox(errorLabel = uiState.error)
        }
        uiState.error.isEmpty() -> {
            ErrorBox(errorLabel = "Todo bien")
        }
    }
}