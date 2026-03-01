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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.File
import javax.swing.JFileChooser
import kotlinx.coroutines.launch

@Composable
fun FilePushSection(
    enabled: Boolean,
    onPush: suspend (File) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }

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
                        chooser.selectedFile?.let { file ->
                            scope.launch {
                                loading = true
                                onPush(file)
                                loading = false
                            }
                        }
                    }
                },
                enabled = enabled && !loading,
                shape = RoundedCornerShape(28.dp),
            ) {
                if (loading) {
                    CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Icon(Icons.Default.Upload, contentDescription = null, Modifier.size(20.dp))
                }
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
            onPush = { _ -> },
        )
    }
}
