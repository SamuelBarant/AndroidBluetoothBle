package barant.curso.androidbluetoothble.feature.ble.domain.useCase.scanner

import android.util.Log
import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository

class BleStartScannerUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke(): Result<List<BLEDevice>> {
        Log.d("@BLEUseCase",repository.startScan().toString())
        return repository.startScan()
    }
}