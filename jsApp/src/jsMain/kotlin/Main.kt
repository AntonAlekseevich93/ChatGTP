import di.PlatformSDK
import org.jetbrains.skiko.wasm.onWasmReady
import platform.PlatformConfiguration


fun main() {
    onWasmReady {
        BrowserViewportWindow("ChatGPT") {
            PlatformSDK.init(PlatformConfiguration())
            Application{

            }
        }
    }
}

