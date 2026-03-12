# GameBooster - Building & Installation Guide

## Prerequisites

Before building GameBooster, ensure you have the following installed:

- **Android Studio**: Version 2022.1 or later
- **Android SDK**: API level 34 (or higher)
- **Java Development Kit (JDK)**: Version 11 or higher
- **Gradle**: Version 8.1.4 (included with Android Studio)
- **Android Device or Emulator**: Running Android 5.0 (API 21) or higher

## Building with Android Studio

### Step 1: Open the Project

1. Launch Android Studio
2. Click **File** → **Open**
3. Navigate to `/home/ubuntu/GameBooster`
4. Click **OK**

### Step 2: Sync Gradle

1. Wait for Android Studio to load the project
2. If Gradle sync doesn't start automatically, click **File** → **Sync Now**
3. Wait for the sync to complete (may take 2-5 minutes)

### Step 3: Build the APK

#### Debug Build (for testing)
1. Click **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**
2. Wait for the build to complete
3. A notification will appear with the APK location

#### Release Build (for distribution)
1. Click **Build** → **Build Bundle(s) / APK(s)** → **Build Bundle(s)**
2. Or manually:
   - Click **Build** → **Generate Signed Bundle / APK**
   - Select **APK**
   - Choose or create a keystore
   - Fill in keystore details
   - Select **release** build type
   - Click **Finish**

### Step 4: Install on Device

#### Using Android Studio
1. Connect your Android device via USB
2. Enable Developer Mode on the device
3. Click **Run** → **Run 'app'** (or press `Shift + F10`)
4. Select your device from the list
5. Click **OK**

#### Using Command Line (adb)
```bash
# Connect device via USB
adb devices

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Or for release APK
adb install app/build/outputs/apk/release/app-release.apk
```

## Building from Command Line

### Using Gradle Wrapper

```bash
# Navigate to project directory
cd /home/ubuntu/GameBooster

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Build and install on connected device
./gradlew installDebug

# Run on device
./gradlew runDebug

# Clean build
./gradlew clean

# Build with verbose output
./gradlew assembleDebug --info
```

### Output Locations

- **Debug APK**: `app/build/outputs/apk/debug/app-debug.apk`
- **Release APK**: `app/build/outputs/apk/release/app-release.apk`

## Installation on Device

### Method 1: Direct APK Installation

1. Transfer APK to device via USB or cloud
2. Open file manager on device
3. Navigate to APK file
4. Tap to install
5. Grant permissions when prompted

### Method 2: ADB Installation

```bash
# Install from computer
adb install path/to/app-release.apk

# Uninstall
adb uninstall com.vip.fakelag

# Reinstall (uninstall then install)
adb install -r path/to/app-release.apk
```

### Method 3: Android Studio Installation

1. Connect device via USB
2. Open project in Android Studio
3. Click **Run** → **Run 'app'**
4. Select device
5. Click **OK**

## Granting Permissions

After installation, the app requires the following permissions:

1. **VPN Permission**
   - Tap the switch to activate engine
   - System will prompt for VPN permission
   - Tap **OK** to grant

2. **Overlay Permission** (for floating panel)
   - System will open Settings app
   - Navigate to **Apps** → **Special app access** → **Display over other apps**
   - Find **GameBooster** and enable the toggle

3. **Other Permissions**
   - Internet access (automatic)
   - Network state access (automatic)

## Troubleshooting

### Build Errors

#### "SDK location not found"
```bash
# Create local.properties file
echo "sdk.dir=/path/to/android/sdk" > local.properties
```

#### "Gradle sync failed"
1. Click **File** → **Sync Now**
2. If still fails, click **File** → **Invalidate Caches** → **Invalidate and Restart**
3. After restart, sync again

#### "Compilation failed"
```bash
# Clean and rebuild
./gradlew clean assembleDebug
```

### Installation Issues

#### "App not installed"
- Ensure device has enough storage space
- Try uninstalling previous version: `adb uninstall com.vip.fakelag`
- Try installing again: `adb install app-debug.apk`

#### "Permission denied"
- Enable Developer Mode on device
- Enable USB Debugging
- Reconnect device and try again

### Runtime Issues

#### "VPN permission denied"
- Go to **Settings** → **Apps** → **Special app access** → **VPN**
- Find **GameBooster** and tap it
- Tap **Allow**

#### "Floating panel not appearing"
- Go to **Settings** → **Apps** → **Special app access** → **Display over other apps**
- Find **GameBooster** and enable the toggle

#### "App crashes on startup"
- Check logcat: `adb logcat | grep GameBooster`
- Ensure all permissions are granted
- Try uninstalling and reinstalling

## Building for Release

### Code Obfuscation

The `proguard-rules.pro` file is configured to:
- Obfuscate class and method names
- Remove logging statements
- Optimize code
- Preserve critical classes

### Signing APK

For release builds, you need to sign the APK:

1. In Android Studio: **Build** → **Generate Signed Bundle / APK**
2. Create or select a keystore
3. Fill in keystore password and key details
4. Select **release** build type
5. Click **Finish**

### APK Size Optimization

The build is configured with:
- `minifyEnabled true` - Code shrinking
- `shrinkResources true` - Resource shrinking
- ProGuard rules for optimization

## Continuous Integration

For CI/CD pipelines, use:

```bash
# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test

# Generate reports
./gradlew lint

# Build and output to specific location
./gradlew assembleRelease -PoutputDir=/path/to/output
```

## Additional Resources

- [Android Developers - Build Your App](https://developer.android.com/studio/build)
- [Gradle Documentation](https://docs.gradle.org/)
- [Android VPN Service](https://developer.android.com/reference/android/net/VpnService)
- [Android Permissions](https://developer.android.com/guide/topics/permissions/overview)

## Support

For build issues or questions, refer to:
- Android Studio documentation
- Gradle troubleshooting guide
- Android developer forums
