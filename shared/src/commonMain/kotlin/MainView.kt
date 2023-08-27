import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import composables.MainScreen
import themes.AppTheme
import themes.ApplicationTheme

@Composable
@Suppress("FunctionName")
fun Application(notification: (String) -> Unit) {
    val viewModel = remember { MainViewModel() }
    val theme by viewModel.themeMode.collectAsState()
    viewModel.notificationUiState = { notificationMessage ->
        if (notificationMessage.isNotEmpty())
            notification.invoke(notificationMessage)
    }
    AppTheme(appTheme = theme) {
        MainScreen(
            viewModel = viewModel
        )
    }
}