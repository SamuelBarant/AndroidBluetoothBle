package barant.curso.androidbluetoothble.feature.ble.data.gatt

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothProfile
import android.content.Context
import androidx.annotation.RequiresPermission
import barant.curso.androidbluetoothble.feature.ble.domain.models.DeviceConnectionState
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.UUID
import kotlin.coroutines.resume

class BleGattDataSource(
    private val context: Context
) {

    private var gatt: BluetoothGatt? = null
    private var writeContinuation: ((Boolean) -> Unit)? = null
    private var readContinuation: ((ByteArray) -> Unit)? = null
    private var discoverContinuation: ((List<BluetoothGattService>) -> Unit)? = null

    @SuppressLint("MissingPermission")
    suspend fun connect(device: BluetoothDevice): DeviceConnectionState =
        suspendCancellableCoroutine { cont ->
            gatt = device.connectGatt(context, false, object : BluetoothGattCallback() {

                override fun onConnectionStateChange(
                    gatt: BluetoothGatt,
                    status: Int,
                    newState: Int
                ) {
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        // Descubrir servicios automáticamente después de conectar
                        gatt.discoverServices()
                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        cont.resume(DeviceConnectionState(gatt, newState))
                    }
                }

                override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        val state = DeviceConnectionState(
                            gatt = gatt,
                            connectionState = BluetoothProfile.STATE_CONNECTED,
                            services = gatt.services
                        )
                        cont.resume(state)
                        discoverContinuation?.invoke(gatt.services)
                        discoverContinuation = null
                    }
                }

                override fun onCharacteristicRead(
                    gatt: BluetoothGatt,
                    characteristic: BluetoothGattCharacteristic,
                    status: Int
                ) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        readContinuation?.invoke(characteristic.value ?: ByteArray(0))
                        readContinuation = null
                    }
                }

                override fun onCharacteristicWrite(
                    gatt: BluetoothGatt,
                    characteristic: BluetoothGattCharacteristic,
                    status: Int
                ) {
                    writeContinuation?.invoke(status == BluetoothGatt.GATT_SUCCESS)
                    writeContinuation = null
                }

            })

            cont.invokeOnCancellation {
                gatt?.disconnect()
                gatt?.close()
            }
        }

    @SuppressLint("MissingPermission")
    suspend fun writeCharacteristic(
        characteristic: BluetoothGattCharacteristic,
        data: ByteArray
    ): Boolean =
        suspendCancellableCoroutine { cont ->
            gatt?.let { g ->
                writeContinuation = { success -> cont.resume(success) }
                characteristic.value = data
                val started = g.writeCharacteristic(characteristic)
                if (!started) {
                    writeContinuation = null
                    cont.resume(false)
                }
            } ?: cont.resume(false)
        }

    @SuppressLint("MissingPermission")
    suspend fun discoverServices(): List<BluetoothGattService> =
        suspendCancellableCoroutine { cont ->
            gatt?.let { g ->
                discoverContinuation = { services -> cont.resume(services) }
                val started = g.discoverServices()
                if (!started) {
                    discoverContinuation = null
                    cont.resume(emptyList())
                }
            } ?: cont.resume(emptyList())
        }

    @SuppressLint("MissingPermission")
    suspend fun readCharacteristic(characteristic: BluetoothGattCharacteristic): ByteArray =
        suspendCancellableCoroutine { cont ->
            gatt?.let { g ->
                readContinuation = { data -> cont.resume(data) }
                val started = g.readCharacteristic(characteristic)
                if (!started) {
                    readContinuation = null
                    cont.resume(ByteArray(0))
                }
            } ?: cont.resume(ByteArray(0))
        }

    @RequiresPermission(android.Manifest.permission.BLUETOOTH_CONNECT)
    fun disconnect() {
        gatt?.disconnect()
        gatt?.close()
        gatt = null
    }

    fun getCharacteristic(
        serviceUuid: UUID,
        characteristicUuid: UUID
    ): BluetoothGattCharacteristic? {
        return gatt?.getService(serviceUuid)?.getCharacteristic(characteristicUuid)
    }
}