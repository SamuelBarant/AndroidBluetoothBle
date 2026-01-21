package barant.curso.androidbluetoothble.feature.ble.data.scanner

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BleScannerDataSource (
    context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){
    private var onConnect: ((BluetoothDevice) -> Unit)? = null

    val adapter = checkNotNull(context.getSystemService(BluetoothManager::class.java).adapter)
    private var _scanning = MutableStateFlow(false)
    val scanning: StateFlow<Boolean> = _scanning

    private val _devices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    val devices: StateFlow<List<BluetoothDevice>> = _devices

    val scanSettings: ScanSettings = ScanSettings.Builder()
        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
        .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
        .build()

    private val scanCallBack = object : ScanCallback(){
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device

            if (_devices.value.none { it.address == device.address }){
                _devices.value = _devices.value + device
            }
        }

        override fun onScanFailed(errorCode: Int) {
            _scanning.value = false
            Log.w("BleScannerDataSource", "Scan failed: $errorCode")
        }
    }

    fun startScan(){
        if (_scanning.value) return
        if (!adapter.isEnabled) return

        _scanning.value = true
        _devices.value = emptyList()

        adapter.bluetoothLeScanner.startScan(null, scanSettings, scanCallBack)

        val scanScope = CoroutineScope(dispatcher + SupervisorJob())

        scanScope.launch {
            delay(15000)
            stopScan()
        }
    }

    fun stopScan(){
        if (!_scanning.value) return
        _scanning.value = false
        adapter.bluetoothLeScanner.stopScan(scanCallBack)
    }

    fun onConnectCallback(callback: (BluetoothDevice) -> Unit) {
        onConnect = callback
    }

    fun handleDeviceConnected(device: BluetoothDevice) {
        onConnect?.invoke(device)
    }
}