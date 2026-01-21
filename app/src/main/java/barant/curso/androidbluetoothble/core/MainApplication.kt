package barant.curso.androidbluetoothble.core

import android.app.Application
import barant.curso.androidbluetoothble.core.di.coreModule
import barant.curso.androidbluetoothble.feature.ble.di.bleModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)

            modules(coreModule, bleModule)
        }
    }
}