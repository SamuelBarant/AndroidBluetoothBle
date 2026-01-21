package barant.curso.androidbluetoothble.feature.ble.data

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import barant.curso.androidbluetoothble.feature.ble.domain.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.DeviceType

fun BluetoothDevice.toUi(signal: Int = 0, mtu: Int = 23): BLEDevice {
    val uuid = this.uuids?.firstOrNull()?.uuid?.toString() ?: "unknown"

    // Inferir tipo según UUID
    val type = when {
        uuid.contains("180D") -> DeviceType.HEALTH
        uuid.contains("180F") -> DeviceType.SENSOR
        uuid.contains("1812") -> DeviceType.SOUND
        uuid.contains("1813") -> DeviceType.SECURITY
        else -> DeviceType.OTHER
    }

    return BLEDevice(
        uuid = uuid,
        name = this.name ?: "Unknown",
        mac = this.address,
        signal = signal,
        type = type,
        mtu = mtu,
        isConnected = false
    )
}
