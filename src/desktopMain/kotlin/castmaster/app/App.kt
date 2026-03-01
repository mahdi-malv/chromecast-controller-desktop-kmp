package castmaster.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import castmaster.app.config.DeviceConfig
import castmaster.app.device.DeviceManager
import castmaster.app.ui.controlpanel.ControlPanel

@Composable
fun App(
    deviceManager: DeviceManager,
    modifier: Modifier = Modifier
) {
    val configId = remember { DeviceConfig.getDeviceId() }
    var override by remember { mutableStateOf<String?>(null) }
    val effectiveId = (override?.takeIf { it.isNotBlank() } ?: configId).ifBlank { "" }

    LaunchedEffect(effectiveId) {
        deviceManager.setDeviceId(effectiveId)
    }

    ControlPanel(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        deviceManager = deviceManager,
        deviceId = effectiveId,
        onDeviceIdOverride = { override = it.ifBlank { null } },
        onSaveDeviceToConfig = { DeviceConfig.setDeviceId(effectiveId) },
    )
}
