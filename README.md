# CastMaster Pro

Simple ADB remote controller — macOS desktop utility using Compose Multiplatform (KMP). Material 3 control pane: remote, volume, file push, Logcat, expert shell.

## Requirements

- **JDK 17+**
- **adb** on `PATH` (e.g. `/usr/local/bin`)
- Chromecast (or any ADB device) connected via USB or network (`IP:5555`)

## Run
Use Android studio or run the gradle command:

```bash
   ./gradlew desktopRun
```

## Config

- Default device ID is stored in `~/.castmaster/device_id`. Set it in the app header and click **Save as default**, or override per session in the device field.
- All ADB commands use `adb -s <device_id>`.

## Features

- **Header**: Device ID (editable), connection status (refreshed every 3s), Save as default.
- **Remote**: D-Pad, Home, Back, Play/Pause (keyevent shell).
- **Volume**: M3 slider, 150 ms debounce, `media volume --stream 3 --set N`.
- **File push**: **Push file to /sdcard/Download/** opens a file chooser; success/failure via Snackbar.
- **Logcat**: Last 1000 lines, Clear, filter (client-side), auto-scroll, color by level (V/D/I/W/E).
- **Expert mode**: Raw shell command + **Send**; output in Snackbar.

## Cleanup

On window close, logcat is stopped and the app exits.
