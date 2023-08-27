package composables.themeSwitcher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import themes.AppTheme
import themes.ApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionName")
fun ThemeSwitcherDialog(
    themeState: AppTheme,
    onDismissRequest: () -> Unit,
    themeSwitcherListener: () -> Unit
) {
    val iconColor = ApplicationTheme.colors.appbarIconsColor

    AlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = ApplicationTheme.colors.cardBackgroundColor),
        ) {
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .clip(CircleShape)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val commonModifier = Modifier.align(Alignment.CenterVertically)
                Icon(
                    imageVector = Icons.Outlined.LightMode,
                    contentDescription = null,
                    modifier = commonModifier,
                    tint = iconColor
                )
                Switch(
                    checked = themeState.toBoolean(),
                    onCheckedChange = {
                        themeSwitcherListener.invoke()
                    },
                    modifier = commonModifier,
                    colors = SwitchDefaults.colors(ApplicationTheme.colors.switchCheckedColor)
                )
                Icon(
                    imageVector = Icons.Outlined.DarkMode,
                    contentDescription = null,
                    modifier = commonModifier,
                    tint = iconColor
                )
            }
        }
    }
}

fun AppTheme.toBoolean() = when (this) {
    AppTheme.DARK -> true
    AppTheme.LIGHT -> false
}