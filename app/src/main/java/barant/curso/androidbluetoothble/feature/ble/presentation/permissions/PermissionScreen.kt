package barant.curso.androidbluetoothble.feature.ble.presentation.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import barant.curso.androidbluetoothble.core.ui.components.ErrorBox
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PermissionScreen(
    onPermissionsGranted: () -> Unit
) {
    val viewModel: BlePermissionViewModel = koinViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.checkPermissions()
    }


    if (uiState.isLoading) {
        ErrorBox(errorLabel = "Cargando..")
    } else if (uiState.error.isNotEmpty()) {
        ErrorBox(errorLabel = uiState.error)
    } else {
        LaunchedEffect(Unit) {
            onPermissionsGranted()
        }
    }

}