package database

import com.chatgpt.database.Messages
import entity.MessageDto
import sqldelight.com.chat_gpt.database.AppDatabase

class SqlDelightDataSource(dbDriverFactory: DbDriverFactory) {
    private val driver = dbDriverFactory.createDriver()
    private val database = AppDatabase(driver)
    private val dbQuery = database.appDatabaseQueries

    suspend fun insertMessage(messageDbDto: MessageDto) {
        dbQuery.insertMessage(
            message = messageDbDto.message,
            messageId = messageDbDto.messageId,
            isUser = messageDbDto.messageType,
            parentMessageId = messageDbDto.parentMessageId,
            parentMessageText = messageDbDto.parentMessageText,
            childMessageId = messageDbDto.childMessageId
        )
    }

    suspend fun getMessagesFromIdToId(fromId: Long, toId: Long): List<Messages> {
        return dbQuery.selectMessagesPage(endExclusive = fromId, beginInclusive = toId)
            .executeAsList()
    }

    suspend fun saveAuthorizationKey(key: String, modelGpt: String) {
        dbQuery.insertKey(key = key, modelGpt = modelGpt)
    }

    suspend fun getLastAuthorizationKeyFromDb() = dbQuery.selectAllKeysInfo().executeAsList()

    suspend fun deleteApiKeys() {
        dbQuery.removeAllKeys()
    }

    suspend fun saveSelectedTheme(themeId: Long) {
        dbQuery.removeAllSettings() //todo fix this
        dbQuery.insertThemeId(themeId)
    }

    suspend fun getSelectedThemeIdOrDefault(): Long? =
        dbQuery.selectAllSettings().executeAsList().takeIf { it.isNotEmpty() }?.first()

    fun updateChildIdForParentMessage(childMessageId: Long, parentMessageId: Long) {
        dbQuery.updateChildIdForParentMessage(
            childMessageId = childMessageId,
            messageId = parentMessageId
        )
    }

    suspend fun getLastMessageId(): Long? =
        dbQuery.lastMessageId().executeAsOneOrNull()?.messageId

    suspend fun getMessageById(messageId: Long): Messages? =
        dbQuery.getMessageById(messageId).executeAsOneOrNull()

    suspend fun deleteAllMessages() {
        dbQuery.removeAllMessages()
    }

    companion object {
        const val USER_TYPE = 0L
        const val GPT_TYPE = 1L
        const val SYSTEM_TYPE = 2L
    }
}

