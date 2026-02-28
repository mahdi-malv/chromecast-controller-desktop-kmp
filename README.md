# CastMaster Pro

macOS desktop utility using Compose Multiplatform (KMP). Split view: **2/3** device mirroring via **scrcpy** and **1/3** Material 3 control pane (remote, volume, file push, Logcat, expert shell).

## Requirements

- **JDK 17+**
- **adb** and **scrcpy** on `PATH` (e.g. `/usr/local/bin`)
- Chromecast (or any ADB device) connected via USB or network (`IP:5555`)

## Setup

1. **Gradle wrapper** (if `gradle/wrapper/gradle-wrapper.jar` is missing):
   ```bash
   gradle wrapper
   ```
2. Run:
   ```bash
   ./gradlew desktopRun
   ```

## Config

- Default device ID is stored in `~/.castmaster/device_id`. Set it in the app header and click **Save as default**, or override per session in the device field.
- All ADB commands use `adb -s <device_id>`.

## Features

- **Mirror pane**: Placeholder + **Start scrcpy** to launch scrcpy for the current device.
- **Header**: Device ID (editable), connection status (refreshed every 3s), Save as default.
- **Remote**: D-Pad, Home, Back, Play/Pause (keyevent shell).
- **Volume**: M3 slider, 150 ms debounce, `media volume --stream 3 --set N`.
- **File push**: **Push file to /sdcard/Download/** opens a file chooser; success/failure via Snackbar.
- **Logcat**: Last 1000 lines, Clear, filter (client-side), auto-scroll, color by level (V/D/I/W/E).
- **Expert mode**: Raw shell command + **Send**; output in Snackbar.

## Cleanup

On window close, scrcpy and logcat processes are stopped, then the app exits.
