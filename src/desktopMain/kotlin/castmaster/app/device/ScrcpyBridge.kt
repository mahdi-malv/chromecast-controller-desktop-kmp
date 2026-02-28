package castmaster.app.device

import java.util.concurrent.atomic.AtomicReference

/**
 * Launches and manages the scrcpy process for a device. Call [stop] on window close.
 */
class ScrcpyBridge(private val deviceId: () -> String) {

    private val processRef = AtomicReference<Process?>(null)

    /**
     * Starts scrcpy with optional window position/size (e.g. to align with the 2/3 pane).
     * Existing process is stopped first.
     */
    fun start(windowX: Int? = null, windowY: Int? = null, windowWidth: Int? = null, windowHeight: Int? = null) {
        stop()
        val id = deviceId()
        if (id.isBlank()) return
        val args = mutableListOf("scrcpy", "-s", id)
        windowX?.let { args.addAll(listOf("--window-x", it.toString())) }
        windowY?.let { args.addAll(listOf("--window-y", it.toString())) }
        windowWidth?.let { args.addAll(listOf("--window-width", it.toString())) }
        windowHeight?.let { args.addAll(listOf("--window-height", it.toString())) }
        val process = ProcessBuilder(args).start()
        processRef.set(process)
    }

    fun stop() {
        processRef.getAndSet(null)?.destroyForcibly()
    }

    fun isRunning(): Boolean = processRef.get()?.isAlive == true
}
