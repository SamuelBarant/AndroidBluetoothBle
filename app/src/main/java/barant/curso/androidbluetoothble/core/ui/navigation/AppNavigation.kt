package barant.curso.androidbluetoothble.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import barant.curso.androidbluetoothble.feature.ble.presentation.list.BLEListScreen
import barant.curso.androidbluetoothble.feature.ble.presentation.permissions.PermissionScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Permissions.route
    ){
        composable(Routes.Permissions.route) {
            PermissionScreen(
                onPermissionsGranted = {
                    navController.navigate(Routes.List.route){
                        popUpTo(Routes.Permissions.route) { inclusive = true}
                    }
                }
            )
        }

        composable(Routes.List.route) {
            BLEListScreen()
        }
    }
}