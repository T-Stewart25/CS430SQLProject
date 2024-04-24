package com.example.model;

public class Device {
    private int deviceId;
    private String deviceName;
    private String deviceType;

    // Constructor
    public Device(int deviceId, String deviceName, String deviceType) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
    }

    // Getters and setters
    public int getdeviceId() {
        return deviceId;
    }

    public void setdeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getdeviceName() {
        return deviceName;
    }

    public void setdeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getdeviceType() {
        return deviceType;
    }

    public void setdeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}

