import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.TrayState
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import di.PlatformSDK
import platform.PlatformConfiguration
import java.awt.Dimension

fun main() = application {
    PlatformSDK.init(PlatformConfiguration())
    val trayState: TrayState = rememberTrayState()
    Tray(
        state = trayState,
        icon = TrayIcon,
        menu = {
            Item(
                "Exit",
                onClick = ::exitApplication
            )
        }
    )

    val iconPainter = BitmapPainter(useResource("logo_ai_gpt.png", ::loadImageBitmap)) //todo
    Window(
        title = "ChatGPT",
        onCloseRequest = ::exitApplication,
    ) {
        val density = LocalDensity.current
        Application() { message ->
            trayState.sendNotification(Notification(title = "ChatGPT", message = message))
        }
        SideEffect {
            window.iconImage = //todo
                iconPainter.toAwtImage(density, LayoutDirection.Ltr, Size(128f, 128f))
        }
        LaunchedEffect(Unit) {
            window.size = Dimension(1100, 800)
        }
    }
}


object TrayIcon : Painter() {

    override val intrinsicSize = Size(0f, 0f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFFFFF))
    }
}