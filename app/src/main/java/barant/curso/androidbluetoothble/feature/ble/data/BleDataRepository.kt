package barant.curso.androidbluetoothble.feature.ble.data

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import androidx.annotation.RequiresPermission
import barant.curso.androidbluetoothble.feature.ble.data.gatt.BleGattDataSource
import barant.curso.androidbluetoothble.feature.ble.data.permissions.BlePermissionDataSource
import barant.curso.androidbluetoothble.feature.ble.data.scanner.BleScannerDataSource
import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.models.DeviceConnectionState
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeout
import java.util.UUID

class BleDataRepository(
    private val permissions: BlePermissionDataSource,
    private val scanner: BleScannerDataSource,
    private val gatt: BleGattDataSource
) : BleRepository {
    override suspend fun checkPermissions(): Result<Map<String, Boolean>> {
        return try {
            val status = permissions.hasPermissions()
            val missing = status.filter { !it.value }.map { it.key }

            if (missing.isEmpty()) {
                Result.success(status)
            } else {
                Result.failure(Exception("Faltan permisos: ${missing.joinToString()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override suspend fun startScan(): Result<List<BLEDevice>> {
        return try {
            scanner.startScan()

            withTimeout(16000) {
                scanner.scanning.first { !it }
            }

            val bleDevices: List<BLEDevice> = scanner.devices.value.map { it.toUi() }
            Log.d("@BLERepo", bleDevices.toString())
            Result.success(bleDevices)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    override suspend fun stopScan(): Result<Boolean> {
        return try {
            scanner.stopScan()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun connect(device: BluetoothDevice): DeviceConnectionState {
        return gatt.connect(device)
    }

    override suspend fun discoverServices() = gatt.discoverServices()

    override suspend fun readCharacteristic(characteristic: BluetoothGattCharacteristic) =
        gatt.readCharacteristic(characteristic)

    override suspend fun writeCharacteristic(
        characteristic: BluetoothGattCharacteristic,
        data: ByteArray
    ) =
        gatt.writeCharacteristic(characteristic, data)

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun disconnect() = gatt.disconnect()

    override fun getCharacteristic(serviceUuid: UUID, characteristicUuid: UUID) =
        gatt.getCharacteristic(serviceUuid, characteristicUuid)

    override fun getBluetoothDevice(mac: String): BluetoothDevice {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        return adapter.getRemoteDevice(mac)
    }
}