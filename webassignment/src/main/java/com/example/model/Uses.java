package com.example.model;

public class Uses {

    private int userId;
    private int deviceId;
    private String usageDate;
    private String usageDuration;

    // Constructor
    public Uses(int userId, int deviceId, String usageDate, String usageDuration) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.usageDuration = usageDuration;
        this.usageDate = usageDate;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getUsageDate() {
        return usageDate;
    }

    public String getUsageDuration() {
        return usageDuration;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setUsageDate(String usageDate) {
        this.usageDate = usageDate;
    }

    public void setUsageDuration(String usageDuration) {
        this.usageDuration = usageDuration;
    }
}
