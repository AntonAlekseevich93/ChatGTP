package composables.authorization

import MainViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import resourceBindings.drawable_gpt_ai_icon
import themes.ApplicationTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AuthorizationScreen(
    viewModel: MainViewModel,
) {
    val key = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .background(ApplicationTheme.colors.chatBackgroundColor)
            .padding(top = 32.dp)
    ) {

        Text(
            "Авторизация",
            style = ApplicationTheme.typography.title1Medium,
            modifier = Modifier.padding(bottom = 32.dp),
            color = ApplicationTheme.colors.mainTextColor
        )
        Image(
            modifier = Modifier.size(110.dp).padding(bottom = 32.dp),
            painter = painterResource(drawable_gpt_ai_icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ApplicationTheme.colors.logoAiTintColor)
        )
        Box(modifier = Modifier.padding(bottom = 8.dp, top = 16.dp).width(280.dp)) {
            Text("Модель: gpt-4", color = ApplicationTheme.colors.mainTextColor)
        }
        OutlinedTextField(
            value = key.value,
            onValueChange = {
                key.value = it
            },
            singleLine = true,
            label = {
                Text(
                    text = "Введите API KEY",
                    color = ApplicationTheme.colors.mainTextColor
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = ApplicationTheme.colors.textFieldFocusedBorderColor,
                focusedLabelColor = ApplicationTheme.colors.textFieldFocusedLabelColor,
                textColor = ApplicationTheme.colors.mainTextColor
            ),
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Button(
            onClick = {
                viewModel.logIn(key = key.value)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ApplicationTheme.colors.mainButtonBackgroundColor
            ),
            modifier = Modifier.padding(top = 16.dp).width(280.dp)
        ) {
            Text("Авторизоваться", color = ApplicationTheme.colors.mainButtonTextColor)
        }
    }
}