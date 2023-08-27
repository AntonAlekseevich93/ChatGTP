package database

import composables.messages.MessageType
import data.MessageVo
import entity.MessageDto
import sqldelight.com.chat_gpt.database.AppDatabase

const val nameDb = "gpt.db"

class SqlDelightDataSource(dbDriverFactory: DbDriverFactory) {
    private val driver = dbDriverFactory.createDriver()
    private val database = AppDatabase(driver)
    private val dbQuery = database.appDatabaseQueries

    suspend fun insertMessage(messageDbDto: MessageDto) {
        dbQuery.insertMessage(
            message = messageDbDto.message,
            isUser = messageDbDto.messageType
        )
    }

    suspend fun getAllMessages() = dbQuery.selectAllMessagesInfo().executeAsList().map {
        MessageVo(
            content = it.message,
            messageType = if (it.isUser == 0L) MessageType.USER else MessageType.GPT
        )
    }

    suspend fun saveAuthorizationKey(key: String, modelGpt: String) {
        dbQuery.insertKey(key = key, modelGpt = modelGpt)
    }

    suspend fun getLastAuthorizationKeyFromDb() = dbQuery.selectAllKeysInfo().executeAsList()

    suspend fun deleteApiKeys() {
        dbQuery.removeAllKeys()
    }

    suspend fun saveSelectedTheme(themeId: Long) {
        dbQuery.insertThemeId(themeId)
    }

    suspend fun getSelectedThemeIdOrDefault(): Long? =
        dbQuery.selectAllSettings().executeAsList().takeIf { it.isNotEmpty() }?.first()

    companion object {
        const val USER_TYPE = 0L
        const val GPT_TYPE = 1L
        const val SYSTEM_TYPE = 2L
    }
}

