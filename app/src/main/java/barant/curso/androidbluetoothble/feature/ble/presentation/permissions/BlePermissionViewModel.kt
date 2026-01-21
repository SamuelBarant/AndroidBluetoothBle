package barant.curso.androidbluetoothble.feature.ble.presentation.permissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.permissions.BlePermissionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BlePermissionViewModel(
    private val permissionUseCase: BlePermissionsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState(isLoading = true))
    val uiState: StateFlow<UiState> = _uiState

    fun checkPermissions() {
        viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)
            val result = permissionUseCase()
            result.fold(
                onSuccess = { granted ->
                    val missing = granted.filter { !it.value }.map { it.key }
                    if (missing.isEmpty()) {
                        _uiState.value = UiState(
                            isLoading = false,
                            error = ""
                        )
                    } else {
                        _uiState.value = UiState(
                            isLoading = false,
                            error = "Faltan permisos: ${missing.joinToString()}"
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.value = UiState(error = error.message ?: "Error desconocido")
                }
            )
        }
    }


    data class UiState(
        val isLoading: Boolean = false,
        val error: String = ""
    )
}