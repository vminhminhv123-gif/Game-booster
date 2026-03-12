# 🚀 Quick Start - Build APK Trên Termux (5 Phút)

Hướng dẫn nhanh nhất để build APK GameBooster trên điện thoại Android.

## 📱 Bước 1: Cài Termux (2 phút)

**Từ F-Droid** (khuyến nghị):
1. Cài F-Droid: `f-droid.org`
2. Tìm "Termux" → Cài đặt

**Hoặc từ Play Store**:
1. Mở Play Store
2. Tìm "Termux" → Cài đặt

## ⚙️ Bước 2: Cài Công Cụ (10 phút)

Mở Termux, copy-paste từng lệnh này:

```bash
pkg update && pkg upgrade -y
```

```bash
pkg install openjdk-17 git android-sdk gradle -y
```

Chờ đến khi hoàn thành (sẽ hỏi "Continue?" → gõ `y` → Enter)

## 📥 Bước 3: Tải Project (2 phút)

```bash
cd ~
git clone https://github.com/your-username/GameBooster.git
cd GameBooster
```

Hoặc nếu bạn có file ZIP:
```bash
cd ~
unzip GameBooster.zip
cd GameBooster
```

## 🔧 Bước 4: Cấu Hình (1 phút)

```bash
echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties
```

## 🏗️ Bước 5: Build APK (15-30 phút)

```bash
./gradlew assembleDebug
```

**Chờ đến khi thấy:**
```
BUILD SUCCESSFUL
```

## 📦 Bước 6: Lấy APK (1 phút)

```bash
cp app/build/outputs/apk/debug/app-debug.apk ~/storage/downloads/
```

Hoặc xem đường dẫn:
```bash
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

## 💾 Bước 7: Cài Đặt APK (1 phút)

Mở File Manager → Downloads → Tap `app-debug.apk` → Install

## ✅ Xong!

Ứng dụng GameBooster đã được cài đặt trên điện thoại của bạn!

---

## 🆘 Nếu Có Lỗi

### Lỗi "Permission denied"
```bash
chmod +x ./gradlew
```

### Lỗi "Out of memory"
```bash
export _JAVA_OPTIONS="-Xmx512m"
./gradlew assembleDebug
```

### Lỗi "SDK not found"
```bash
pkg install android-sdk -y
echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties
```

### Build Bị Tắt
- Cài "Acquire Wakelock" trong Termux settings
- Hoặc giữ Termux mở

---

## 📚 Hướng Dẫn Chi Tiết

Xem `TERMUX_BUILD_GUIDE.md` để có hướng dẫn đầy đủ hơn.

**Chúc bạn thành công! 🎉**
