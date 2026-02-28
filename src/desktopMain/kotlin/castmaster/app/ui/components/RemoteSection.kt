package castmaster.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Remote",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.size(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.size(48.dp))
                FilledTonalButton(
                    onClick = { onShell("input keyevent KEYCODE_DPAD_UP") },
                    enabled = enabled,
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.padding(4.dp)
                ) { Text("↑") }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilledTonalButton(
                    onClick = { onShell("input keyevent KEYCODE_DPAD_LEFT") },
                    enabled = enabled,
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.padding(4.dp)
                ) { Text("←") }
                Spacer(Modifier.size(48.dp))
                FilledTonalButton(
                    onClick = { onShell("input keyevent KEYCODE_DPAD_RIGHT") },
                    enabled = enabled,
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.padding(4.dp)
                ) { Text("→") }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.size(48.dp))
                FilledTonalButton(
                    onClick = { onShell("input keyevent KEYCODE_DPAD_DOWN") },
                    enabled = enabled,
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.padding(4.dp)
                ) { Text("↓") }
            }
            Spacer(Modifier.size(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilledTonalButton(
                    onClick = { onShell("input keyevent KEYCODE_HOME") },
                    enabled = enabled,
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.padding(4.dp)
                ) { Icon(Icons.Default.Home, contentDescription = "Home") }
                FilledTonalButton(
                    onClick = { onShell("input keyevent KEYCODE_BACK") },
                    enabled = enabled,
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.padding(4.dp)
                ) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
                FilledTonalButton(
                    onClick = { onShell("input keyevent KEYCODE_MEDIA_PLAY_PAUSE") },
                    enabled = enabled,
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.padding(4.dp)
                ) { Icon(Icons.Default.PlayArrow, contentDescription = "Play/Pause") }
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
