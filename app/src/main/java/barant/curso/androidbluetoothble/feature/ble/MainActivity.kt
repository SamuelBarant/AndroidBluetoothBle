package barant.curso.androidbluetoothble.feature.ble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import barant.curso.androidbluetoothble.core.ui.theme.AndroidBluetoothBleTheme
import barant.curso.androidbluetoothble.feature.ble.presentation.list.BLEListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBluetoothBleTheme {
                BLEListScreen()
            }
        }
    }
}