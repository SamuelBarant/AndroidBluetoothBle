package barant.curso.androidbluetoothble.feature.ble.presentation.list

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barant.curso.androidbluetoothble.feature.ble.domain.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.BleStartScannerUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.BleStopScannerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BleScannerViewModel(
    private val startUseCase: BleStartScannerUseCase,
    private val stopUseCase: BleStopScannerUseCase
) : ViewModel(){
    private val _uiState = MutableStateFlow(UiState(isLoading = false))
    val uiState: StateFlow<UiState> = _uiState

    private var scanningJob = viewModelScope.launch {} // placeholder para cancelación

    fun startScan() {
        scanningJob.cancel() // cancelar cualquier escaneo previo
        scanningJob = viewModelScope.launch {
            _uiState.value = UiState(isLoading = true)

            try {
                val result = startUseCase()

                result.fold(
                    onSuccess = {
                        UiState(
                            isLoading = false,
                            data = it
                        )
                    },
                    onFailure = {
                        UiState(
                            isLoading = false,
                            error = it
                        )
                    }
                )
            } catch (e: Exception) {
                _uiState.value = UiState(
                    isLoading = false,
                    error = e
                )
            }
        }
    }

    fun stopScan() = viewModelScope.launch {
        stopUseCase()
        _uiState.value = _uiState.value.copy(isLoading = false)
    }

    data class UiState(
        val isLoading: Boolean = false,
        val data: List<BLEDevice> = emptyList(),
        val error: Throwable? = null
    )
}