package barant.curso.androidbluetoothble.feature.ble.di

import barant.curso.androidbluetoothble.feature.ble.data.BleDataRepository
import barant.curso.androidbluetoothble.feature.ble.data.permissions.BlePermissionDataSource
import barant.curso.androidbluetoothble.feature.ble.domain.permissions.BlePermissionsUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository
import barant.curso.androidbluetoothble.feature.ble.presentation.permissions.BlePermissionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val bleModule = module {
    // Data
    single { BlePermissionDataSource(get()) }
    single<BleRepository> { BleDataRepository(get()) }

    // UseCase
    factory { BlePermissionsUseCase(get()) }

    // ViewModel
    viewModel { BlePermissionViewModel(get()) }
}