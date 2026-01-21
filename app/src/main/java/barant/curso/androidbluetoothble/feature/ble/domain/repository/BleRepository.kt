package barant.curso.androidbluetoothble.feature.ble.domain.repository

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.models.DeviceConnectionState
import java.util.UUID

interface BleRepository {
    suspend fun checkPermissions(): Result<Map<String, Boolean>>
    suspend fun startScan(): Result<List<BLEDevice>>
    suspend fun stopScan(): Result<Boolean>
    suspend fun connect(device: BluetoothDevice): DeviceConnectionState
    suspend fun discoverServices(): List<BluetoothGattService>
    suspend fun readCharacteristic(characteristic: BluetoothGattCharacteristic): ByteArray
    suspend fun writeCharacteristic(characteristic: BluetoothGattCharacteristic, data: ByteArray): Boolean
    fun disconnect()
    fun getCharacteristic(serviceUuid: UUID, characteristicUuid: UUID): BluetoothGattCharacteristic?
    fun getBluetoothDevice(mac: String): BluetoothDevice

}