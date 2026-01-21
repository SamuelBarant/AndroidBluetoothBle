package barant.curso.androidbluetoothble.feature.ble.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class DeviceType {
    SOUND, SECURITY, HEALTH, SENSOR, OTHER
}

@Parcelize
data class BLEDevice(
    val uuid: String,
    val name: String,
    val mac: String,
    val signal: Int,
    val type: DeviceType,
    val mtu: Int,
    val isConnected: Boolean = false
): Parcelable
