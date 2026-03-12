#!/bin/bash

# GameBooster - Termux Setup Script
# Tự động cài đặt tất cả công cụ cần thiết

echo "=========================================="
echo "GameBooster - Termux Setup Script"
echo "=========================================="
echo ""

# Kiểm tra nếu đang chạy trên Termux
if [ ! -d "$PREFIX" ]; then
    echo "❌ Script này chỉ chạy được trên Termux!"
    echo "Vui lòng cài Termux từ F-Droid hoặc Play Store"
    exit 1
fi

echo "✅ Phát hiện Termux"
echo ""

# Bước 1: Cập nhật packages
echo "📦 Bước 1: Cập nhật packages..."
pkg update -y
pkg upgrade -y
echo "✅ Cập nhật hoàn thành"
echo ""

# Bước 2: Cài JDK
echo "☕ Bước 2: Cài Java Development Kit..."
pkg install openjdk-17 -y
echo "✅ JDK cài đặt hoàn thành"
echo ""

# Bước 3: Cài Git
echo "🔗 Bước 3: Cài Git..."
pkg install git -y
echo "✅ Git cài đặt hoàn thành"
echo ""

# Bước 4: Cài Android SDK
echo "📱 Bước 4: Cài Android SDK..."
pkg install android-sdk -y
echo "✅ Android SDK cài đặt hoàn thành"
echo ""

# Bước 5: Cài Gradle
echo "🏗️ Bước 5: Cài Gradle..."
pkg install gradle -y
echo "✅ Gradle cài đặt hoàn thành"
echo ""

# Bước 6: Tạo local.properties
echo "⚙️ Bước 6: Cấu hình local.properties..."
cat > local.properties << EOF
sdk.dir=$ANDROID_SDK_ROOT
ndk.dir=$ANDROID_NDK_ROOT
EOF
echo "✅ local.properties tạo thành công"
echo ""

# Bước 7: Kiểm tra cài đặt
echo "🔍 Kiểm tra cài đặt..."
echo ""
echo "Java version:"
java -version
echo ""
echo "Gradle version:"
gradle -version
echo ""
echo "Git version:"
git --version
echo ""

# Bước 8: Tạo thư mục storage
echo "📁 Bước 7: Cấu hình storage..."
if [ ! -d "$HOME/storage" ]; then
    termux-setup-storage
fi
echo "✅ Storage cấu hình hoàn thành"
echo ""

echo "=========================================="
echo "✅ Setup hoàn thành!"
echo "=========================================="
echo ""
echo "📝 Tiếp theo, chạy lệnh này để build APK:"
echo ""
echo "  ./gradlew assembleDebug"
echo ""
echo "Hoặc xem hướng dẫn chi tiết:"
echo "  cat QUICK_START_TERMUX.md"
echo ""
