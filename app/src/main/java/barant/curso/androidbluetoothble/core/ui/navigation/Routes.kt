package barant.curso.androidbluetoothble.core.ui.navigation

sealed class Routes(val route: String) {
    object Permissions: Routes("permissions")
    object List: Routes("list")
    object Panel: Routes("panel/{deviceUuid}")
}