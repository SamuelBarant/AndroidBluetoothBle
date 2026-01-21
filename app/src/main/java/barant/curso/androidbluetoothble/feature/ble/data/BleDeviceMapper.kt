package barant.curso.androidbluetoothble.feature.ble.data

import android.Manifest
import android.bluetooth.BluetoothDevice
import androidx.annotation.RequiresPermission
import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.domain.models.DeviceType

@RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
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
