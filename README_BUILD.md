# 🚀 GameBooster - Build APK Guide

Dự án GameBooster đã sẵn sàng để build APK. Chọn một trong các cách dưới đây:

## ✅ Cách 1: Build Trên Điện Thoại (Termux) - KHUYẾN NGHỊ

### Ưu Điểm
- Không cần máy tính
- Chỉ cần điện thoại Android
- Hướng dẫn chi tiết bằng Tiếng Việt
- Scripts tự động hóa

### Hướng Dẫn
1. Đọc: `QUICK_START_TERMUX.md` (5 phút)
2. Hoặc: `TERMUX_BUILD_GUIDE.md` (chi tiết)

### Quy Trình
```bash
# 1. Cài Termux từ F-Droid
# 2. Mở Termux
# 3. Chạy lệnh:
cd ~
unzip GameBooster.zip
cd GameBooster
bash setup.sh
bash build.sh
```

---

## ✅ Cách 2: Build Trên Máy Tính (Android Studio)

### Ưu Điểm
- Giao diện đẹp
- Dễ debug
- Hỗ trợ tốt

### Yêu Cầu
- Android Studio 2022.1+
- Android SDK API 34+
- Java 11+

### Hướng Dẫn
1. Đọc: `BUILDING.md`
2. Mở Android Studio
3. File → Open → GameBooster
4. Build → Build APK(s)

---

## ✅ Cách 3: Build Từ Command Line (Máy Tính)

### Yêu Cầu
- Java 11+
- Gradle 8.1.4+
- Android SDK

### Hướng Dẫn
```bash
# 1. Giải nén
unzip GameBooster.zip
cd GameBooster

# 2. Tạo local.properties
echo "sdk.dir=/path/to/android-sdk" > local.properties

# 3. Build
./gradlew assembleDebug
```

---

## 📦 File APK Output

Sau khi build thành công, APK sẽ nằm tại:

**Debug APK:**
```
app/build/outputs/apk/debug/app-debug.apk
```

**Release APK:**
```
app/build/outputs/apk/release/app-release.apk
```

---

## 📋 Danh Sách Hướng Dẫn

| Hướng Dẫn | Mục Đích |
|-----------|---------|
| QUICK_START_TERMUX.md | Build nhanh trên Termux (5 phút) |
| TERMUX_BUILD_GUIDE.md | Build chi tiết trên Termux |
| BUILDING.md | Build trên máy tính |
| INSTALL_GUIDE.md | Cài đặt APK |
| QUICK_COMMANDS.md | Danh sách lệnh |

---

## 🎯 Khuyến Nghị

**Nếu chỉ có điện thoại**: Dùng **Cách 1 (Termux)**
- Đơn giản nhất
- Không cần máy tính
- Hướng dẫn chi tiết

**Nếu có máy tính**: Dùng **Cách 2 (Android Studio)**
- Giao diện tốt
- Dễ debug
- Hỗ trợ tốt

---

## ✨ Hoàn Thành!

Khi build thành công, bạn sẽ thấy:
```
BUILD SUCCESSFUL in XXs
```

Sau đó cài đặt APK theo hướng dẫn trong `INSTALL_GUIDE.md`.

---

**Phiên bản**: 1.0
**Ngày tạo**: 2026-03-09
**Trạng thái**: Sẵn sàng build
