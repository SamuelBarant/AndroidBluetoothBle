package barant.curso.androidbluetoothble.feature.ble.domain.repository

import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice

interface BleRepository {
    suspend fun checkPermissions(): Result<Map<String, Boolean>>
    suspend fun startScan(): Result<List<BLEDevice>>
    suspend fun stopScan(): Result<Boolean>
}