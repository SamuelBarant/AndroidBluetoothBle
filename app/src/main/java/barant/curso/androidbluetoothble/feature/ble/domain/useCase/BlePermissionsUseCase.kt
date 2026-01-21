package barant.curso.androidbluetoothble.feature.ble.domain.useCase

import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository
import java.lang.Exception

class BlePermissionsUseCase(
    private val repository: BleRepository
) {
    suspend operator fun invoke(): Result<Map<String, Boolean>>{
        return try {
            repository.checkPermissions()
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}