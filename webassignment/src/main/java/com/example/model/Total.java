package com.example.model;

public class Total {
    private int userId;
    private String userName;
    private String deviceName;
    private String deviceType;
    private int totalUsageDuration;
    
    public Total(int userId, String userName, String deviceName, String deviceType, int totalUsageDuration){
        this.userId = userId;
        this.userName = userName;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.totalUsageDuration = totalUsageDuration;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getTotalUsageDuration() {
        return totalUsageDuration;
    }

    public void setTotalUsageDuration(int totalUsageDuration) {
        this.totalUsageDuration = totalUsageDuration;
    }
}
