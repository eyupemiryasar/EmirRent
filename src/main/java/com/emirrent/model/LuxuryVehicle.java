package com.emirrent.model;

public class LuxuryVehicle extends Vehicle {

    public LuxuryVehicle(Long id,
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
            FuelType.GASOLINE,  // Always Gasoline
            GearType.AUTO,      // Always Auto
            baseDailyPrice,
            27, // Min Age
            5,  // Min License Year
            location,
            VehicleCategory.LUXURY
        );
    }

    @Override
    public int calculatePricePerDay() {
        return getBaseDailyPrice();
    }
}


