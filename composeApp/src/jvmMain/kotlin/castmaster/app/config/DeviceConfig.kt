package castmaster.app.config

import java.io.File

/**
 * Persists and reads the default ADB device ID (serial or IP:5555).
 * Config file: ~/.castmaster/device_id
 */
object DeviceConfig {
    private val configDir: File by lazy {
        File(System.getProperty("user.home"), ".castmaster").also { it.mkdirs() }
    }
    private val deviceIdFile: File get() = File(configDir, "device_id")

    fun getDeviceId(): String =
        deviceIdFile.takeIf { it.exists() }?.readText()?.trim().orEmpty()

    fun setDeviceId(deviceId: String) {
        deviceIdFile.writeText(deviceId.trim())
    }
}
