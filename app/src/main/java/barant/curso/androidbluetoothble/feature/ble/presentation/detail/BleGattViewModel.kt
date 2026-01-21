package barant.curso.androidbluetoothble.feature.ble.presentation.detail

import androidx.lifecycle.ViewModel
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleConnectGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleDisconnectGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleDiscoverGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleGetGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleReadGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleWriteGattUseCase

class BleGattViewModel (
    private val discoverUseCase: BleDiscoverGattUseCase,
    private val connectUseCase: BleConnectGattUseCase,
    private val disconnectUseCase: BleDisconnectGattUseCase,
    private val readUseCase: BleReadGattUseCase,
    private val writeUseCase: BleWriteGattUseCase,
    private val getUseCase: BleGetGattUseCase
): ViewModel(){

}