package com.vip.fakelag;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.app.NotificationCompat;

public class FloatingControlService extends Service {

    private static final String TAG = "FloatingControlService";
    private static final String CHANNEL_ID = "GameBoosterFloating";
    private static final int NOTIFICATION_ID = 2;

    private WindowManager windowManager;
    private LinearLayout floatingView;
    private WindowManager.LayoutParams params;

    private Button stopButton;
    private Button teleButton;
    private Button ghostButton;

    private float lastX, lastY;
    private float startX, startY;
    private boolean isDragging = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create notification channel
        createNotificationChannel();

        // Start foreground service
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);

        // Create floating view
        createFloatingView();

        return START_STICKY;
    }

    private void createFloatingView() {
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        // Create layout
        floatingView = new LinearLayout(this);
        floatingView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        floatingView.setOrientation(LinearLayout.VERTICAL);
        floatingView.setBackgroundColor(0x88000000);

        // Create buttons
        stopButton = createButton("Ngưng", 0xff00E676);
        teleButton = createButton("Tele", 0xff00E676);
        ghostButton = createButton("Ghost", 0xff00E676);

        floatingView.addView(stopButton);
        floatingView.addView(teleButton);
        floatingView.addView(ghostButton);

        // Set button listeners
        stopButton.setOnClickListener(v -> {
            GameBoosterVpnService.blockDownload = !GameBoosterVpnService.blockDownload;
            updateButtonState(stopButton, GameBoosterVpnService.blockDownload);
        });

        teleButton.setOnClickListener(v -> {
            GameBoosterVpnService.blockUpload = !GameBoosterVpnService.blockUpload;
            updateButtonState(teleButton, GameBoosterVpnService.blockUpload);
        });

        ghostButton.setOnClickListener(v -> {
            GameBoosterVpnService.ghostMode = !GameBoosterVpnService.ghostMode;
            updateButtonState(ghostButton, GameBoosterVpnService.ghostMode);
        });

        // Set touch listener for dragging
        floatingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        startX = lastX;
                        startY = lastY;
                        isDragging = false;
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float dx = event.getRawX() - lastX;
                        float dy = event.getRawY() - lastY;

                        if (Math.abs(dx) > 10 || Math.abs(dy) > 10) {
                            isDragging = true;
                        }

                        if (isDragging) {
                            params.x += (int) dx;
                            params.y += (int) dy;
                            windowManager.updateViewLayout(floatingView, params);
                            lastX = event.getRawX();
                            lastY = event.getRawY();
                        }
                        return true;

                    case MotionEvent.ACTION_UP:
                        return !isDragging;
                }
                return false;
            }
        });

        // Window manager layout params
        params = new WindowManager.LayoutParams();
        params.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.TRANSLUCENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.x = 0;
        params.y = 100;

        // Make window touchable
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        windowManager.addView(floatingView, params);
    }

    private Button createButton(String text, int textColor) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextColor(textColor);
        button.setBackgroundColor(0xff161b22);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                150,
                60
        ));
        return button;
    }

    private void updateButtonState(Button button, boolean isActive) {
        if (isActive) {
            button.setBackgroundColor(0xff238636);
            button.setTextColor(0xff00E676);
        } else {
            button.setBackgroundColor(0xff161b22);
            button.setTextColor(0xff00E676);
        }
    }

    private Notification createNotification() {
        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("GameBooster Control")
                .setContentText("Floating control panel active...")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "GameBooster Floating",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
