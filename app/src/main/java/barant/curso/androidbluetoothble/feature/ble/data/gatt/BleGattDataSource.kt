package barant.curso.androidbluetoothble.feature.ble.data.gatt

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import barant.curso.androidbluetoothble.feature.ble.domain.models.DeviceConnectionState
import java.util.UUID

class BleGattDataSource(
    private val context: Context
) {

    private var _state: DeviceConnectionState? = null
    val state: DeviceConnectionState? get() = _state

    private var gatt: BluetoothGatt? = null

    fun connect(
        device: BluetoothDevice,
        onStateChanged: (DeviceConnectionState) -> Unit
    ) {
        gatt = device.connectGatt(context, false, object : BluetoothGattCallback() {

            override fun onConnectionStateChange(
                gatt: BluetoothGatt,
                status: Int,
                newState: Int
            ) {
                _state = DeviceConnectionState(
                    gatt = gatt,
                    connectionState = newState,
                    services = gatt.services
                )
                onStateChanged(_state!!)
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                _state = _state?.copy(services = gatt.services)
                onStateChanged(_state!!)
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                status: Int
            ) {
                _state = _state?.copy(messageReceived = characteristic.value)
                onStateChanged(_state!!)
            }

            override fun onCharacteristicWrite(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic,
                status: Int
            ) {
                _state = _state?.copy(messageSent = characteristic.value)
                onStateChanged(_state!!)
            }

            override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
                _state = _state?.copy(mtu = mtu)
                onStateChanged(_state!!)
            }

            override fun onCharacteristicChanged(
                gatt: BluetoothGatt,
                characteristic: BluetoothGattCharacteristic
            ) {
                // Aquí puedes manejar notificaciones/indicaciones del periférico
                _state = _state?.copy(messageReceived = characteristic.value)
                onStateChanged(_state!!)
            }
        })
    }

    fun disconnect() {
        gatt?.disconnect()
        gatt?.close()
        gatt = null
        _state = null
    }

    fun requestMtu(mtu: Int) {
        gatt?.requestMtu(mtu)
    }

    fun discoverServices() {
        gatt?.discoverServices()
    }

    fun writeCharacteristic(characteristic: BluetoothGattCharacteristic, data: ByteArray) {
        characteristic.value = data
        gatt?.writeCharacteristic(characteristic)
    }

    fun readCharacteristic(characteristic: BluetoothGattCharacteristic) {
        gatt?.readCharacteristic(characteristic)
    }

    fun getCharacteristic(serviceUuid: UUID, characteristicUuid: UUID): BluetoothGattCharacteristic? {
        return gatt?.getService(serviceUuid)?.getCharacteristic(characteristicUuid)
    }
}
