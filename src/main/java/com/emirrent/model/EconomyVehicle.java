package com.emirrent.model;

    //inheritance (burada nesne oluştur)
    public class EconomyVehicle extends Vehicle {

    public EconomyVehicle(Long id,
                          String brand,
                          String model,
                          String imageUrl,
                          FuelType fuel,
                          GearType gear,
                          int baseDailyPrice,
                          String location) {
        super(
            id,
            brand,
            model,
            imageUrl,
            fuel,
            gear,
            baseDailyPrice,
            21, // Min Age rule
            2,  // Min License Year rule
            location,
            VehicleCategory.ECONOMY // kategorisini belirle
        );
    }
    //ana sınıftaki boş metodun içini doldurucam
    @Override
    public int calculatePricePerDay() {
        int price = getBaseDailyPrice();

        // If Diesel (+100 TL), If Manual (-100 TL)
        if (getFuel() == FuelType.DIESEL) {
            price += 100;
        }
        if (getGear() == GearType.MANUAL) {
            price -= 100;
        }
        return price;
    }
}


