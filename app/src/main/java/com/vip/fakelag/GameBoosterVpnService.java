package com.vip.fakelag;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.os.Build;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;

import androidx.core.app.NotificationCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameBoosterVpnService extends VpnService {

    private static final String TAG = "GameBoosterVpnService";
    private static final String CHANNEL_ID = "GameBoosterVPN";
    private static final int NOTIFICATION_ID = 1;

    private String targetPackage;
    private ParcelFileDescriptor vpnInterface;
    private Thread vpnThread;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    // Packet filtering states
    public static volatile boolean blockDownload = false;
    public static volatile boolean blockUpload = false;
    public static volatile boolean ghostMode = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            targetPackage = intent.getStringExtra("PACKAGE");
        }

        // Create notification channel
        createNotificationChannel();

        // Start foreground service
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);

        // Start VPN thread
        if (!isRunning.get()) {
            isRunning.set(true);
            vpnThread = new Thread(this::runVpn);
            vpnThread.start();
        }

        return START_STICKY;
    }

    private void runVpn() {
        try {
            // Setup VPN interface
            Builder builder = new Builder();
            builder.setSession("GameBooster VPN")
                    .addAddress("10.0.0.1", 24)
                    .addRoute("0.0.0.0", 0)
                    .addDnsServer("8.8.8.8")
                    .addDnsServer("8.8.4.4");

            vpnInterface = builder.establish();

            if (vpnInterface == null) {
                return;
            }

            // Packet processing loop
            FileInputStream in = new FileInputStream(vpnInterface.getFileDescriptor());
            FileOutputStream out = new FileOutputStream(vpnInterface.getFileDescriptor());

            byte[] packet = new byte[32767];
            int length;

            while (isRunning.get()) {
                try {
                    length = in.read(packet);
                    if (length > 0) {
                        processPacket(packet, length);

                        // Apply anti-ban jitter
                        BypassAntiban.applyPacketJitter();

                        // Forward packet if not blocked
                        if (!shouldBlockPacket(packet, length)) {
                            out.write(packet, 0, length);
                            out.flush();
                        }
                    }
                } catch (IOException e) {
                    if (isRunning.get()) {
                        e.printStackTrace();
                    }
                }
            }

            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processPacket(byte[] packet, int length) {
        if (length < 20) {
            return;
        }

        // Parse IP header
        int version = (packet[0] & 0xf0) >> 4;
        if (version != 4) {
            return;
        }

        int protocol = packet[9] & 0xff;
        int headerLength = (packet[0] & 0x0f) * 4;

        // Extract source and destination IPs
        int srcIp = ((packet[12] & 0xff) << 24) |
                    ((packet[13] & 0xff) << 16) |
                    ((packet[14] & 0xff) << 8) |
                    (packet[15] & 0xff);

        int dstIp = ((packet[16] & 0xff) << 24) |
                    ((packet[17] & 0xff) << 16) |
                    ((packet[18] & 0xff) << 8) |
                    (packet[19] & 0xff);

        // TCP protocol (6)
        if (protocol == 6 && length >= headerLength + 20) {
            int srcPort = ((packet[headerLength] & 0xff) << 8) | (packet[headerLength + 1] & 0xff);
            int dstPort = ((packet[headerLength + 2] & 0xff) << 8) | (packet[headerLength + 3] & 0xff);
            // TCP packet processed
        }
        // UDP protocol (17)
        else if (protocol == 17 && length >= headerLength + 8) {
            int srcPort = ((packet[headerLength] & 0xff) << 8) | (packet[headerLength + 1] & 0xff);
            int dstPort = ((packet[headerLength + 2] & 0xff) << 8) | (packet[headerLength + 3] & 0xff);
            // UDP packet processed
        }
    }

    private boolean shouldBlockPacket(byte[] packet, int length) {
        if (length < 20) {
            return false;
        }

        // Parse IP header
        int version = (packet[0] & 0xf0) >> 4;
        if (version != 4) {
            return false;
        }

        int protocol = packet[9] & 0xff;
        int headerLength = (packet[0] & 0x0f) * 4;

        // Check if packet is outgoing (block upload)
        if (blockUpload && isOutgoingPacket(packet)) {
            return true;
        }

        // Check if packet is incoming (block download)
        if (blockDownload && isIncomingPacket(packet)) {
            return true;
        }

        // Ghost mode: bypass specific packets
        if (ghostMode) {
            // Bypass position sync, item sync, chat packets
            if (protocol == 17 && length >= headerLength + 8) {
                int dstPort = ((packet[headerLength + 2] & 0xff) << 8) | (packet[headerLength + 3] & 0xff);
                // Bypass common game ports
                if (dstPort >= 5000 && dstPort <= 65535) {
                    return false;
                }
            }
        }

        return false;
    }

    private boolean isOutgoingPacket(byte[] packet) {
        // Simple check: if source IP is in VPN range, it's outgoing
        return ((packet[12] & 0xff) == 10);
    }

    private boolean isIncomingPacket(byte[] packet) {
        // Simple check: if destination IP is in VPN range, it's incoming
        return ((packet[16] & 0xff) == 10);
    }

    private Notification createNotification() {
        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("GameBooster VPN")
                .setContentText("Fake lag engine running...")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "GameBooster VPN",
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
        isRunning.set(false);

        if (vpnInterface != null) {
            try {
                vpnInterface.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (vpnThread != null) {
            try {
                vpnThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
