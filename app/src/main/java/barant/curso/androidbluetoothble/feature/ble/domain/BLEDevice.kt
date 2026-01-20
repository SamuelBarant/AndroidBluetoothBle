package barant.curso.androidbluetoothble.feature.ble.domain

enum class DeviceType {
    SOUND, SECURITY, HEALTH, SENSOR, OTHER
}

data class BLEDevice(
    val uuid: String,
    val name: String,
    val mac: String,
    val signal: Int,
    val type: DeviceType,
    val mtu: Int,
    val isConnected: Boolean = false
)
