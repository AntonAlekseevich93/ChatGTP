package composables.input

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import themes.ApplicationTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInput(
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    resetScroll: () -> Unit = {},
) {
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }
    var textState by remember { mutableStateOf(TextFieldValue()) }
    // Used to decide if the keyboard should be shown
    var textFieldFocusState by remember { mutableStateOf(false) }
    Surface(elevation = 2.dp) {
        val actualOnMessageSent = {
            onMessageSent(textState.text.trim())
            // Reset text field and close keyboard
            textState = TextFieldValue()
            // Move scroll to bottom
            resetScroll()
            dismissKeyboard()
        }
        Column(
            modifier = modifier
                .background(ApplicationTheme.colors.userInputBackgroundColor)
        ) {
            UserInputText(
                textFieldValue = textState,
                onTextChanged = { textState = it },
                // Only show the keyboard if there's no input selector and text field has focus
                // keyboardShown = currentInputSelector == InputSelector.NONE && textFieldFocusState,
                // Close extended selector if text field receives focus
                onTextFieldFocused = { focused ->
                    if (focused) {
                        currentInputSelector = InputSelector.NONE
                        resetScroll()
                    }
                    textFieldFocusState = focused
                },
                focusState = textFieldFocusState,
                onMessageSent = actualOnMessageSent
            )
            SelectorExpanded(
                currentSelector = currentInputSelector
            )
        }
    }
}

@Composable
private fun SelectorExpanded(
    currentSelector: InputSelector,
) {
    if (currentSelector == InputSelector.NONE) return
    Surface(elevation = 8.dp) {
        when (currentSelector) {
            InputSelector.PICTURE -> FunctionalityNotAvailablePanel()
            else -> {
                throw NotImplementedError()
            }
        }
    }
}

@Composable
fun FunctionalityNotAvailablePanel() {
    AnimatedVisibility(
        visibleState = remember { MutableTransitionState(false).apply { targetState = true } },
        // Remove if https://issuetracker.google.com/190816173 is fixed
        enter = expandHorizontally() + fadeIn(),
        exit = shrinkHorizontally() + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .height(320.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Functionality currently not available",
                style = androidx.compose.material.MaterialTheme.typography.body1
            )
            Text(
                text = "Grab a beverage and check back later",
                modifier = Modifier.paddingFrom(FirstBaseline, before = 32.dp),
                style = androidx.compose.material.MaterialTheme.typography.body2,
                color = Color.DarkGray
            )
        }
    }
}


@ExperimentalFoundationApi
@Composable
private fun UserInputText(
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean,
    onMessageSent: () -> Unit,
) {
    var ctrlPressed by remember { mutableStateOf(false) }
    var lastFocusState by remember { mutableStateOf(false) }
    Divider(Modifier.height(0.2.dp))
    Box(
        Modifier
            .padding(horizontal = 4.dp)
            .padding(top = 6.dp, bottom = 6.dp)
    ) {
        Row {
            TextField(
                value = textFieldValue,
                onValueChange = {
                    if (!ctrlPressed) onTextChanged(it)
                },
                label = null,
                placeholder = {
                    Text(
                        text = "Ask me anything",
                        fontSize = 14.sp,
                        color = ApplicationTheme.colors.mainTextColor
                    )
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .weight(1f)
                    .onFocusChanged { state ->
                        if (lastFocusState != state.isFocused) {
                            onTextFieldFocused(state.isFocused)
                        }
                        lastFocusState = state.isFocused
                    }
                    .onKeyEvent {
                        when (it.key) {
                            Key.Enter -> {
                                if (it.type == KeyEventType.KeyDown && ctrlPressed) {
                                    onMessageSent()
                                    true
                                } else false
                            }

                            Key.CtrlLeft,
                            Key.CtrlRight,
                            -> {
                                when (it.type) {
                                    KeyEventType.KeyDown -> {
                                        ctrlPressed = true
                                        true
                                    }

                                    KeyEventType.KeyUp -> {
                                        ctrlPressed = false
                                        true
                                    }

                                    else -> false
                                }
                            }

                            else -> false
                        }
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = ApplicationTheme.colors.userInputInnerBackgroundColor,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    textColor = ApplicationTheme.colors.userInputTextColor
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.Send
                ),
                maxLines = 100,
            )

            IconButton(onClick = {
                if (textFieldValue.text.isNotEmpty() && textFieldValue.text.isNotBlank())
                    onMessageSent.invoke()
            }) {
                Icon(
                    Icons.Filled.Send,
                    null,
                    modifier = Modifier.size(26.dp),
                    tint = if (textFieldValue.text.isEmpty())
                        ApplicationTheme.colors.userInputSendDisableButtonColor
                    else
                        ApplicationTheme.colors.userInputSendEnableButtonColor,
                )
            }
        }
    }
}

enum class InputSelector {
    NONE,
    PICTURE
}