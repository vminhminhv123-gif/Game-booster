# 📥 GameBooster - Download & Setup

## 📦 File Tải Xuống

Bạn đã tải xuống dự án GameBooster. Dưới đây là hướng dẫn để bắt đầu.

### File Được Cung Cấp

- **GameBooster.zip** (40KB) - Toàn bộ dự án (định dạng ZIP)
- **GameBooster.tar.gz** (26KB) - Toàn bộ dự án (định dạng TAR.GZ)

Chọn một trong hai file trên để tải xuống.

## 🚀 Bước 1: Giải Nén File

### Trên Windows
1. Chuột phải vào file `GameBooster.zip`
2. Chọn "Extract All"
3. Chọn nơi lưu

### Trên Mac/Linux
```bash
unzip GameBooster.zip
# hoặc
tar -xzf GameBooster.tar.gz
```

### Trên Android (Termux)
```bash
cd ~
unzip GameBooster.zip
# hoặc
tar -xzf GameBooster.tar.gz
```

## 📂 Cấu Trúc Thư Mục

```
GameBooster/
├── app/
│   ├── src/main/
│   │   ├── java/com/vip/fakelag/
│   │   │   ├── MainActivity.java
│   │   │   ├── GameBoosterVpnService.java
│   │   │   ├── FloatingControlService.java
│   │   │   └── BypassAntiban.java
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   ├── values/
│   │   │   └── drawable/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
├── setup.sh
├── build.sh
├── README.md
├── QUICK_START_TERMUX.md
├── TERMUX_BUILD_GUIDE.md
├── QUICK_COMMANDS.md
├── INSTALL_GUIDE.md
├── BUILDING.md
├── ARCHITECTURE.md
├── PROJECT_SUMMARY.md
├── INDEX.md
└── todo.md
```

## 📖 Hướng Dẫn Tiếp Theo

### Nếu Bạn Chỉ Có Điện Thoại (Termux)

1. **Đọc**: `QUICK_START_TERMUX.md` (5 phút)
   - Cài Termux
   - Cài công cụ
   - Build APK

2. **Hoặc đọc**: `TERMUX_BUILD_GUIDE.md` (chi tiết)
   - Hướng dẫn từng bước
   - Troubleshooting
   - Tips & tricks

3. **Sau đó**: `INSTALL_GUIDE.md`
   - Cài đặt APK trên điện thoại

### Nếu Bạn Có Máy Tính (Android Studio)

1. **Đọc**: `BUILDING.md`
   - Mở trong Android Studio
   - Build APK
   - Troubleshooting

2. **Sau đó**: `INSTALL_GUIDE.md`
   - Cài đặt APK trên điện thoại

## 🎯 Quy Trình Nhanh Nhất

### Trên Termux (Điện Thoại)

```bash
# 1. Giải nén
cd ~
unzip GameBooster.zip
cd GameBooster

# 2. Cài công cụ
bash setup.sh

# 3. Build APK
bash build.sh

# 4. Cài đặt
# Copy file APK sang Downloads rồi tap để cài
```

### Trên Android Studio (Máy Tính)

```bash
# 1. Giải nén
unzip GameBooster.zip

# 2. Mở trong Android Studio
# File → Open → GameBooster

# 3. Build APK
# Build → Build APK(s)

# 4. Cài đặt
# Run → Run 'app'
```

## 📋 Danh Sách File Quan Trọng

| File | Mục Đích |
|------|---------|
| QUICK_START_TERMUX.md | Hướng dẫn 5 phút (Termux) |
| TERMUX_BUILD_GUIDE.md | Hướng dẫn chi tiết (Termux) |
| BUILDING.md | Hướng dẫn build (máy tính) |
| INSTALL_GUIDE.md | Cài đặt APK |
| QUICK_COMMANDS.md | Danh sách lệnh |
| INDEX.md | Danh sách tài liệu |
| setup.sh | Script cài công cụ |
| build.sh | Script build APK |

## ✅ Kiểm Tra Cấu Trúc

Sau khi giải nén, kiểm tra xem các file sau có tồn tại không:

```bash
# Trên Termux
cd GameBooster
ls -la app/src/main/java/com/vip/fakelag/
ls -la app/src/main/res/
ls -la *.md
```

Bạn sẽ thấy:
- 4 Java files (.java)
- 5 XML files (.xml)
- 10+ Markdown files (.md)

## 🚀 Bắt Đầu Ngay

1. **Chọn hướng dẫn phù hợp** (Termux hoặc Android Studio)
2. **Làm theo từng bước**
3. **Build APK**
4. **Cài đặt trên điện thoại**
5. **Sử dụng ứng dụng**

## 📞 Hỗ Trợ

Nếu gặp vấn đề:

1. Xem **Troubleshooting** trong hướng dẫn tương ứng
2. Xem **QUICK_COMMANDS.md** để tìm lệnh giải quyết
3. Đọc **ARCHITECTURE.md** để hiểu cấu trúc

## 📝 Ghi Chú

- **Tất cả hướng dẫn**: Tiếng Việt
- **Hỗ trợ**: Android 5.0+ (API 21+)
- **Build system**: Gradle 8.1.4
- **Ngôn ngữ**: Java
- **Kích thước APK**: 1-3 MB

## 🎉 Hoàn Thành!

Khi bạn đã cài đặt thành công, bạn sẽ thấy icon **GameBooster** trên điện thoại.

Chúc mừng! 🚀

---

**Phiên bản**: 1.0
**Ngày tạo**: 2026-03-09
**Trạng thái**: Sẵn sàng build
