package barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt

import android.bluetooth.BluetoothDevice
import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.models.DeviceConnectionState
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository

class BleConnectGattUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke(device: BLEDevice): Result<DeviceConnectionState>{
        return try {
            val deviceMac = repository.getBluetoothDevice(device.mac)
            val state = repository.connect(deviceMac)
            Result.success(state)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}