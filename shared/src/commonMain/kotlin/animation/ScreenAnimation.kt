package animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SlideAnimationFirstScreen(
    isShow: Boolean,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        isShow,
        modifier = Modifier.fillMaxSize(),
        enter = slideInHorizontally(
            initialOffsetX = { 1000 },
            animationSpec = tween(
                durationMillis = 100,
                easing = LinearEasing
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { 1000 },
            animationSpec = tween(
                durationMillis = 100,
                easing = LinearEasing
            )
        ),
        content = content
    )
}

@Composable
fun SlideAnimationSecondScreen(
    isShow: Boolean,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        isShow,
        modifier = Modifier.fillMaxSize(),
        enter = slideInHorizontally(
            initialOffsetX = { -1000 },
            animationSpec = tween(
                durationMillis = 100,
                easing = LinearEasing
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { -1000 },
            animationSpec = tween(
                durationMillis = 100,
                easing = LinearEasing
            )
        ),
        content = content
    )
}