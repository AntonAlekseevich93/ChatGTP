import androidx.compose.ui.window.ComposeUIViewController
import di.PlatformSDK
import platform.PlatformConfiguration

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController {
    PlatformSDK.init(PlatformConfiguration())
    Application {}
}