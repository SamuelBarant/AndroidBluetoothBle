package barant.curso.androidbluetoothble.core.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val selected: Boolean = false,
    val onClick: () -> Unit
)

@Composable
fun NavBottomBar(
    items: List<NavItem>
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.selected,
                onClick = item.onClick,
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
