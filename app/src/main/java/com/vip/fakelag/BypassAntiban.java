package com.vip.fakelag;

import java.util.Random;

public class BypassAntiban {

    private static final String TAG = "BypassAntiban";
    private static final Random RANDOM = new Random();

    // Jitter table for packet timing
    private static int[] jitterTable = null;
    private static int[] burstLengths = null;

    // Anti-ban settings
    private static boolean jitterEnabled = true;
    private static int basePing = 50;

    static {
        // Initialize jitter table with random values
        initializeJitterTable();
    }

    /**
     * Initialize jitter table with random delay values
     */
    private static void initializeJitterTable() {
        jitterTable = new int[100];
        for (int i = 0; i < jitterTable.length; i++) {
            // Random jitter between 1-10ms
            jitterTable[i] = RANDOM.nextInt(10) + 1;
        }

        burstLengths = new int[10];
        for (int i = 0; i < burstLengths.length; i++) {
            // Random burst length between 5-20 packets
            burstLengths[i] = RANDOM.nextInt(16) + 5;
        }
    }

    /**
     * Apply packet jitter to simulate natural network behavior
     * This adds slight delays to packets to avoid detection
     */
    public static void applyPacketJitter() {
        if (!jitterEnabled || jitterTable == null) {
            return;
        }

        try {
            // Get random jitter from table
            int jitterIndex = RANDOM.nextInt(jitterTable.length);
            int jitterMs = jitterTable[jitterIndex];

            // Apply light jitter (1-10ms)
            // This is minimal to avoid increasing actual ping
            if (jitterMs > 0 && jitterMs <= 10) {
                Thread.sleep(jitterMs);
            }

            // Occasionally apply burst pattern
            if (RANDOM.nextDouble() < 0.1) { // 10% chance
                int burstIndex = RANDOM.nextInt(burstLengths.length);
                int burstLength = burstLengths[burstIndex];
                // Process burst of packets
                for (int i = 0; i < burstLength; i++) {
                    Thread.sleep(1);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Humanize movement by adding noise to coordinates
     * This prevents detection of perfect movement patterns
     *
     * @param x current X coordinate
     * @param y current Y coordinate
     * @param z current Z coordinate
     * @return humanized coordinates as float array [x, y, z]
     */
    public static float[] humanizeMovement(float x, float y, float z) {
        // Add random noise to coordinates
        float noiseX = (RANDOM.nextFloat() - 0.5f) * 0.2f;
        float noiseY = (RANDOM.nextFloat() - 0.5f) * 0.2f;
        float noiseZ = (RANDOM.nextFloat() - 0.5f) * 0.2f;

        // Apply noise with random scale
        float scale = 0.9f + RANDOM.nextFloat() * 0.2f;

        float newX = x + noiseX * scale;
        float newY = y + noiseY * scale;
        float newZ = z + noiseZ * scale;

        // Clamp values to reasonable range
        newX = Math.max(-1000, Math.min(1000, newX));
        newY = Math.max(-1000, Math.min(1000, newY));
        newZ = Math.max(-1000, Math.min(1000, newZ));

        return new float[]{newX, newY, newZ};
    }

    /**
     * Set jitter enabled/disabled
     */
    public static void setEnabled(boolean enabled) {
        jitterEnabled = enabled;
    }

    /**
     * Update base ping value
     */
    public static void updateBasePing(int ping) {
        basePing = ping;
    }

    /**
     * Get current jitter enabled state
     */
    public static boolean isEnabled() {
        return jitterEnabled;
    }

    /**
     * Get current base ping
     */
    public static int getBasePing() {
        return basePing;
    }

    /**
     * Re-initialize jitter table with new random values
     */
    public static void reinitializeJitterTable() {
        initializeJitterTable();
    }
}
