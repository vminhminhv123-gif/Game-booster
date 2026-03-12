# GameBooster - Architecture Documentation

## Overview

GameBooster is an Android application that provides selective packet filtering and network lag simulation for gaming purposes. The application uses Android VPN Service to intercept and manipulate network packets without affecting the actual ping.

## System Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    GameBooster App                       │
├─────────────────────────────────────────────────────────┤
│                                                           │
│  ┌──────────────────────────────────────────────────┐   │
│  │            MainActivity (UI Layer)               │   │
│  │  - App Selection Dialog                          │   │
│  │  - VPN Switch Control                            │   │
│  │  - Permission Handling                           │   │
│  └──────────────────────────────────────────────────┘   │
│                         ↓                                 │
│  ┌──────────────────────────────────────────────────┐   │
│  │         Service Management Layer                 │   │
│  │  - Start/Stop Services                           │   │
│  │  - Permission Validation                         │   │
│  │  - State Management                              │   │
│  └──────────────────────────────────────────────────┘   │
│                    ↙              ↘                       │
│  ┌──────────────────────┐  ┌──────────────────────┐    │
│  │  VPN Service         │  │  Floating Control    │    │
│  │  (Packet Filtering)  │  │  Service             │    │
│  │                      │  │  (UI Control)        │    │
│  │ - TUN Interface      │  │                      │    │
│  │ - Packet Processing  │  │ - Floating Buttons   │    │
│  │ - Filtering Logic    │  │ - State Management   │    │
│  │ - Anti-ban Bypass    │  │ - Drag & Drop        │    │
│  └──────────────────────┘  └──────────────────────┘    │
│           ↓                                               │
│  ┌──────────────────────────────────────────────────┐   │
│  │         BypassAntiban Module                     │   │
│  │  - Packet Jitter                                 │   │
│  │  - Burst Pattern                                 │   │
│  │  - Movement Humanization                         │   │
│  │  - Anti-detection Logic                          │   │
│  └──────────────────────────────────────────────────┘   │
│                                                           │
└─────────────────────────────────────────────────────────┘
                         ↓
        ┌────────────────────────────────────┐
        │      Android System Services       │
        │  - VPN Service API                 │
        │  - WindowManager                   │
        │  - PackageManager                  │
        │  - NotificationManager             │
        └────────────────────────────────────┘
                         ↓
        ┌────────────────────────────────────┐
        │      Target Application            │
        │      (Game/App to be filtered)     │
        └────────────────────────────────────┘
```

## Component Details

### 1. MainActivity

**Responsibility**: User interface and permission management

**Key Methods**:
- `onCreate()` - Initialize UI components and load apps
- `checkVpnAndOverlay()` - Validate VPN and overlay permissions
- `startEngine()` - Start VPN and Floating Control services
- `stopAllServices()` - Stop all running services
- `loadLaunchableApps()` - Load installed applications
- `showAppSelector()` - Display app selection dialog
- `styleButton()` / `styleSwitch()` - Apply UI styling
- `onActivityResult()` - Handle permission results

**State Variables**:
- `launchableApps: List<ApplicationInfo>` - List of installed apps
- `selectedPackage: String` - Currently selected app package
- `vpnSwitch: Switch` - VPN toggle switch
- `waitingForPermission: boolean` - Permission waiting flag

**Permissions Handled**:
- VPN Service permission (via VpnService.prepare())
- Overlay permission (via Settings.ACTION_MANAGE_OVERLAY_PERMISSION)

### 2. GameBoosterVpnService

**Responsibility**: Network packet interception and filtering

**Key Methods**:
- `onStartCommand()` - Initialize VPN service
- `runVpn()` - Main packet processing loop
- `processPacket()` - Parse and analyze packets
- `shouldBlockPacket()` - Determine if packet should be blocked
- `isOutgoingPacket()` - Check if packet is outgoing
- `isIncomingPacket()` - Check if packet is incoming
- `createNotification()` - Create foreground notification

**State Variables**:
- `targetPackage: String` - Package to filter
- `vpnInterface: ParcelFileDescriptor` - VPN interface
- `isRunning: AtomicBoolean` - Service running state
- `blockDownload: volatile boolean` - Block incoming packets
- `blockUpload: volatile boolean` - Block outgoing packets
- `ghostMode: volatile boolean` - Bypass specific packets

**Packet Processing**:
1. Read packet from TUN interface
2. Parse IP header (version, protocol, ports)
3. Apply anti-ban jitter
4. Check filtering rules
5. Forward or drop packet
6. Repeat

### 3. FloatingControlService

**Responsibility**: Floating control panel UI and state management

**Key Methods**:
- `onStartCommand()` - Initialize floating service
- `createFloatingView()` - Create floating panel
- `createButton()` - Create control button
- `updateButtonState()` - Update button appearance

**State Variables**:
- `windowManager: WindowManager` - Window manager instance
- `floatingView: LinearLayout` - Floating panel container
- `params: WindowManager.LayoutParams` - Window layout parameters
- `stopButton, teleButton, ghostButton: Button` - Control buttons
- `isDragging: boolean` - Drag state

**Button Functions**:
- **Ngưng (Stop)**: Toggle `GameBoosterVpnService.blockDownload`
- **Tele (Teleport)**: Toggle `GameBoosterVpnService.blockUpload`
- **Ghost (Ghost Mode)**: Toggle `GameBoosterVpnService.ghostMode`

### 4. BypassAntiban

**Responsibility**: Anti-detection and anti-ban mechanisms

**Key Methods**:
- `applyPacketJitter()` - Apply random delay to packets
- `humanizeMovement()` - Add noise to coordinates
- `setEnabled()` - Enable/disable jitter
- `updateBasePing()` - Update base ping value
- `reinitializeJitterTable()` - Regenerate jitter table

**State Variables**:
- `jitterTable: int[]` - Random jitter values (1-10ms)
- `burstLengths: int[]` - Burst pattern lengths
- `jitterEnabled: boolean` - Jitter enabled flag
- `basePing: int` - Base ping value

**Anti-detection Logic**:
- Minimal jitter (1-10ms) to avoid increasing actual ping
- Burst patterns for natural packet flow
- Random noise in coordinates
- Periodic re-initialization of jitter table

## Data Flow

### Engine Activation Flow

```
User taps "ACTIVATE BOOST"
    ↓
Check if app selected
    ↓
checkVpnAndOverlay()
    ↓
Request VPN permission
    ↓
Request Overlay permission
    ↓
startEngine()
    ↓
Start GameBoosterVpnService
Start FloatingControlService
    ↓
Engine Active
```

### Packet Filtering Flow

```
Incoming Packet
    ↓
GameBoosterVpnService.runVpn()
    ↓
Read from TUN interface
    ↓
processPacket() - Parse IP header
    ↓
shouldBlockPacket() - Check filtering rules
    ├─ blockDownload? (incoming packets)
    ├─ blockUpload? (outgoing packets)
    └─ ghostMode? (bypass specific packets)
    ↓
applyPacketJitter() - Add anti-ban jitter
    ↓
Forward or Drop packet
    ↓
Write to TUN interface
```

### Floating Panel Control Flow

```
User taps Button
    ↓
Button listener triggered
    ↓
Toggle state variable
    ├─ blockDownload
    ├─ blockUpload
    └─ ghostMode
    ↓
Update button appearance
    ↓
VPN Service reads state
    ↓
Apply filtering in packet loop
```

## Threading Model

| Component | Thread | Purpose |
|-----------|--------|---------|
| MainActivity | Main UI Thread | User interface |
| GameBoosterVpnService | Background Thread | Packet processing |
| FloatingControlService | Main UI Thread | Floating panel |
| BypassAntiban | VPN Service Thread | Jitter application |

**Thread Safety**:
- State variables use `volatile` keyword for visibility
- `AtomicBoolean` for thread-safe boolean operations
- No shared mutable state between threads

## Permission Model

| Permission | Purpose | Required | When |
|-----------|---------|----------|------|
| INTERNET | Network access | Yes | Always |
| FOREGROUND_SERVICE | Background services | Yes | API ≥ 26 |
| SYSTEM_ALERT_WINDOW | Floating window | Yes | Floating panel |
| BIND_VPN_SERVICE | VPN service | Yes | VPN filtering |
| CHANGE_NETWORK_STATE | Network modification | Yes | VPN setup |
| ACCESS_NETWORK_STATE | Network info | Yes | Network access |
| QUERY_ALL_PACKAGES | List apps | Yes | App selection |

## Notification System

**VPN Service Notification**:
- Title: "GameBooster VPN"
- Text: "Fake lag engine running..."
- Priority: Low
- Ongoing: Yes (cannot be dismissed)

**Floating Control Notification**:
- Title: "GameBooster Control"
- Text: "Floating control panel active..."
- Priority: Low
- Ongoing: Yes

## Resource Management

### Memory
- Packet buffer: 32KB per read
- Jitter table: 100 integers (~400 bytes)
- Burst lengths: 10 integers (~40 bytes)
- Minimal UI overhead

### CPU
- Packet processing loop: Continuous
- Jitter application: Minimal (1-10ms sleep)
- Floating panel: Event-driven

### Network
- VPN interface: One per service
- Packet forwarding: Real-time
- No buffering (stream-based)

## Error Handling

**VPN Service Errors**:
- IOException during packet read/write → Log and continue
- VPN interface null → Return early
- Thread interruption → Set running flag to false

**Permission Errors**:
- VPN permission denied → Show toast, disable switch
- Overlay permission denied → Show toast, disable floating panel
- App selection required → Show toast, prompt selection

**UI Errors**:
- App list empty → Show "No apps available" toast
- Service start failure → Log error, show notification

## Performance Considerations

1. **Packet Processing**: Optimized for minimal latency
2. **Jitter Application**: Minimal delay (1-10ms) to avoid ping increase
3. **Memory Usage**: Efficient buffer reuse
4. **CPU Usage**: Event-driven architecture
5. **Battery Usage**: Minimal impact (foreground service)

## Security Considerations

1. **Code Obfuscation**: ProGuard rules applied
2. **Permission Validation**: Runtime permission checks
3. **Packet Integrity**: No modification of packet data
4. **User Data**: No collection or transmission
5. **Anti-ban**: Jitter and humanization to avoid detection

## Future Enhancements

1. **Advanced Packet Detection**: Protocol-specific filtering
2. **Configurable Jitter**: User-adjustable delay levels
3. **Multiple Profiles**: Save/load filter configurations
4. **Statistics**: Packet count, bandwidth usage
5. **Advanced Anti-detection**: Machine learning-based patterns
6. **Custom Rules**: User-defined filtering rules
7. **Packet Logging**: Debug and analysis tools

## Compliance Notes

- Application requires VPN permission (system-level)
- Floating window requires overlay permission
- Package query requires QUERY_ALL_PACKAGES permission
- All permissions requested in AndroidManifest.xml
- Runtime permission handling for API ≥ 23

## Testing Strategy

| Component | Test Type | Focus |
|-----------|-----------|-------|
| MainActivity | Unit/UI | Permission handling, app selection |
| VPN Service | Integration | Packet filtering, state management |
| Floating Service | UI | Button functionality, dragging |
| BypassAntiban | Unit | Jitter application, humanization |

## Deployment

- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 34 (Android 14)
- **Supported Architectures**: armeabi-v7a, arm64-v8a
- **APK Size**: ~2-3 MB (debug), ~1-2 MB (release with ProGuard)
