package barant.curso.androidbluetoothble.feature.ble.data

import barant.curso.androidbluetoothble.feature.ble.data.permissions.BlePermissionDataSource
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository
import java.lang.Exception

class BleDataRepository (
    private val permissions: BlePermissionDataSource
): BleRepository{
    override suspend fun checkPermissions(): Result<Map<String, Boolean>> {
        return try {
            val status = permissions.hasPermissions()
            val missing = status.filter { !it.value }.map { it.key }

            if (missing.isEmpty()) {
                Result.success(status)
            } else {
                Result.failure(Exception("Faltan permisos: ${missing.joinToString()}"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}