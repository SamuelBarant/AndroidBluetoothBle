package barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt

import android.bluetooth.BluetoothGattCharacteristic
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository

class BleReadGattUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke(characteristic: BluetoothGattCharacteristic): Result<ByteArray>{
        return try {
            val state = repository.readCharacteristic(characteristic)
            Result.success(state)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}