# GameBooster - Hướng Dẫn Cài Đặt APK

Hướng dẫn chi tiết để cài đặt APK GameBooster trên điện thoại Android.

## 📋 Yêu Cầu

- **Điện thoại Android**: API 21 (Android 5.0) hoặc cao hơn
- **Bộ nhớ trống**: Ít nhất 50MB
- **File APK**: `app-debug.apk` hoặc `app-release.apk`

## 🔓 Bước 1: Bật "Cài Đặt Từ Nguồn Không Xác Định"

Vì APK không được ký bằng chứng chỉ chính thức, bạn cần bật tùy chọn này:

### Android 12 trở lên:
1. Mở **Settings** (Cài đặt)
2. Đi tới **Apps** (Ứng dụng)
3. Chọn **Special app access** (Quyền truy cập đặc biệt)
4. Tap **Install unknown apps** (Cài đặt ứng dụng không xác định)
5. Chọn ứng dụng file manager (VD: Files, Downloads, etc.)
6. Bật **Allow from this source** (Cho phép từ nguồn này)

### Android 8-11:
1. Mở **Settings** (Cài đặt)
2. Đi tới **Security** (Bảo mật)
3. Bật **Unknown sources** (Nguồn không xác định)

### Android 5-7:
1. Mở **Settings** (Cài đặt)
2. Đi tới **Security** (Bảo mật)
3. Bật **Unknown sources** (Nguồn không xác định)

## 📥 Bước 2: Tìm File APK

### Cách 1: Từ Termux (Nếu bạn build trên Termux)
```bash
# File sẽ ở đây
~/storage/downloads/app-debug.apk
```

### Cách 2: Từ File Manager
1. Mở **File Manager** (Quản lý tệp)
2. Điều hướng tới **Downloads** (Tải xuống)
3. Tìm file `app-debug.apk` hoặc `app-release.apk`

### Cách 3: Từ Cloud Storage
1. Nếu bạn upload lên Google Drive, OneDrive, etc.
2. Download file từ ứng dụng cloud
3. File sẽ được lưu vào Downloads

## 💾 Bước 3: Cài Đặt APK

### Cách 1: Từ File Manager (Dễ nhất)
1. Mở **File Manager**
2. Điều hướng tới **Downloads**
3. Tap vào file `app-debug.apk`
4. Chọn **Install** (Cài đặt)
5. Chờ cài đặt hoàn thành
6. Chọn **Open** (Mở) hoặc **Done** (Xong)

### Cách 2: Từ Termux (Nếu có ADB)
```bash
adb install ~/storage/downloads/app-debug.apk
```

### Cách 3: Từ Notification
1. Nếu file được download từ trình duyệt
2. Mở **Notification** (Thông báo)
3. Tap vào file APK
4. Chọn **Install**

## ✅ Bước 4: Cấp Quyền

Sau khi cài đặt, ứng dụng sẽ yêu cầu các quyền sau:

### VPN Permission (Quyền VPN)
- **Khi**: Bạn bật switch "KÍCH HOẠT ENGINE"
- **Làm gì**: Cho phép ứng dụng tạo VPN connection
- **Cách cấp**: Tap **OK** khi được hỏi

### Overlay Permission (Quyền Hiển Thị Trên Các Ứng Dụng Khác)
- **Khi**: Khi bạn bật engine
- **Làm gì**: Cho phép floating panel xuất hiện
- **Cách cấp**:
  1. Mở **Settings** → **Apps**
  2. Chọn **Special app access** → **Display over other apps**
  3. Tìm **GameBooster**
  4. Bật toggle

### Internet Permission (Quyền Internet)
- **Tự động**: Được cấp khi cài đặt
- **Làm gì**: Cho phép ứng dụng truy cập mạng

## 🚀 Bước 5: Chạy Ứng Dụng

### Từ App Drawer
1. Mở **App Drawer** (Danh sách ứng dụng)
2. Tìm **GameBooster**
3. Tap để mở

### Từ Home Screen
1. Nếu có shortcut trên home screen
2. Tap vào icon **GameBooster**

### Từ Settings
1. Mở **Settings** → **Apps**
2. Tìm **GameBooster**
3. Tap **Open** (Mở)

## 🎮 Bước 6: Sử Dụng Ứng Dụng

### Bước Đầu Tiên
1. Mở ứng dụng GameBooster
2. Tap nút **"🚀 ACTIVATE BOOST"**
3. Chọn ứng dụng game từ danh sách
4. Cấp quyền VPN khi được hỏi
5. Cấp quyền Overlay khi được hỏi

### Kiểm Soát Fake Lag
1. Khi engine bật, floating panel sẽ xuất hiện
2. Tap **"Ngưng"** để chặn download
3. Tap **"Tele"** để chặn upload
4. Tap **"Ghost"** để bỏ qua packet

### Tắt Engine
1. Tap switch **"KÍCH HOẠT ENGINE"** để tắt
2. Hoặc tap nút **"Ngưng"** trên floating panel

## 🆘 Troubleshooting

### Lỗi: "App not installed"
**Nguyên nhân**: Không đủ bộ nhớ hoặc file APK bị hỏng

**Giải pháp**:
1. Xóa một số ứng dụng để giải phóng bộ nhớ
2. Download lại file APK
3. Khởi động lại điện thoại
4. Cài đặt lại

### Lỗi: "Parse error"
**Nguyên nhân**: File APK bị hỏng hoặc không tương thích

**Giải pháp**:
1. Download lại file APK
2. Kiểm tra phiên bản Android của bạn (phải ≥ API 21)
3. Thử file APK khác (debug hoặc release)

### Lỗi: "Permission denied"
**Nguyên nhân**: Chưa bật "Unknown sources"

**Giải pháp**:
1. Mở Settings → Security
2. Bật "Unknown sources"
3. Cài đặt lại APK

### Lỗi: "Installation blocked"
**Nguyên nhân**: Antivirus hoặc Google Play Protect chặn

**Giải pháp**:
1. Tắm tạm Google Play Protect
2. Hoặc tắm antivirus
3. Cài đặt lại APK
4. Bật lại bảo vệ

### Ứng Dụng Không Mở
**Nguyên nhân**: Lỗi cài đặt hoặc thiếu quyền

**Giải pháp**:
1. Xóa ứng dụng: Settings → Apps → GameBooster → Uninstall
2. Khởi động lại điện thoại
3. Cài đặt lại APK
4. Cấp tất cả quyền được yêu cầu

### VPN Permission Bị Từ Chối
**Nguyên nhân**: Điện thoại đã kết nối VPN khác

**Giải pháp**:
1. Ngắt kết nối VPN hiện tại
2. Mở Settings → VPN
3. Xóa tất cả VPN connections
4. Thử lại ứng dụng

### Floating Panel Không Xuất Hiện
**Nguyên nhân**: Chưa cấp quyền Overlay

**Giải pháp**:
1. Mở Settings → Apps → Special app access → Display over other apps
2. Tìm GameBooster
3. Bật toggle
4. Khởi động lại ứng dụng

## 📊 Kiểm Tra Cài Đặt

### Xác Nhận Cài Đặt Thành Công
1. Mở **Settings** → **Apps**
2. Tìm **GameBooster**
3. Xem **App info**:
   - Version: 1.0
   - Package: com.vip.fakelag
   - Size: ~2-5MB

### Xem Quyền Được Cấp
1. Mở **Settings** → **Apps** → **GameBooster**
2. Tap **Permissions**
3. Xem danh sách quyền

## 🔄 Cập Nhật & Gỡ Cài Đặt

### Cập Nhật Ứng Dụng
1. Build APK mới từ source
2. Xóa ứng dụng cũ
3. Cài đặt APK mới

### Gỡ Cài Đặt
1. Mở **Settings** → **Apps**
2. Tìm **GameBooster**
3. Tap **Uninstall** (Gỡ cài đặt)
4. Xác nhận

## 📝 Lưu Ý Quan Trọng

1. **Chỉ cài từ nguồn tin cậy**: Chỉ cài APK từ nguồn chính thức
2. **Backup dữ liệu**: Trước khi cài, backup dữ liệu quan trọng
3. **Kiểm tra phiên bản Android**: Phải ≥ Android 5.0 (API 21)
4. **Bộ nhớ đủ**: Cần ít nhất 50MB bộ nhớ trống
5. **Khởi động lại**: Nếu gặp vấn đề, hãy khởi động lại điện thoại

## ✨ Hoàn Thành!

Nếu bạn đã cài đặt thành công, bạn sẽ thấy icon **GameBooster** trên home screen hoặc app drawer.

Chúc mừng! Bạn đã cài đặt thành công GameBooster! 🎉

---

**Cần trợ giúp?** Xem hướng dẫn chi tiết trong `TERMUX_BUILD_GUIDE.md` hoặc `README.md`
