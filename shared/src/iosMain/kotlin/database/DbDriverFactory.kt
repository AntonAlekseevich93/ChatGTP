package database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import constants.DB_NAME_WITH_VERSION

import platform.PlatformConfiguration
import sqldelight.com.chat_gpt.database.AppDatabase

actual class DbDriverFactory actual constructor(private val platformConfiguration: PlatformConfiguration) {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema, DB_NAME_WITH_VERSION)
    }
}