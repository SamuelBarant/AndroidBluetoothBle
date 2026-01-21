package barant.curso.androidbluetoothble.feature.ble.di

import android.bluetooth.BluetoothDevice
import barant.curso.androidbluetoothble.feature.ble.data.BleDataRepository
import barant.curso.androidbluetoothble.feature.ble.data.permissions.BlePermissionDataSource
import barant.curso.androidbluetoothble.feature.ble.data.scanner.BleScannerDataSource
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.BlePermissionsUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.BleStartScannerUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.BleStopScannerUseCase
import barant.curso.androidbluetoothble.feature.ble.presentation.list.BleScannerViewModel
import barant.curso.androidbluetoothble.feature.ble.presentation.permissions.BlePermissionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val bleModule = module {

    // Data
    single { BlePermissionDataSource(get()) }

    // BleScannerDataSource ahora no recibe lambda en el constructor
    factory { BleScannerDataSource(get()) }

    // Repository
    single<BleRepository> { BleDataRepository(get(), get()) }

    // UseCases
    factory { BlePermissionsUseCase(get()) }
    factory { BleStartScannerUseCase(get()) }
    factory { BleStopScannerUseCase(get()) }

    // ViewModels
    viewModel { BlePermissionViewModel(get()) }
    viewModel { BleScannerViewModel(get(), get()) }
}