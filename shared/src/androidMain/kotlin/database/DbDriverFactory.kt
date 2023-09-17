package database


import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import platform.PlatformConfiguration
import constants.DB_NAME_WITH_VERSION
import sqldelight.com.chat_gpt.database.AppDatabase

actual class DbDriverFactory actual constructor(private val platformConfiguration: PlatformConfiguration) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            AppDatabase.Schema,
            platformConfiguration.androidContext,
            DB_NAME_WITH_VERSION
        )
    }
}