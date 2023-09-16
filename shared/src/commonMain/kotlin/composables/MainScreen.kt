package composables

import MainViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import composables.authorization.AuthorizationScreen
import data.AppScreenState

@Composable
@Suppress("FunctionName")
fun MainScreen(
    viewModel: MainViewModel,
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val screenState by viewModel.screenState.collectAsState()
    when (screenState) {
        AppScreenState.CHAT -> {
            AnimatedVisibility(screenState == AppScreenState.CHAT) {
                ConversationContent(
                    viewModel = viewModel,
                    scrollState = scrollState,
                    scope = coroutineScope,
                )
            }
        }

        AppScreenState.AUTHORIZATION -> {
            AnimatedVisibility(screenState == AppScreenState.AUTHORIZATION) {
                AuthorizationScreen(
                    viewModel = viewModel,
                )
            }
        }
    }
}