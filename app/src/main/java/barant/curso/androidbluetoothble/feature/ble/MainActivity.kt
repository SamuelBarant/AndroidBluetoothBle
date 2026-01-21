package barant.curso.androidbluetoothble.feature.ble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import barant.curso.androidbluetoothble.core.ui.navigation.AppNavigation
import barant.curso.androidbluetoothble.core.ui.theme.AndroidBluetoothBleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBluetoothBleTheme {
                AppNavigation()
            }
        }
    }
}