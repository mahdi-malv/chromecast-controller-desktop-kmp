package castmaster.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import castmaster.app.device.LogcatObserver
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ColumnScope.LogcatSection(
    enabled: Boolean,
    logcatObserver: LogcatObserver,
    onStart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var filter by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val lines = logcatObserver.lines.value
    val filteredLines = remember(lines, filter) {
        if (filter.isBlank()) lines else lines.filter { it.contains(filter, ignoreCase = true) }
    }

    LaunchedEffect(Unit) { if (enabled) onStart() }
    LaunchedEffect(filteredLines.size) {
        if (filteredLines.isNotEmpty()) listState.animateScrollToItem(filteredLines.size - 1)
    }

    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier.fillMaxWidth().weight(1f),
    ) {
        Column(Modifier.padding(12.dp).fillMaxSize()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Logcat", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                FilledTonalButton(onClick = { logcatObserver.clear() }, enabled = enabled, shape = RoundedCornerShape(12.dp)) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                    Spacer(Modifier.size(4.dp))
                    Text("Clear")
                }
            }
            OutlinedTextField(
                value = filter,
                onValueChange = { filter = it },
                label = { Text("Filter") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            )
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                items(filteredLines, key = { it.hashCode().toString() + it }) { line ->
                    Text(
                        text = colorizeLogLine(line),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

private fun colorizeLogLine(line: String): androidx.compose.ui.text.AnnotatedString {
    val level = when {
        line.contains(" E ") || line.startsWith("E/") -> LogLevel.E
        line.contains(" W ") || line.startsWith("W/") -> LogLevel.W
        line.contains(" I ") || line.startsWith("I/") -> LogLevel.I
        line.contains(" D ") || line.startsWith("D/") -> LogLevel.D
        line.contains(" V ") || line.startsWith("V/") -> LogLevel.V
        else -> LogLevel.OTHER
    }
    return buildAnnotatedString {
        withStyle(SpanStyle(color = level.color)) { append(line) }
    }
}

private enum class LogLevel(val color: Color) {
    V(Color(0xFF9E9E9E)),
    D(Color(0xFF2196F3)),
    I(Color(0xFF4CAF50)),
    W(Color(0xFFFF9800)),
    E(Color(0xFFF44336)),
    OTHER(Color(0xFFE0E0E0))
}

@Composable
@Preview
private fun LogcatSectionPreview() {
    val scope = rememberCoroutineScope()
    val observer = remember { LogcatObserver(deviceId = { "" }, scope = scope) }
    castmaster.app.theme.CastMasterTheme {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            LogcatSection(
                enabled = true,
                logcatObserver = observer,
                onStart = { observer.start() },
            )
        }
    }
}
