package castmaster.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File
import javax.swing.JFileChooser
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FilePushSection(
    enabled: Boolean,
    onPush: (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier.fillMaxWidth().height(72.dp),
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(
                onClick = {
                    val chooser = JFileChooser().apply {
                        fileSelectionMode = JFileChooser.FILES_ONLY
                        isMultiSelectionEnabled = false
                    }
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        chooser.selectedFile?.let { onPush(it) }
                    }
                },
                enabled = enabled,
                shape = RoundedCornerShape(28.dp),
            ) {
                Icon(Icons.Default.Upload, contentDescription = null, Modifier.size(20.dp))
                Spacer(Modifier.size(8.dp))
                Text("Push file to /sdcard/Download/")
            }
        }
    }
}

@Composable
@Preview
private fun FilePushSectionPreview() {
    castmaster.app.theme.CastMasterTheme {
        FilePushSection(
            enabled = true,
            onPush = {},
        )
    }
}
