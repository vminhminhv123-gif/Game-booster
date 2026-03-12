# GameBooster - Fake Lag Engine

An advanced Android application that provides selective packet filtering and network lag simulation for gaming purposes. The application uses VPN Service to intercept and manipulate network packets without affecting the actual ping.

## Features

- **VPN-based Packet Filtering**: Uses Android VPN Service to intercept and filter network packets
- **Floating Control Panel**: Three floating buttons for real-time control:
  - **Ngưng (Stop)**: Block incoming packets (download blocking)
  - **Tele (Teleport)**: Block outgoing packets (upload blocking)
  - **Ghost (Ghost Mode)**: Bypass specific packets for free movement
- **Anti-ban Bypass**: Implements packet jitter and humanized movement patterns to avoid detection
- **App Selection**: Choose target application for fake lag simulation
- **Dark Theme UI**: Modern dark theme interface with green accent colors

## Project Structure

```
GameBooster/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/vip/fakelag/
│   │       │   ├── MainActivity.java
│   │       │   ├── GameBoosterVpnService.java
│   │       │   ├── FloatingControlService.java
│   │       │   └── BypassAntiban.java
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml
│   │       │   ├── values/
│   │       │   │   ├── strings.xml
│   │       │   │   ├── colors.xml
│   │       │   │   └── styles.xml
│   │       │   └── drawable/
│   │       │       └── button_background.xml
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Requirements

- Android SDK 21 (API 21) or higher
- Android Studio 2022.1 or later
- Java 11 or higher
- Gradle 8.1.4 or later

## Building

### Using Android Studio

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Connect an Android device or start an emulator
4. Click **Run** → **Run 'app'** or press `Shift + F10`

### Using Command Line

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug

# Run on device
./gradlew runDebug
```

## Installation

1. Build the APK using the steps above
2. Transfer the APK to your Android device or use `adb install`
3. Grant required permissions when prompted:
   - VPN permission
   - Overlay permission (for floating panel)
4. Launch the app from your app drawer

## Usage

### Initial Setup

1. Open GameBooster app
2. Tap **"ACTIVATE BOOST"** button
3. Select target application from the list
4. Grant VPN permission when prompted
5. Grant overlay permission when prompted

### Controlling Fake Lag

Once the engine is active:

1. **Ngưng (Stop)** - Tap to toggle download blocking
   - When active: Blocks incoming packets from server
   - Maintains connection to avoid ping increase

2. **Tele (Teleport)** - Tap to toggle upload blocking
   - When active: Blocks outgoing packets to server
   - Allows free movement without server sync

3. **Ghost (Ghost Mode)** - Tap to toggle packet bypass
   - When active: Bypasses position/item sync packets
   - Enables ghost mode for free exploration

### Deactivating

- Toggle the **"KÍCH HOẠT ENGINE"** switch OFF to stop all services
- Floating panel will disappear
- All packet filtering will cease

## Technical Details

### VPN Service

The `GameBoosterVpnService` class:
- Establishes a VPN interface using Android VPN Service API
- Intercepts all network packets from the target application
- Implements packet filtering based on floating panel states
- Applies anti-ban jitter to avoid detection
- Maintains connection stability with heartbeat packets

### Floating Control Service

The `FloatingControlService` class:
- Creates a draggable floating window overlay
- Provides three control buttons for real-time state changes
- Updates VPN Service filtering based on button states
- Supports drag and drop repositioning

### Anti-ban Bypass

The `BypassAntiban` class:
- Implements packet jitter with random delays (1-10ms)
- Applies burst patterns for natural packet flow
- Humanizes movement coordinates with noise
- Prevents detection of artificial patterns

## Permissions

The application requires the following permissions:

- `INTERNET` - Access network
- `FOREGROUND_SERVICE` - Run services in foreground
- `SYSTEM_ALERT_WINDOW` - Display floating window
- `BIND_VPN_SERVICE` - Bind to VPN Service
- `CHANGE_NETWORK_STATE` - Modify network settings
- `ACCESS_NETWORK_STATE` - Access network information
- `QUERY_ALL_PACKAGES` - Query installed applications

## Security Considerations

- This application intercepts network traffic
- Use only for authorized testing and development
- Ensure compliance with local laws and regulations
- Do not use for unauthorized access or malicious purposes
- Some games may detect and ban accounts using this tool

## Troubleshooting

### VPN Permission Denied
- Ensure device is not already connected to another VPN
- Check if VPN permission is granted in Settings
- Try restarting the application

### Floating Panel Not Appearing
- Verify overlay permission is granted
- Check if FloatingControlService is running
- Restart the application

### Packet Filtering Not Working
- Verify correct app is selected
- Check if VPN Service is running (notification should appear)
- Try toggling buttons again

### High Ping Despite Fake Lag
- Ensure only selective packets are being blocked
- Verify anti-ban jitter is enabled
- Check network connection stability

## Development Notes

### Adding New Features

1. Modify relevant service class
2. Update UI in MainActivity or create new activity
3. Update AndroidManifest.xml if adding new components
4. Test thoroughly on multiple Android versions

### Debugging

Enable logging in classes:
```java
Log.d(TAG, "Debug message");
```

Use Android Studio Logcat to view logs:
```bash
./gradlew installDebug
adb logcat | grep GameBooster
```

## License

This project is provided as-is for educational and authorized testing purposes only.

## Disclaimer

This application is designed for authorized network testing and development purposes. Users are responsible for ensuring compliance with applicable laws and regulations. Unauthorized access to computer systems or networks is illegal. The developers assume no liability for misuse of this application.

## Support

For issues, questions, or suggestions, please refer to the project documentation or contact the development team.
