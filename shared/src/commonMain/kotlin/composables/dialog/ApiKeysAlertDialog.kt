package composables.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import constants.APP_VERSION
import constants.DB_NAME_WITH_VERSION
import themes.ApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiKeysAlertDialog(
    keyList: List<String>,
    onDismissRequest: () -> Unit,
    onDeleteAllKeysEvent: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = ApplicationTheme.colors.cardBackgroundColor),
        ) {
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Список активных Api Keys",
                        style = ApplicationTheme.typography.headlineMedium,
                        color = ApplicationTheme.colors.mainTextColor
                    )
                }
                Spacer(Modifier.padding(bottom = 16.dp))
                keyList.forEach { key ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = ApplicationTheme.colors.keyInfoCardBackgroundColor
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        SelectionContainer() {
                            Text(
                                text = key,
                                modifier = Modifier.padding(8.dp),
                                style = ApplicationTheme.typography.captionRegular,
                                color = ApplicationTheme.colors.mainTextColor
                            )
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                    Text(
                        text ="app version: $APP_VERSION",
                        modifier = Modifier.padding(top = 10.dp),
                        style = ApplicationTheme.typography.captionRegular,
                        color = ApplicationTheme.colors.mainTextColor,
                    )
                }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                    Text(
                        text ="db version: $DB_NAME_WITH_VERSION",
                        modifier = Modifier.padding(top = 10.dp),
                        style = ApplicationTheme.typography.captionRegular,
                        color = ApplicationTheme.colors.mainTextColor,
                    )
                }
            }
            Spacer(Modifier.padding(bottom = 8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    "Удалить ключи",
                    style = ApplicationTheme.typography.headlineMedium,
                    color = ApplicationTheme.colors.mainTextColor,
                    modifier = Modifier.padding(end = 16.dp).clickable {
                        onDeleteAllKeysEvent.invoke()
                        onDismissRequest.invoke()
                    }
                )
                Text(
                    "Закрыть",
                    style = ApplicationTheme.typography.headlineMedium,
                    color = ApplicationTheme.colors.mainTextColor,
                    modifier = Modifier.clickable {
                        onDismissRequest.invoke()
                    }
                )
            }
        }
    }
}