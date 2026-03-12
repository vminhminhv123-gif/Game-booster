package com.vip.fakelag;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ApplicationInfo> launchableApps;
    private String selectedPackage;
    private Switch vpnSwitch;
    private TextView selectedAppText;
    private boolean waitingForPermission = false;

    private static final int VPN_PERMISSION_REQUEST = 0x3e9;
    private static final int OVERLAY_PERMISSION_REQUEST = 0x3ea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize UI components
        vpnSwitch = findViewById(R.id.vpn_switch);
        selectedAppText = findViewById(R.id.selected_app_text);
        Button activateButton = findViewById(R.id.activate_button);

        // Style switch
        styleSwitch(vpnSwitch);

        // Style button
        styleButton(activateButton);

        // Load installed applications
        loadLaunchableApps();

        // Switch listener
        vpnSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (selectedPackage == null || selectedPackage.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select an app first", Toast.LENGTH_SHORT).show();
                    vpnSwitch.setChecked(false);
                    return;
                }
                checkVpnAndOverlay();
            } else {
                stopAllServices();
            }
        });

        // Activate button listener
        activateButton.setOnClickListener(v -> {
            if (selectedPackage == null || selectedPackage.isEmpty()) {
                showAppSelector();
            } else {
                if (!vpnSwitch.isChecked()) {
                    vpnSwitch.setChecked(true);
                }
            }
        });
    }

    private void checkVpnAndOverlay() {
        // Check overlay permission for API >= 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                startActivityForResult(
                    new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION),
                    OVERLAY_PERMISSION_REQUEST
                );
                return;
            }
        }

        // Check VPN permission
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            waitingForPermission = true;
            startActivityForResult(intent, VPN_PERMISSION_REQUEST);
        } else {
            startEngine();
        }
    }

    private void startEngine() {
        // Start VPN Service
        Intent vpnIntent = new Intent(this, GameBoosterVpnService.class);
        vpnIntent.putExtra("PACKAGE", selectedPackage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(vpnIntent);
        } else {
            startService(vpnIntent);
        }

        // Start Floating Control Service
        Intent floatingIntent = new Intent(this, FloatingControlService.class);
        floatingIntent.putExtra("PACKAGE", selectedPackage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(floatingIntent);
        } else {
            startService(floatingIntent);
        }

        // Update UI
        vpnSwitch.setTextColor(0xff00E676);
        Toast.makeText(this, "Fake lag! Mở game để bắt đầu.", Toast.LENGTH_SHORT).show();
    }

    private void stopAllServices() {
        // Stop VPN Service
        stopService(new Intent(this, GameBoosterVpnService.class));

        // Stop Floating Control Service
        stopService(new Intent(this, FloatingControlService.class));

        // Update UI
        vpnSwitch.setTextColor(0xffFF0000);
    }

    private void loadLaunchableApps() {
        launchableApps = new ArrayList<>();
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo app : apps) {
            // Filter out system apps and this app
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0 && !app.packageName.equals(getPackageName())) {
                // Check if app has launch intent
                Intent launchIntent = pm.getLaunchIntentForPackage(app.packageName);
                if (launchIntent != null) {
                    launchableApps.add(app);
                }
            }
        }

        // Sort apps by name
        Collections.sort(launchableApps, new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo a, ApplicationInfo b) {
                String aName = a.loadLabel(pm).toString();
                String bName = b.loadLabel(pm).toString();
                return aName.compareTo(bName);
            }
        });
    }

    private void showAppSelector() {
        if (launchableApps.isEmpty()) {
            Toast.makeText(this, "No apps available", Toast.LENGTH_SHORT).show();
            return;
        }

        PackageManager pm = getPackageManager();
        String[] appNames = new String[launchableApps.size()];
        for (int i = 0; i < launchableApps.size(); i++) {
            appNames[i] = launchableApps.get(i).loadLabel(pm).toString();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Application");
        builder.setItems(appNames, (dialog, which) -> {
            selectedPackage = launchableApps.get(which).packageName;
            selectedAppText.setText("Selected App: " + appNames[which]);
            Toast.makeText(MainActivity.this, "Selected: " + appNames[which], Toast.LENGTH_SHORT).show();
        });
        builder.show();
    }

    private void styleButton(Button button) {
        button.setTextColor(0xff00E676);
        button.setTextSize(14);
    }

    private void styleSwitch(Switch switchView) {
        switchView.setText("KÍCH HOẠT ENGINE");
        switchView.setTextColor(0xffFFD700);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VPN_PERMISSION_REQUEST) {
            if (resultCode == RESULT_OK) {
                startEngine();
            } else {
                vpnSwitch.setChecked(false);
                Toast.makeText(this, "VPN permission denied", Toast.LENGTH_SHORT).show();
            }
            waitingForPermission = false;
        } else if (requestCode == OVERLAY_PERMISSION_REQUEST) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    checkVpnAndOverlay();
                } else {
                    vpnSwitch.setChecked(false);
                    Toast.makeText(this, "Overlay permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
