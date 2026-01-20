package barant.curso.androidbluetoothble.core.di.dispatcher

import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatcherModule = module {
    single(named("IO")) { Dispatchers.IO }
    single(named("DEFAULT")) { Dispatchers.Default }
    single(named("MAIN")) { Dispatchers.Main }
    single(named("UNCONFINED")) { Dispatchers.Unconfined }
}