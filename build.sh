#!/data/data/com.termux/files/usr/bin/bash

echo "=========================================="
echo "GameBooster - Build Script"
echo "=========================================="
echo ""

# Kiểm tra gradlew
if [ ! -f "gradlew" ]; then
    echo "❌ Không tìm thấy gradlew!"
    echo "Vui lòng chạy script này từ thư mục GameBooster"
    exit 1
fi

echo "Chọn loại build:"
echo "1) Debug APK (nhanh, cho test)"
echo "2) Release APK (tối ưu)"
echo "3) Clean build (xóa cache rồi build)"
echo ""

read -p "Nhập lựa chọn (1-3): " choice

case $choice in

1)
echo ""
echo "🏗️ Building Debug APK..."
chmod +x ./gradlew
./gradlew assembleDebug --no-daemon

if [ $? -eq 0 ]; then
echo ""
echo "✅ Build thành công!"
echo "📦 APK:"
echo "app/build/outputs/apk/debug/app-debug.apk"

read -p "Copy sang Downloads? (y/n): " copy_choice

if [ "$copy_choice" = "y" ]; then
cp app/build/outputs/apk/debug/app-debug.apk ~/storage/downloads/
echo "✅ Đã copy sang Downloads"
fi

else
echo "❌ Build thất bại!"
fi
;;

2)
echo "🚀 Building Release APK..."
chmod +x ./gradlew
./gradlew assembleRelease --no-daemon
;;

3)
echo "🧹 Cleaning project..."
./gradlew clean
./gradlew assembleDebug --no-daemon
;;

*)
echo "❌ Lựa chọn không hợp lệ!"
;;

esac
