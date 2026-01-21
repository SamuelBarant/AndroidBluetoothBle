package barant.curso.androidbluetoothble.feature.ble.domain.useCase.scanner

import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository

class BleStopScannerUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return repository.stopScan()
    }
}