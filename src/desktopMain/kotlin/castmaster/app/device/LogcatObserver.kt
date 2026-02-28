package castmaster.app.device

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.atomic.AtomicReference

private const val MAX_LINES = 1000

/**
 * Streams device logcat via `adb -s <deviceId> logcat`. Keeps last [MAX_LINES] lines.
 * Run on a scope that is cancelled when the app closes so the process is terminated.
 */
class LogcatObserver(
    private val deviceId: () -> String,
    private val scope: CoroutineScope,
) {
    private val buffer = ArrayDeque<String>(MAX_LINES)
    private val filter = AtomicReference("")
    private val _lines = MutableStateFlow<List<String>>(emptyList())
    val lines: StateFlow<List<String>> = _lines.asStateFlow()

    private val processRef = AtomicReference<Process?>(null)

    fun start() {
        scope.launch(Dispatchers.IO) {
            val id = deviceId()
            if (id.isBlank()) return@launch
            val process = ProcessBuilder("adb", "-s", id, "logcat", "-v", "brief")
                .redirectErrorStream(true)
                .start()
            processRef.set(process)
            try {
                BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                    generateSequence { reader.readLine() }
                        .takeWhile { scope.isActive }
                        .forEach { line ->
                            val f = filter.get()
                            if (f.isEmpty() || line.contains(f, ignoreCase = true)) {
                                synchronized(buffer) {
                                    if (buffer.size >= MAX_LINES) buffer.removeFirst()
                                    buffer.addLast(line)
                                    _lines.value = buffer.toList()
                                }
                            }
                        }
                }
            } finally {
                processRef.set(null)
                try { process.destroyForcibly() } catch (_: Exception) { }
            }
        }
    }

    fun stop() {
        processRef.getAndSet(null)?.destroyForcibly()
    }

    fun clear() {
        synchronized(buffer) {
            buffer.clear()
            _lines.value = emptyList()
        }
    }

    fun updateFilter(newFilter: String) {
        filter.set(newFilter.trim())
    }
}
