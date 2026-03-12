# 🚀 Quick Commands - Lệnh Nhanh Cho Termux

Danh sách các lệnh hữu ích để build APK GameBooster trên Termux.

## 📱 Cài Đặt Công Cụ (Chỉ Chạy Lần Đầu)

```bash
# Cập nhật packages
pkg update && pkg upgrade -y

# Cài tất cả công cụ cần thiết
pkg install openjdk-17 git android-sdk gradle -y

# Hoặc chạy script tự động
bash setup.sh
```

## 🏗️ Build APK

```bash
# Build Debug APK (nhanh)
./gradlew assembleDebug

# Build Release APK (tối ưu)
./gradlew assembleRelease

# Build với script tự động
bash build.sh

# Clean build (xóa cache)
./gradlew clean assembleDebug

# Build với verbose output
./gradlew assembleDebug --info
```

## 📂 Tìm APK

```bash
# Debug APK
ls -lh app/build/outputs/apk/debug/app-debug.apk

# Release APK
ls -lh app/build/outputs/apk/release/app-release.apk

# Xem tất cả APK
find . -name "*.apk" -type f
```

## 💾 Copy APK Sang Downloads

```bash
# Copy Debug APK
cp app/build/outputs/apk/debug/app-debug.apk ~/storage/downloads/

# Copy Release APK
cp app/build/outputs/apk/release/app-release.apk ~/storage/downloads/

# Xem file đã copy
ls -lh ~/storage/downloads/*.apk
```

## 🔧 Cấu Hình

```bash
# Tạo local.properties
echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties

# Xem local.properties
cat local.properties

# Tạo gradle.properties
echo "org.gradle.jvmargs=-Xmx512m" > gradle.properties
```

## 🔍 Kiểm Tra

```bash
# Kiểm tra Java
java -version

# Kiểm tra Gradle
gradle -version

# Kiểm tra Git
git --version

# Kiểm tra Android SDK
echo $ANDROID_SDK_ROOT

# Kiểm tra bộ nhớ
df -h

# Kiểm tra RAM
free -h
```

## 🧹 Dọn Dẹp

```bash
# Xóa build cache
./gradlew clean

# Xóa tất cả build files
rm -rf app/build

# Xóa gradle cache
rm -rf ~/.gradle

# Xóa tất cả APK
find . -name "*.apk" -delete
```

## 📊 Thông Tin Build

```bash
# Xem dependencies
./gradlew dependencies

# Xem tasks
./gradlew tasks

# Xem project info
./gradlew projects

# Xem build info
./gradlew buildEnvironment
```

## 🔄 Cập Nhật

```bash
# Cập nhật Gradle wrapper
./gradlew wrapper --gradle-version 8.1.4

# Cập nhật dependencies
./gradlew dependencyUpdates

# Cập nhật packages Termux
pkg update && pkg upgrade -y
```

## 🆘 Troubleshooting

```bash
# Xem log chi tiết
./gradlew assembleDebug --info

# Xem log stack trace
./gradlew assembleDebug --stacktrace

# Xem tất cả logs
./gradlew assembleDebug --debug

# Tăng heap size
export _JAVA_OPTIONS="-Xmx512m"

# Giảm workers
./gradlew assembleDebug --max-workers=1

# Xóa lock file
rm -rf ~/.gradle/wrapper/dists
```

## 🎯 Workflow Hoàn Chỉnh

```bash
# 1. Cập nhật packages
pkg update && pkg upgrade -y

# 2. Cài công cụ (nếu chưa cài)
pkg install openjdk-17 git android-sdk gradle -y

# 3. Vào thư mục project
cd ~/GameBooster

# 4. Cấu hình
echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties

# 5. Clean build
./gradlew clean

# 6. Build APK
./gradlew assembleDebug

# 7. Copy sang Downloads
cp app/build/outputs/apk/debug/app-debug.apk ~/storage/downloads/

# 8. Xem file
ls -lh ~/storage/downloads/app-debug.apk
```

## 💡 Tips

```bash
# Chạy lệnh trong background
nohup ./gradlew assembleDebug > build.log 2>&1 &

# Xem log real-time
tail -f build.log

# Chạy lệnh với timeout
timeout 3600 ./gradlew assembleDebug

# Chạy lệnh và lưu output
./gradlew assembleDebug 2>&1 | tee build-output.txt

# Chạy nhiều lệnh liên tiếp
./gradlew clean && ./gradlew assembleDebug && echo "Done!"
```

## 📝 Aliases (Tùy Chọn)

Thêm vào `~/.bashrc` để dùng lệnh ngắn:

```bash
alias gb-setup='bash setup.sh'
alias gb-build='bash build.sh'
alias gb-clean='./gradlew clean'
alias gb-debug='./gradlew assembleDebug'
alias gb-release='./gradlew assembleRelease'
alias gb-copy='cp app/build/outputs/apk/debug/app-debug.apk ~/storage/downloads/'
```

Sau đó reload:
```bash
source ~/.bashrc
```

Dùng:
```bash
gb-setup    # Cài công cụ
gb-build    # Build APK
gb-debug    # Build debug
gb-release  # Build release
gb-copy     # Copy sang Downloads
```

---

**Xem hướng dẫn chi tiết**: `TERMUX_BUILD_GUIDE.md` hoặc `QUICK_START_TERMUX.md`
