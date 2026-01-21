package barant.curso.androidbluetoothble.feature.ble.data.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

class BlePermissionDataSource(
    private val context: Context
) {
    fun hasPermissions(): Map<String, Boolean> {
        val permissionsList = mutableMapOf<String, Boolean>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionsList[Manifest.permission.BLUETOOTH_SCAN] =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_SCAN
                ) == PackageManager.PERMISSION_GRANTED
            permissionsList[Manifest.permission.BLUETOOTH_CONNECT] =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
        } else {
            permissionsList[Manifest.permission.BLUETOOTH] =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH
                ) == PackageManager.PERMISSION_GRANTED
            permissionsList[Manifest.permission.BLUETOOTH_ADMIN] =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_ADMIN
                ) == PackageManager.PERMISSION_GRANTED
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            permissionsList[Manifest.permission.ACCESS_FINE_LOCATION] =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            permissionsList[Manifest.permission.ACCESS_COARSE_LOCATION] =
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        }

        return permissionsList
    }
}