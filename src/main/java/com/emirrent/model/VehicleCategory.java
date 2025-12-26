package com.emirrent.model;

public enum VehicleCategory {
    ECONOMY("Ekonomik"),
    COMFORT("Konfor"),
    LUXURY("Lüks");

    private final String displayName;

    VehicleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

//GearType ve FuelType ile tamamen aynı