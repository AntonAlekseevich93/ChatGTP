package composables.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composables.dialog.ApiKeysAlertDialog
import composables.themeSwitcher.ThemeSwitcherDialog
import themes.AppTheme
import themes.ApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    appBarTitle: String,
    keyList: List<String>,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    themeState: AppTheme,
    showBackButton: Boolean,
    backButtonListener: () -> Unit,
    themeSwitcherListener: () -> Unit,
    deleteApiKeysListener: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var apiKeysAlertDialogState by remember { mutableStateOf(false) }
    var themeSwitcherDialogState by remember { mutableStateOf(false) }
    if (apiKeysAlertDialogState) {
        ApiKeysAlertDialog(
            keyList = keyList,
            onDismissRequest = { apiKeysAlertDialogState = false },
            onDeleteAllKeysEvent = deleteApiKeysListener
        )
    }

    if (themeSwitcherDialogState) {
        ThemeSwitcherDialog(
            themeState = themeState,
            onDismissRequest = { themeSwitcherDialogState = false },
            themeSwitcherListener = themeSwitcherListener
        )
    }


    ChatAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = appBarTitle,
                    style = ApplicationTheme.typography.headlineMedium,
                    color = ApplicationTheme.colors.mainTextColor
                )
            }
        },
        showBackButton = showBackButton,
        backButtonListener = backButtonListener,
        actions = {
            // Theme icon
            Icon(
                imageVector = Icons.Outlined.ColorLens,
                tint = ApplicationTheme.colors.appbarIconsColor,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { themeSwitcherDialogState = true }
                    )
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .height(24.dp),
                contentDescription = null
            )
            // Info icon
            Icon(
                imageVector = Icons.Outlined.Info,
                tint = ApplicationTheme.colors.appbarIconsColor,
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { apiKeysAlertDialogState = true }
                    )
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .height(24.dp),
                contentDescription = null
            )
        }
    )
}
