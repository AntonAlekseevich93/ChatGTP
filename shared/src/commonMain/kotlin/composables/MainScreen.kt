package composables

import MainViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import composables.authorization.AuthorizationScreen
import data.AppScreenState

@Composable
@Suppress("FunctionName")
fun MainScreen(
    viewModel: MainViewModel,
) {
    val scrollState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val screenState by viewModel.screenState.collectAsState()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerShape = NavShape(300.dp, 0f),
    ) {
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
}

private class NavShape(
    private val widthOffset: Dp,
    private val scale: Float,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Rectangle(
            Rect(
                Offset.Zero,
                Offset(
                    size.width * scale + with(density) { widthOffset.toPx() },
                    size.height
                )
            )
        )
    }
}