package barant.curso.androidbluetoothble.feature.ble.domain.models

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattService

data class DeviceConnectionState(
    val gatt: BluetoothGatt,
    val connectionState: Int,
    val services: List<BluetoothGattService> = emptyList(),
    val mtu: Int? = null,
    val messageSent: ByteArray? = null,
    val messageReceived: ByteArray? = null
)