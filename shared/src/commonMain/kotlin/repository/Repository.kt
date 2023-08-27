package repository

import data.MessageVo
import data.toDto
import database.SqlDelightDataSource

class Repository(
    private val dataSource: SqlDelightDataSource
) {
    suspend fun insertMessageToDb(message: MessageVo) {
        dataSource.insertMessage(message.toDto())
    }

    suspend fun getAllMessagesFromDb() = dataSource.getAllMessages()

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

    suspend fun getSelectedThemeIdOrDefault(): Int? =
        dataSource.getSelectedThemeIdOrDefault()?.toInt()

}