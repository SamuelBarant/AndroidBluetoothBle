package barant.curso.androidbluetoothble.feature.ble.presentation.detail

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.models.DeviceConnectionState
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleConnectGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleDisconnectGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleDiscoverGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleGetGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleReadGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleWriteGattUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class BleGattViewModel (
    private val discoverUseCase: BleDiscoverGattUseCase,
    private val connectUseCase: BleConnectGattUseCase,
    private val disconnectUseCase: BleDisconnectGattUseCase,
    private val readUseCase: BleReadGattUseCase,
    private val writeUseCase: BleWriteGattUseCase,
    private val getUseCase: BleGetGattUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(UiState(isLoading = false))
    val uiState: StateFlow<UiState> = _uiState

    fun connect(device: BLEDevice) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = connectUseCase(device)

            result.fold(
                onSuccess = { state ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = state
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error
                        )
                    }
                }
            )
        }
    }


    fun disconnect() {
        viewModelScope.launch {
            disconnectUseCase()
            _uiState.update { UiState() }
        }
    }


    fun discoverServices() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = discoverUseCase()

            result.fold(
                onSuccess = { services ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = it.data?.copy(services = services)
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error)
                    }
                }
            )
        }
    }

    fun readCharacteristic(characteristic: BluetoothGattCharacteristic) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = readUseCase(characteristic)

            result.fold(
                onSuccess = { data ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = it.data?.copy(messageReceived = data)
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error)
                    }
                }
            )
        }
    }

    fun writeCharacteristic(
        characteristic: BluetoothGattCharacteristic,
        data: ByteArray
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = writeUseCase(characteristic, data)

            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            data = it.data?.copy(messageSent = data)
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error)
                    }
                }
            )
        }
    }

//    fun getCharacteristic(
//        serviceUuid: UUID,
//        characteristicUuid: UUID
//    ) {
//        viewModelScope.launch {
//            _uiState.update { it.copy(isLoading = true, error = null) }
//
//            val result = getUseCase(serviceUuid, characteristicUuid)
//
//            result.fold(
//                onSuccess = { characteristic ->
//                    _uiState.update {
//                        it.copy(
//                            isLoading = false,
//                            data = it.data?.copy(
//                                // puedes guardarla donde quieras
//                            )
//                        )
//                    }
//                },
//                onFailure = { error ->
//                    _uiState.update {
//                        it.copy(isLoading = false, error = error)
//                    }
//                }
//            )
//        }
//    }

    data class UiState(
        var isLoading: Boolean = false,
        var data: DeviceConnectionState? = null,
        var error: Throwable? = null
    )
}