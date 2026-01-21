package barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt

import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository

class BleDisconnectGattUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke() {
        return repository.disconnect()
    }
}