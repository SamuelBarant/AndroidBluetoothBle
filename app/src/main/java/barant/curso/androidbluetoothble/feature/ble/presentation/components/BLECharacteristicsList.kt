package barant.curso.androidbluetoothble.feature.ble.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.MiscellaneousServices
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import barant.curso.androidbluetoothble.R
import barant.curso.androidbluetoothble.feature.ble.domain.BLEDevice

@Composable
fun CharacteristicsList(
    modifier: Modifier,
    item: BLEDevice
){
    LazyColumn (
        modifier = modifier
    ){
        item {
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(id = R.string.detail_section_detail_character)
            )
        }
        item{
            BLECharacteristicsCard(
                text = "${item.signal} dBm",
                icon = Icons.Default.SignalCellularAlt,
                label = stringResource(id = R.string.detail_section_detail_character1)
            )
            BLECharacteristicsCard(
                text = item.mac,
                icon = Icons.Default.Chat,
                label = stringResource(id = R.string.detail_section_detail_character2)
            )
            BLECharacteristicsCard(
                text = item.uuid,
                icon = Icons.Default.Fingerprint,
                label = stringResource(id = R.string.detail_section_detail_character3)
            )
            BLECharacteristicsCard(
                text = "${item.mtu} Bytes",
                icon = Icons.Default.MiscellaneousServices,
                label = stringResource(id = R.string.detail_section_detail_character4)
            )
        }
    }
}