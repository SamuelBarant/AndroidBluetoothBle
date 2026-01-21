package barant.curso.androidbluetoothble.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import barant.curso.androidbluetoothble.feature.ble.domain.models.BLEDevice
import barant.curso.androidbluetoothble.feature.ble.presentation.detail.BLEDetailScreen
import barant.curso.androidbluetoothble.feature.ble.presentation.list.BLEListScreen
import barant.curso.androidbluetoothble.feature.ble.presentation.permissions.PermissionScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Permissions.route
    ) {
        composable(Routes.Permissions.route) {
            PermissionScreen(
                onPermissionsGranted = {
                    navController.navigate(Routes.List.route) {
                        popUpTo(Routes.Permissions.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.List.route) {
            BLEListScreen { device ->
                // Guardamos el BLEDevice en el backStackEntry actual
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("device", device)
                navController.navigate(Routes.Panel.route)
            }
        }

        composable(Routes.Panel.route) {
            // Obtenemos el BLEDevice desde el backStackEntry previo
            val device = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<BLEDevice>("device")

            BLEDetailScreen(device = device){
                navController.navigate(Routes.List.route)
            }
        }
    }
}
