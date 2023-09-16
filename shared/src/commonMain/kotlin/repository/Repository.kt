package repository

import data.MessageVo
import data.toDto
import data.toVo
import database.SqlDelightDataSource

class Repository(
    private val dataSource: SqlDelightDataSource
) {
    suspend fun insertMessageToDb(message: MessageVo) {
        dataSource.insertMessage(message.toDto())
    }

    suspend fun getMessagesFromIdToId(fromId: Long, toId: Long) =
        dataSource.getMessagesFromIdToId(fromId, toId).map { it.toVo() }

    suspend fun saveAuthorizationKey(key: String, modelGpt: String) {
        dataSource.saveAuthorizationKey(key, modelGpt)
    }

    suspend fun getLastAuthorizationKeyFromDb(): String {
        val list = dataSource.getLastAuthorizationKeyFromDb()
        return if (list.isNotEmpty()) list.first().key
        else ""
    }

    suspend fun deleteApiKeys() {
        dataSource.deleteApiKeys()
    }

    suspend fun saveSelectedTheme(themeId: Int) {
        dataSource.saveSelectedTheme(themeId.toLong())
    }

    suspend fun updateChildIdForParentMessage(
        childMessageId: Long,
        parentMessageId: Long
    ) {
        dataSource.updateChildIdForParentMessage(childMessageId, parentMessageId)
    }

    suspend fun getSelectedThemeIdOrDefault(): Int? =
        dataSource.getSelectedThemeIdOrDefault()?.toInt()

    suspend fun getLastMessageId() = dataSource.getLastMessageId()

    suspend fun getMessageById(messageId: Long) = dataSource.getMessageById(messageId)?.toVo()
    suspend fun deleteAllMessages() {
        dataSource.deleteAllMessages()
    }
}