package castmaster.app.device

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

/**
 * Runs ADB commands for a specific device. All operations use `adb -s <deviceId>`.
 * Must be used with a non-empty device ID.
 */
class DeviceManager(private var deviceId: String) {

    fun setDeviceId(id: String) { deviceId = id }

    fun getDeviceId(): String = deviceId

    fun hasDevice(): Boolean = deviceId.isNotBlank()

    suspend fun isDeviceConnected(): Boolean = withContext(Dispatchers.IO) {
        if (deviceId.isBlank()) return@withContext false
        runAdb("devices").lines()
            .drop(1)
            .any { it.trim().startsWith(deviceId) && it.contains("device") }
    }

    /**
     * Runs `adb -s <deviceId> shell <command>` and returns combined stdout and stderr.
     */
    suspend fun runShell(command: String): String = withContext(Dispatchers.IO) {
        if (deviceId.isBlank()) return@withContext "No device set."
        runAdb("shell", command)
    }

    /**
     * Pushes a local file to the device. Default remote path: /sdcard/Download/
     * Returns true on success.
     */
    suspend fun pushFile(local: File, remotePath: String = "/sdcard/Download/"): Boolean =
        withContext(Dispatchers.IO) {
            if (deviceId.isBlank()) return@withContext false
            if (!local.exists()) return@withContext false
            val remote = remotePath.trimEnd('/') + "/" + local.name
            val out = runAdb("push", local.absolutePath, remote)
            out.contains("pushed") || out.contains("100%")
        }

    private fun runAdb(vararg args: String): String {
        val fullArgs = listOf("adb", "-s", deviceId) + args
        val process = ProcessBuilder(fullArgs)
            .redirectErrorStream(true)
            .start()
        val output = InputStreamReader(process.inputStream).readText()
        process.waitFor(30, TimeUnit.SECONDS)
        return output
    }
}
