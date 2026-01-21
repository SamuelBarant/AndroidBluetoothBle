package barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt

import android.bluetooth.BluetoothGattService
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository

class BleDiscoverGattUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke(): Result<List<BluetoothGattService>> {
        return try {
            val state = repository.discoverServices()
            Result.success(state)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}