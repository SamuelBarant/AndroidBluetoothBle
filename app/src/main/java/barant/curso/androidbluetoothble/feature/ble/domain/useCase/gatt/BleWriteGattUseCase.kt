package barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt

import android.bluetooth.BluetoothGattCharacteristic
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository

class BleWriteGattUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke(characteristic: BluetoothGattCharacteristic, data: ByteArray): Result<Boolean>{
        return try {
            val state = repository.writeCharacteristic(characteristic, data)
            Result.success(state)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}