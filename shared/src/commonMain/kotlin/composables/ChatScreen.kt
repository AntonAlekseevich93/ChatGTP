package composables

import MainViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import composables.appbar.MainAppBar
import composables.input.UserInput
import composables.messages.Messages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import platform.statusBarsPaddingMpp
import platform.userInputModifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionName")
internal fun ConversationContent(
    viewModel: MainViewModel,
    scrollState: LazyListState,
    scope: CoroutineScope,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val messagesState by viewModel.conversationUiState.collectAsState()
    val waitingForResponseState by viewModel.waitingForResponseState.collectAsState()
    val keyList by viewModel.keyList.collectAsState()
    val themeState by viewModel.themeMode.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Messages(messagesState, scrollState, waitingForResponseState)
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            UserInput(
                onMessageSent = { content ->
                    viewModel.sendMessage(content)
                },
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                // Use navigationBarsWithImePadding(), to move the input panel above both the
                // navigation bar, and on-screen keyboard (IME)
                modifier = Modifier.userInputModifier(),
            )
        }
        MainAppBar(
            appBarTitle = messagesState.appBarTitle,
            scrollBehavior = scrollBehavior,
            keyList = keyList,
            themeState = themeState,
            // Use statusBarsPadding() to move the app bar content below the status bar
            modifier = Modifier.statusBarsPaddingMpp(),
            themeSwitcherListener = { viewModel.switchTheme() },
            deleteApiKeysListener = { viewModel.deleteApiKeys() }
        )
    }
}