package di

import database.databaseModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.singleton
import platform.PlatformConfiguration

object PlatformSDK {
    fun init(
        configuration: PlatformConfiguration
    ) {
        val mainModule = DI.Module(
            name = "mainModule",
            init = {
                bind<PlatformConfiguration>() with singleton { configuration }
            }
        )

        Inject.createDependencies(
            DI {
                importAll(
                    mainModule,
                    databaseModule
                )
            }.direct
        )
    }
}