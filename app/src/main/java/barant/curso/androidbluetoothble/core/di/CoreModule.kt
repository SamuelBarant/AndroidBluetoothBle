package barant.curso.androidbluetoothble.core.di

import barant.curso.androidbluetoothble.core.di.dispatcher.dispatcherModule
import org.koin.dsl.module

val coreModule = module {
    includes(dispatcherModule)
}