package castmaster.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

private val DpadButtonShape = RoundedCornerShape(16.dp)

@Composable
private fun DpadButton(label: String, enabled: Boolean, onClick: () -> Unit) {
    FilledTonalButton(
        onClick = onClick,
        enabled = enabled,
        shape = DpadButtonShape
    ) {
        Text(
            label,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun RemoteSection(
    enabled: Boolean,
    onShell: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                "Remote",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Left 1/3 — D-pad
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    DpadButton("↑", enabled) { onShell("input keyevent KEYCODE_DPAD_UP") }
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        DpadButton("←", enabled) { onShell("input keyevent KEYCODE_DPAD_LEFT") }
                        DpadButton("∘", enabled) { onShell("input keyevent KEYCODE_DPAD_CENTER") }
                        DpadButton("→", enabled) { onShell("input keyevent KEYCODE_DPAD_RIGHT") }
                    }
                    DpadButton("↓", enabled) { onShell("input keyevent KEYCODE_DPAD_DOWN") }
                }

                // Right 2/3 — action buttons + reserved space
                Column(
                    modifier = Modifier.weight(2f)
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        FilledTonalButton(
                            onClick = { onShell("input keyevent KEYCODE_HOME") },
                            enabled = enabled,
                            modifier = Modifier.weight(1f),
                        ) { Icon(Icons.Default.Home, contentDescription = "Home") }
                        FilledTonalButton(
                            onClick = { onShell("input keyevent KEYCODE_BACK") },
                            enabled = enabled,
                            modifier = Modifier.weight(1f),
                        ) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                        FilledTonalButton(
                            onClick = { onShell("input keyevent KEYCODE_MEDIA_PLAY_PAUSE") },
                            enabled = enabled,
                            modifier = Modifier.weight(1f),
                        ) { Icon(Icons.Default.PlayArrow, contentDescription = "Play/Pause") }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        FilledTonalButton(
                            onClick = { onShell("input keyevent KEYCODE_POWER") },
                            enabled = enabled,
                            modifier = Modifier.weight(1f),
                        ) { Icon(Icons.Default.Power, contentDescription = "Power") }
                        FilledTonalButton(
                            onClick = { onShell("input keyevent KEYCODE_MENU") },
                            enabled = enabled,
                            modifier = Modifier.weight(1f),
                        ) { Icon(Icons.Default.Menu, contentDescription = "Menu") }
                        FilledTonalButton(
                            onClick = { onShell("input keyevent KEYCODE_VOLUME_MUTE") },
                            enabled = enabled,
                            modifier = Modifier.weight(1f),
                        ) { Icon(Icons.Default.VolumeMute, contentDescription = "Mute") }
                    }
                    // Reserved space for future buttons
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
@Preview
private fun RemoteSectionPreview() {
    castmaster.app.theme.CastMasterTheme {
        RemoteSection(
            enabled = true,
            onShell = {},
        )
    }
}
