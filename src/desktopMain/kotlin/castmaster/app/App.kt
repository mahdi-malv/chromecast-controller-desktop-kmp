package castmaster.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import castmaster.app.config.DeviceConfig
import castmaster.app.device.DeviceManager
import castmaster.app.device.LogcatObserver
import castmaster.app.device.ScrcpyBridge
import castmaster.app.ui.controlpanel.ControlPanel

@Composable
fun App(
    deviceManager: DeviceManager,
    logcatObserver: LogcatObserver,
    scrcpyBridge: ScrcpyBridge,
) {
    val configId = remember { DeviceConfig.getDeviceId() }
    var override by remember { mutableStateOf<String?>(null) }
    val effectiveId = (override?.takeIf { it.isNotBlank() } ?: configId).ifBlank { "" }

    LaunchedEffect(effectiveId) {
        deviceManager.setDeviceId(effectiveId)
    }

    DisposableEffect(Unit) {
        onDispose {
            scrcpyBridge.stop()
            logcatObserver.stop()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.Start,
    ) {
        MirrorPane(modifier = Modifier.weight(2f), scrcpyBridge = scrcpyBridge, deviceId = effectiveId)
        ControlPanel(
            modifier = Modifier.weight(1f),
            deviceManager = deviceManager,
            logcatObserver = logcatObserver,
            scrcpyBridge = scrcpyBridge,
            deviceId = effectiveId,
            onDeviceIdOverride = { override = it.ifBlank { null } },
            onSaveDeviceToConfig = { DeviceConfig.setDeviceId(effectiveId) },
        )
    }
}

@Composable
private fun MirrorPane(
    modifier: Modifier = Modifier,
    scrcpyBridge: ScrcpyBridge,
    deviceId: String,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "Scrcpy window will appear here",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        if (deviceId.isNotBlank()) {
            Button(
                onClick = { scrcpyBridge.start() },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.padding(top = 8.dp),
            ) {
                Text("Start scrcpy")
            }
        }
    }
}
