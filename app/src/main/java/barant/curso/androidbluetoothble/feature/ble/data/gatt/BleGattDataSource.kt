package barant.curso.androidbluetoothble.feature.ble.data.gatt

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import androidx.annotation.RequiresPermission
import barant.curso.androidbluetoothble.feature.ble.domain.models.DeviceConnectionState
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.UUID
import kotlin.coroutines.resume

class BleGattDataSource(
    private val context: Context){

    private var gatt: BluetoothGatt? = null

    /** Conecta al dispositivo y espera hasta que se conecte o falle */
    @SuppressLint("MissingPermission")
    suspend fun connect(device: BluetoothDevice): DeviceConnectionState =
        suspendCancellableCoroutine { cont ->

            gatt = device.connectGatt(context, false, object : BluetoothGattCallback() {

                override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        val state = DeviceConnectionState(
                            gatt = gatt,
                            connectionState = newState,
                            services = gatt.services
                        )
                        cont.resume(state)
                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        cont.resume(DeviceConnectionState(gatt, newState))
                    }
                }

                override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                    // opcional: se puede usar si quieres suspender discoverServices
                }

                override fun onCharacteristicRead(
                    gatt: BluetoothGatt,
                    characteristic: BluetoothGattCharacteristic,
                    status: Int
                ) {
                    // opcional: suspender read
                }

                override fun onCharacteristicWrite(
                    gatt: BluetoothGatt,
                    characteristic: BluetoothGattCharacteristic,
                    status: Int
                ) {
                    // opcional: suspender write
                }
            })

            cont.invokeOnCancellation {
                gatt?.disconnect()
                gatt?.close()
            }
        }

    /** Descubre servicios de forma suspend */
    @SuppressLint("MissingPermission")
    suspend fun discoverServices(): List<BluetoothGattService> =
        suspendCancellableCoroutine { cont ->
            gatt?.let { g ->
                g.discoverServices()
                val callback = object : BluetoothGattCallback() {
                    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                        cont.resume(gatt.services)
                    }
                }
            } ?: cont.resume(emptyList())
        }

    /** Leer característica de forma suspend */
    @SuppressLint("MissingPermission")
    suspend fun readCharacteristic(characteristic: BluetoothGattCharacteristic): ByteArray =
        suspendCancellableCoroutine { cont ->
            gatt?.let { g ->
                g.readCharacteristic(characteristic)
                val callback = object : BluetoothGattCallback() {
                    override fun onCharacteristicRead(
                        gatt: BluetoothGatt,
                        characteristic: BluetoothGattCharacteristic,
                        status: Int
                    ) {
                        cont.resume(characteristic.value)
                    }
                }
            } ?: cont.resume(ByteArray(0))
        }

    /** Escribir característica de forma suspend */
    @SuppressLint("MissingPermission")
    suspend fun writeCharacteristic(characteristic: BluetoothGattCharacteristic, data: ByteArray): Boolean =
        suspendCancellableCoroutine { cont ->
            gatt?.let { g ->
                characteristic.value = data
                g.writeCharacteristic(characteristic)
                val callback = object : BluetoothGattCallback() {
                    override fun onCharacteristicWrite(
                        gatt: BluetoothGatt,
                        characteristic: BluetoothGattCharacteristic,
                        status: Int
                    ) {
                        cont.resume(status == BluetoothGatt.GATT_SUCCESS)
                    }
                }
            } ?: cont.resume(false)
        }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun disconnect() {
        gatt?.disconnect()
        gatt?.close()
        gatt = null
    }

    fun getCharacteristic(serviceUuid: UUID, characteristicUuid: UUID): BluetoothGattCharacteristic? {
        return gatt?.getService(serviceUuid)?.getCharacteristic(characteristicUuid)
    }


}
