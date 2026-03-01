package castmaster.app.ui.controlpanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import castmaster.app.device.DeviceManager
import castmaster.app.ui.components.DeviceCard
import castmaster.app.ui.components.ExpertSection
import castmaster.app.ui.components.FilePushSection
import castmaster.app.ui.components.RemoteSection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ControlPanel(
    modifier: Modifier,
    deviceManager: DeviceManager,
    deviceId: String,
    onDeviceIdOverride: (String) -> Unit,
    onSaveDeviceToConfig: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var connected by remember { mutableStateOf(false) }
    var deviceIdInput by remember(deviceId) { mutableStateOf(deviceId) }

    LaunchedEffect(Unit) {
        deviceManager.adbError?.let { snackbarHostState.showSnackbar(it) }
    }

    LaunchedEffect(deviceId) {
        connected = deviceManager.isDeviceConnected()
    }
    LaunchedEffect(deviceId) {
        if (deviceId.isBlank()) return@LaunchedEffect
        while (true) {
            delay(3000)
            connected = deviceManager.isDeviceConnected()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DeviceCard(
            deviceIdInput = deviceIdInput,
            onDeviceIdChange = { deviceIdInput = it; onDeviceIdOverride(it) },
            connected = connected,
            onSaveAsDefault = onSaveDeviceToConfig,
        )

        RemoteSection(
            enabled = deviceManager.hasDevice(),
            onShell = { scope.launch { deviceManager.runShell(it) } },
        )

        FilePushSection(
            enabled = deviceManager.hasDevice(),
            onPush = { file ->
                val ok = deviceManager.pushFile(file)
                snackbarHostState.showSnackbar(
                    if (ok) "Pushed ${file.name} to /sdcard/Download/" else "Push failed"
                )
            },
        )

        ExpertSection(
            enabled = deviceManager.hasDevice(),
            onShell = { cmd ->
                scope.launch {
                    val out = deviceManager.runShell(cmd)
                    snackbarHostState.showSnackbar(out.take(200).ifEmpty { "(no output)" })
                }
            },
        )

        SnackbarHost(hostState = snackbarHostState)
    }
}
