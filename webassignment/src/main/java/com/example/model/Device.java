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
    public int getUserId() {
        return deviceId;
    }

    public void setUserId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserName() {
        return deviceName;
    }

    public void setUserName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getUserType() {
        return deviceType;
    }

    public void setUserType(String deviceType) {
        this.deviceType = deviceType;
    }
}
