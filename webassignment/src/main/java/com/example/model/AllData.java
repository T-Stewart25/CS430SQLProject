package com.example.model;

public class AllData {
    private User user;
    private Uses uses;
    private Device device;

    // Constructor
    public AllData(User user, Uses uses, Device device) {
        this.user = user;
        this.uses = uses;
        this.device = device;
    }

    // Getters
    public User getUser() {
        return user;
    }

    public Uses getUses() {
        return uses;
    }

    public Device getDevice() {
        return device;
    }

    // Setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setUses(Uses uses) {
        this.uses = uses;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
