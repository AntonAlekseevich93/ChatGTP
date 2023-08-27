package database

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import repository.Repository

internal val databaseModule = DI.Module("databaseModule") {
    bind<DbDriverFactory>() with singleton {
        DbDriverFactory(instance())
    }

    bind<SqlDelightDataSource>() with provider {
        SqlDelightDataSource(instance())
    }

    bind<Repository>() with singleton {
        Repository(instance())
    }
}