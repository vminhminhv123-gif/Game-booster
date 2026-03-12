# GameBooster - Build APK Bằng Termux Trên Điện Thoại Android

Hướng dẫn chi tiết để build APK GameBooster trực tiếp trên điện thoại Android bằng Termux.

## 📋 Yêu Cầu Trước Tiên

- **Điện thoại Android**: API 21 hoặc cao hơn
- **Bộ nhớ trống**: Ít nhất 5GB (để cài đặt tools và build)
- **Kết nối Internet**: Ổn định (để download dependencies)
- **Termux**: Ứng dụng terminal emulator cho Android

## 🚀 Bước 1: Cài Đặt Termux

### Cách 1: Từ F-Droid (Khuyến nghị)
1. Cài đặt F-Droid từ `f-droid.org`
2. Mở F-Droid
3. Tìm kiếm "Termux"
4. Cài đặt phiên bản chính thức

### Cách 2: Từ Play Store
1. Mở Google Play Store
2. Tìm kiếm "Termux"
3. Cài đặt (phiên bản chính thức)

### Cách 3: Từ GitHub
1. Truy cập: `github.com/termux/termux-app/releases`
2. Download file `.apk` mới nhất
3. Cài đặt trên điện thoại

## 🔧 Bước 2: Cài Đặt Công Cụ Cần Thiết

Mở Termux và chạy các lệnh sau **lần lượt**:

### Bước 2.1: Cập Nhật Packages
```bash
pkg update
pkg upgrade -y
```
**Thời gian**: ~5-10 phút (tùy tốc độ internet)

### Bước 2.2: Cài Đặt Java Development Kit (JDK)
```bash
pkg install openjdk-17 -y
```
**Thời gian**: ~3-5 phút
**Kích thước**: ~500MB

### Bước 2.3: Cài Đặt Git
```bash
pkg install git -y
```
**Thời gian**: ~1-2 phút

### Bước 2.4: Cài Đặt Android SDK
```bash
pkg install android-sdk -y
```
**Thời gian**: ~5-10 phút
**Kích thước**: ~1-2GB

### Bước 2.5: Cài Đặt Android NDK (Tùy chọn)
```bash
pkg install android-ndk -y
```
**Thời gian**: ~5 phút
**Kích thước**: ~500MB

### Bước 2.6: Cài Đặt Gradle
```bash
pkg install gradle -y
```
**Thời gian**: ~2-3 phút
**Kích thước**: ~100MB

### Xác Nhận Cài Đặt
```bash
java -version
gradle -version
git --version
```

## 📥 Bước 3: Tải Project GameBooster

### Cách 1: Từ Git Repository
```bash
cd ~
git clone https://github.com/your-username/GameBooster.git
cd GameBooster
```

### Cách 2: Từ File ZIP
```bash
# Nếu bạn có file ZIP
cd ~
unzip GameBooster.zip
cd GameBooster
```

### Cách 3: Tạo Project Mới
Nếu bạn muốn copy file từ máy tính:
```bash
# Tạo thư mục
mkdir -p ~/GameBooster
cd ~/GameBooster

# Copy file từ máy tính (dùng scp hoặc file manager)
# Hoặc download từ cloud storage
```

## 🏗️ Bước 4: Cấu Hình Build

### Bước 4.1: Tạo Local Properties
```bash
cd ~/GameBooster
echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties
echo "ndk.dir=$ANDROID_NDK_ROOT" >> local.properties
```

### Bước 4.2: Kiểm Tra Cấu Hình
```bash
cat local.properties
```

## 🔨 Bước 5: Build APK

### Build Debug APK (Nhanh hơn)
```bash
cd ~/GameBooster
./gradlew assembleDebug
```
**Thời gian**: ~15-30 phút (lần đầu), ~5-10 phút (lần sau)
**Kích thước APK**: ~3-5MB

### Build Release APK (Tối ưu hơn)
```bash
cd ~/GameBooster
./gradlew assembleRelease
```
**Thời gian**: ~20-40 phút (lần đầu)
**Kích thước APK**: ~1-2MB (với ProGuard obfuscation)

## 📦 Bước 6: Tìm APK Đã Build

Sau khi build hoàn thành, APK sẽ nằm tại:

### Debug APK:
```bash
~/GameBooster/app/build/outputs/apk/debug/app-debug.apk
```

### Release APK:
```bash
~/GameBooster/app/build/outputs/apk/release/app-release.apk
```

### Kiểm Tra File:
```bash
ls -lh ~/GameBooster/app/build/outputs/apk/debug/
ls -lh ~/GameBooster/app/build/outputs/apk/release/
```

## 💾 Bước 7: Cài Đặt APK

### Cách 1: Từ Termux (Nếu có ADB)
```bash
adb install ~/GameBooster/app/build/outputs/apk/debug/app-debug.apk
```

### Cách 2: Từ File Manager
1. Mở File Manager trên điện thoại
2. Điều hướng đến: `/data/data/com.termux/files/home/GameBooster/app/build/outputs/apk/debug/`
3. Tap vào file `app-debug.apk`
4. Chọn "Install"

### Cách 3: Copy Sang Thư Mục Downloads
```bash
cp ~/GameBooster/app/build/outputs/apk/debug/app-debug.apk ~/storage/downloads/
```
Sau đó mở file từ ứng dụng Downloads

## 🐛 Troubleshooting

### Lỗi: "Permission denied"
```bash
chmod +x ~/GameBooster/gradlew
```

### Lỗi: "Out of memory"
Tăng heap size:
```bash
export _JAVA_OPTIONS="-Xmx512m"
./gradlew assembleDebug
```

### Lỗi: "SDK not found"
```bash
# Cài đặt lại Android SDK
pkg install android-sdk -y

# Hoặc tạo local.properties
echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties
```

### Lỗi: "Gradle sync failed"
```bash
# Clean cache
./gradlew clean

# Rebuild
./gradlew assembleDebug
```

### Lỗi: "Build timeout"
Tăng timeout:
```bash
./gradlew assembleDebug --max-workers=1
```

### Termux Bị Tắt Khi Build
- Giữ Termux trong foreground
- Hoặc cài đặt "Acquire Wakelock" trong Termux settings
- Hoặc chạy trong background: `nohup ./gradlew assembleDebug &`

## ⚡ Tối Ưu Hóa Build

### Build Nhanh Hơn
```bash
# Chỉ build cho architecture cụ thể
./gradlew assembleDebug -PbuildArchitectures=arm64-v8a

# Hoặc
./gradlew assembleDebug --max-workers=1
```

### Giảm Kích Thước APK
```bash
# Build release với ProGuard
./gradlew assembleRelease

# Hoặc tạo APK tách theo architecture
./gradlew bundleRelease
```

## 📊 Kiểm Tra Build

### Xem Log Chi Tiết
```bash
./gradlew assembleDebug --info
```

### Xem Kích Thước APK
```bash
ls -lh ~/GameBooster/app/build/outputs/apk/debug/app-debug.apk
```

### Kiểm Tra APK Content
```bash
cd ~/GameBooster/app/build/outputs/apk/debug/
unzip -l app-debug.apk | head -20
```

## 🔄 Lệnh Hữu Ích

```bash
# Clean build
./gradlew clean

# Build debug
./gradlew assembleDebug

# Build release
./gradlew assembleRelease

# Build và install
./gradlew installDebug

# Run tests
./gradlew test

# View dependencies
./gradlew dependencies

# Check for updates
./gradlew dependencyUpdates

# Build with verbose output
./gradlew assembleDebug -v

# Build with specific Java version
JAVA_HOME=/data/data/com.termux/files/usr ./gradlew assembleDebug
```

## 📝 Tips & Tricks

1. **Giữ Termux Chạy**: Cài đặt "Acquire Wakelock" để điện thoại không sleep
2. **Tăng Tốc Độ**: Dùng WiFi thay vì mobile data
3. **Tiết Kiệm Pin**: Giảm brightness, đóng ứng dụng khác
4. **Giám Sát**: Dùng `top` hoặc `htop` để kiểm tra tài nguyên
5. **Backup**: Copy project sang cloud trước khi build

## 🎯 Quy Trình Build Hoàn Chỉnh (Tóm Tắt)

```bash
# 1. Mở Termux
# 2. Cập nhật packages
pkg update && pkg upgrade -y

# 3. Cài đặt tools
pkg install openjdk-17 git android-sdk gradle -y

# 4. Tải project
cd ~
git clone <repo_url>
cd GameBooster

# 5. Cấu hình
echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties

# 6. Build
./gradlew assembleDebug

# 7. Cài đặt
cp app/build/outputs/apk/debug/app-debug.apk ~/storage/downloads/
```

## ⏱️ Thời Gian Dự Kiến

| Bước | Thời Gian |
|------|-----------|
| Cài Termux | 5 phút |
| Cài JDK | 5 phút |
| Cài Git | 2 phút |
| Cài Android SDK | 10 phút |
| Cài Gradle | 3 phút |
| Tải Project | 2 phút |
| Build Debug (lần 1) | 30 phút |
| Build Debug (lần 2+) | 10 phút |
| **Tổng Cộng** | **~60-90 phút** |

## 📞 Hỗ Trợ

Nếu gặp vấn đề:
1. Kiểm tra log: `./gradlew assembleDebug --info`
2. Xóa cache: `./gradlew clean`
3. Cài lại tools: `pkg install android-sdk -y`
4. Khởi động lại Termux
5. Khởi động lại điện thoại

## 🎉 Hoàn Thành!

Khi build thành công, bạn sẽ thấy:
```
BUILD SUCCESSFUL in XXs
```

APK sẽ nằm tại:
```
app/build/outputs/apk/debug/app-debug.apk
```

Chúc mừng! Bạn đã build thành công APK GameBooster trên điện thoại! 🚀
