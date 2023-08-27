package database

import app.cash.sqldelight.db.SqlDriver
import platform.PlatformConfiguration

expect class DbDriverFactory constructor(platformConfiguration: PlatformConfiguration) {
    fun createDriver(): SqlDriver
}