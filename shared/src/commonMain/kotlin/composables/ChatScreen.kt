package composables

import MainViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import animation.SlideAnimationFirstScreen
import animation.SlideAnimationSecondScreen
import composables.appbar.MainAppBar
import composables.input.UserInput
import composables.messages.MessagesScreen
import composables.messages.quote.QuoteChatScreen
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
    val loaderIsShowingState by viewModel.waitingForResponseState.collectAsState()
    val keyList by viewModel.keyList.collectAsState()
    val themeState by viewModel.themeMode.collectAsState()
    val quoteDataUiState by viewModel.commonUiState.quoteDataUiState.collectAsState()
    val showQuoteMessagesBranch by messagesState.showQuoteMessagesBranch
    val scrollStateQuoteThread = rememberLazyListState()
    val quoteMessagesId by messagesState.quoteMessagesId

    Box(Modifier.fillMaxSize()) {
        SlideAnimationFirstScreen(showQuoteMessagesBranch) {
            QuoteChatScreen(
                conversationUiState = messagesState,
                scrollState = scrollStateQuoteThread,
                loaderIsShowing = loaderIsShowingState,
            )
        }

        SlideAnimationSecondScreen(!showQuoteMessagesBranch) {
            MessagesScreen(
                conversationUiState = messagesState,
                quoteDataUiState = quoteDataUiState,
                scrollState = scrollState,
                loaderIsShowing = loaderIsShowingState,
                uploadDataListener = { viewModel.uploadData() },
                quoteOpenBranchListener = { childMessageId ->
                    viewModel.openQuoteScreen(
                        childMessageId
                    )
                },
                quoteListener = { message, position, parentMessageId ->
                    viewModel.showQuote(
                        message,
                        position,
                        parentMessageId
                    )
                }
            )
        }
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            UserInput(
                onMessageSent = { content ->
                    if (quoteDataUiState.showingQuote) {
                        viewModel.sendMessageWithQuote(
                            message = content,
                            parentMessageId = quoteDataUiState.parentMessageId
                        )
                    } else if (showQuoteMessagesBranch) {
                        viewModel.sendMessageWithQuote(
                            message = content,
                            parentMessageId = quoteMessagesId,
                            fromQuoteScreen = true
                        )
                    } else {
                        viewModel.sendMessage(content)
                    }
                },
                resetScroll = {
                    scope.launch {
                        scrollStateQuoteThread.scrollToItem(0)
                        scrollState.scrollToItem(0)
                    }
                },
                // Use navigationBarsWithImePadding(), to move the input panel above both the
                // navigation bar, and on-screen keyboard (IME)
                modifier = Modifier.userInputModifier(),
                closeQuoteListener = { viewModel.closeQuote() },
                quoteDataUiState = quoteDataUiState,
            )
        }
        MainAppBar(
            appBarTitle = messagesState.appBarTitle,
            scrollBehavior = scrollBehavior,
            keyList = keyList,
            themeState = themeState,
            // Use statusBarsPadding() to move the app bar content below the status bar
            modifier = Modifier.statusBarsPaddingMpp(),
            showBackButton = showQuoteMessagesBranch,
            backButtonListener = { viewModel.closeMessagesThread() },
            themeSwitcherListener = { viewModel.switchTheme() },
            deleteApiKeysListener = { viewModel.deleteApiKeys() }
        )
    }
}