package barant.curso.androidbluetoothble.feature.ble.di

import barant.curso.androidbluetoothble.feature.ble.data.BleDataRepository
import barant.curso.androidbluetoothble.feature.ble.data.gatt.BleGattDataSource
import barant.curso.androidbluetoothble.feature.ble.data.permissions.BlePermissionDataSource
import barant.curso.androidbluetoothble.feature.ble.data.scanner.BleScannerDataSource
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.permissions.BlePermissionsUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.repository.BleRepository
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleConnectGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleDisconnectGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleDiscoverGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleGetGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleReadGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.gatt.BleWriteGattUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.scanner.BleStartScannerUseCase
import barant.curso.androidbluetoothble.feature.ble.domain.useCase.scanner.BleStopScannerUseCase
import barant.curso.androidbluetoothble.feature.ble.presentation.detail.BleGattViewModel
import barant.curso.androidbluetoothble.feature.ble.presentation.list.BleScannerViewModel
import barant.curso.androidbluetoothble.feature.ble.presentation.permissions.BlePermissionViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val bleModule = module {

    // Data
    singleOf(::BlePermissionDataSource)

    // BleScannerDataSource ahora no recibe lambda en el constructor
    factory { BleScannerDataSource(context = get(), dispatcher = get(named("IO"))) }
    factoryOf(::BleGattDataSource)

    // Repository
    singleOf(::BleDataRepository) bind BleRepository::class

    // UseCases
    factoryOf(::BlePermissionsUseCase)
    factoryOf(::BleStartScannerUseCase)
    factoryOf(::BleStopScannerUseCase)
    factoryOf(::BleGetGattUseCase)
    factoryOf(::BleDiscoverGattUseCase)
    factoryOf(::BleConnectGattUseCase)
    factoryOf(::BleDisconnectGattUseCase)
    factoryOf(::BleReadGattUseCase)
    factoryOf(::BleWriteGattUseCase)

    // ViewModels
    viewModelOf(::BlePermissionViewModel)
    viewModelOf(::BleScannerViewModel)
    viewModelOf(::BleGattViewModel)
}