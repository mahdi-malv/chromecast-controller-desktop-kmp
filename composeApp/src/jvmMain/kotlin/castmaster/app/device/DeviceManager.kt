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

    val adbPath: String? = resolveAdbPath()
    val adbError: String? = if (adbPath == null) "adb not found. Install Android SDK Platform Tools and ensure adb is on your PATH." else null

    fun setDeviceId(id: String) { deviceId = id }

    fun getDeviceId(): String = deviceId

    fun hasDevice(): Boolean = deviceId.isNotBlank()

    fun isAdbAvailable(): Boolean = adbPath != null

    suspend fun isDeviceConnected(): Boolean = withContext(Dispatchers.IO) {
        if (deviceId.isBlank() || adbPath == null) return@withContext false
        runAdb("devices").lines()
            .drop(1)
            .any { it.trim().startsWith(deviceId) && it.contains("device") }
    }

    /**
     * Runs `adb -s <deviceId> shell <command>` and returns combined stdout and stderr.
     */
    suspend fun runShell(command: String): String = withContext(Dispatchers.IO) {
        if (deviceId.isBlank()) return@withContext "No device set."
        if (adbPath == null) return@withContext adbError ?: "adb not found."
        runAdb("shell", command)
    }

    /**
     * Pushes a local file to the device. Default remote path: /sdcard/Download/
     * Returns true on success.
     */
    suspend fun pushFile(local: File, remotePath: String = "/sdcard/Download/"): Boolean =
        withContext(Dispatchers.IO) {
            if (deviceId.isBlank() || adbPath == null) return@withContext false
            if (!local.exists()) return@withContext false
            val remote = remotePath.trimEnd('/') + "/" + local.name
            val out = runAdb("push", local.absolutePath, remote)
            out.contains("pushed") || out.contains("100%")
        }

    private fun runAdb(vararg args: String): String {
        return try {
            val fullArgs = listOf(adbPath!!, "-s", deviceId) + args
            val process = ProcessBuilder(fullArgs)
                .redirectErrorStream(true)
                .start()
            val output = InputStreamReader(process.inputStream).readText()
            process.waitFor(30, TimeUnit.SECONDS)
            output
        } catch (e: Exception) {
            "adb error: ${e.message}"
        }
    }

    companion object {
        private fun resolveAdbPath(): String? {
            // Check common install locations first
            val candidates = listOf(
                "/opt/homebrew/bin/adb",
                "/usr/local/bin/adb",
                "${System.getProperty("user.home")}/Library/Android/sdk/platform-tools/adb",
                "/usr/local/lib/android/sdk/platform-tools/adb",
                "/opt/android-sdk/platform-tools/adb",
            )
            candidates.firstOrNull { File(it).canExecute() }?.let { return it }

            // Fall back to `which adb` to honour the user's PATH
            return try {
                val process = ProcessBuilder("which", "adb")
                    .redirectErrorStream(true)
                    .start()
                val path = InputStreamReader(process.inputStream).readText().trim()
                process.waitFor(5, TimeUnit.SECONDS)
                path.takeIf { it.isNotBlank() && File(it).canExecute() }
            } catch (_: Exception) {
                null
            }
        }
    }
}
