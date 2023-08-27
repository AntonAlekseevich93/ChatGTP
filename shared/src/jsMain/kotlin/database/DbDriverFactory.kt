package database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.w3c.dom.Worker
import platform.PlatformConfiguration
import sqldelight.com.chat_gpt.database.AppDatabase

actual class DbDriverFactory actual constructor(val platformConfiguration: PlatformConfiguration) {
    actual fun createDriver(): SqlDriver {
         return WebWorkerDriver(
            Worker(
                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
            )
        ).also { AppDatabase.Companion.Schema.create(it) }
    }
}