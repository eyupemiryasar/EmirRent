package com.emirrent.model;

public class ComfortVehicle extends Vehicle {

    public ComfortVehicle(Long id,
                          String brand,
                          String model,
                          String imageUrl,
                          int baseDailyPrice,
                          String location) {
        super(
            id,
            brand,
            model,
            imageUrl,
            FuelType.GASOLINE,  // hep Gasoline , dizel yok
            GearType.AUTO,      // hep Auto , manuel yok
            baseDailyPrice,
            25, // Min Age
            3,  // Min License Year
            location,
            VehicleCategory.COMFORT
        );
    }

    @Override
    public int calculatePricePerDay() {
        return getBaseDailyPrice();
    }
}


