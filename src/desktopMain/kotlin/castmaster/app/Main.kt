package castmaster.app

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import castmaster.app.config.DeviceConfig
import castmaster.app.device.DeviceManager
import castmaster.app.device.LogcatObserver
import castmaster.app.theme.CastMasterTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun main() = application {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    val deviceManager = DeviceManager(DeviceConfig.getDeviceId())
    val logcatObserver = LogcatObserver(deviceId = { deviceManager.getDeviceId() }, scope = scope)

    val windowState = rememberWindowState(width = 720.dp, height = 1280.dp)
    Window(
        onCloseRequest = {
            logcatObserver.stop()
            exitApplication()
        },
        state = windowState,
        title = "CastMaster Pro",
    ) {
        CastMasterTheme {
            App(
                deviceManager = deviceManager,
                logcatObserver = logcatObserver,
            )
        }
    }
}
