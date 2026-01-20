package barant.curso.androidbluetoothble.feature.ble.domain.repository

interface BleRepository {
    suspend fun checkPermissions(): Result<Map<String, Boolean>>
}