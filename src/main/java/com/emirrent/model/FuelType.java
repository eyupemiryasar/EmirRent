package com.emirrent.model;

public enum FuelType {
    DIESEL("Dizel"),
    GASOLINE("Benzin");

    private final String displayName;

    FuelType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

//GearType ve VehicleCategory ile tamamen aynı