package castmaster.app

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import castmaster.app.config.DeviceConfig
import castmaster.app.device.DeviceManager
import castmaster.app.theme.CastMasterTheme

fun main() = application {
    val deviceManager = DeviceManager(DeviceConfig.getDeviceId())

    val windowState = rememberWindowState(width = 600.dp, height = 670.dp)
    Window(
        onCloseRequest = { exitApplication() },
        state = windowState,
        title = "CastMaster Pro",
    ) {
        CastMasterTheme {
            App(deviceManager = deviceManager)
        }
    }
}
