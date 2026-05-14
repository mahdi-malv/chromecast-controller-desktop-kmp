package castmaster.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import castmaster.app.theme.castMasterColors

@Composable
fun DeviceCard(
    deviceIdInput: String,
    onDeviceIdChange: (String) -> Unit,
    connected: Boolean,
    onSaveAsDefault: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val appColors = castMasterColors()
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = appColors.panel),
        border = BorderStroke(1.dp, appColors.panelBorder),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Device",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            OutlinedTextField(
                value = deviceIdInput,
                onValueChange = onDeviceIdChange,
                label = { Text("Serial or IP:5555") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    color = if (connected) appColors.successContainer else MaterialTheme.colorScheme.errorContainer,
                    contentColor = if (connected) appColors.onSuccessContainer else MaterialTheme.colorScheme.onErrorContainer,
                    shape = RoundedCornerShape(999.dp),
                ) {
                    Text(
                        text = if (connected) "Connected" else "Disconnected",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    )
                }
                TextButton(onClick = onSaveAsDefault) { Text("Save as default") }
            }
        }
    }
}

@Composable
@Preview
private fun DeviceCardPreview() {
    castmaster.app.theme.CastMasterTheme {
        DeviceCard(
            deviceIdInput = "192.168.1.100:5555",
            onDeviceIdChange = {},
            connected = true,
            onSaveAsDefault = {},
        )
    }
}
