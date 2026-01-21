package barant.curso.androidbluetoothble.feature.ble.domain.useCase

import barant.curso.androidbluetoothble.feature.ble.domain.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository

class BleStartScannerUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke(): Result<List<BLEDevice>> {
        return repository.startScan()
    }
}