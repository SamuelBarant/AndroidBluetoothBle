package barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt

import android.bluetooth.BluetoothGattCharacteristic
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository
import java.util.UUID

class BleGetGattUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke(serviceUuid: UUID, characteristicUuid: UUID): Result<BluetoothGattCharacteristic?>{
        return try {
            val state = repository.getCharacteristic(serviceUuid, characteristicUuid)
            Result.success(state)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}