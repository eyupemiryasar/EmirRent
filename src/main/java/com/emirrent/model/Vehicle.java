package com.emirrent.model;

//ata sınıf (abstraction)- nesneleri alt sınıflarında oluşturabilirim
public abstract class Vehicle {

    private Long id;
    private String brand;
    private String model;
    private String imageUrl;
    private FuelType fuel;
    private GearType gear;

    private int minAge;
    private int minLicenseYear;

    private int baseDailyPrice;

    private String location;

    private VehicleCategory category;

    //constructor metodum
    protected Vehicle(Long id,
                      String brand,
                      String model,
                      String imageUrl,
                      FuelType fuel,
                      GearType gear,
                      int baseDailyPrice,
                      int minAge,
                      int minLicenseYear,
                      String location,
                      VehicleCategory category) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.imageUrl = imageUrl;
        this.fuel = fuel;
        this.gear = gear;
        this.baseDailyPrice = baseDailyPrice;
        this.minAge = minAge;
        this.minLicenseYear = minLicenseYear;
        this.location = location;
        this.category = category;
    }

    //bu metodun içeriği her alt sınıfta değişiyor
    public abstract int calculatePricePerDay();
    
    //tüm getter metodları - private oldukları için bu metodlarla readable yaptım
    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public FuelType getFuel() {
        return fuel;
    }

    public GearType getGear() {
        return gear;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMinLicenseYear() {
        return minLicenseYear;
    }

    public int getBaseDailyPrice() {
        return baseDailyPrice;
    }

    public String getLocation() {
        return location;
    }

    public VehicleCategory getCategory() {
        return category;
    }

    //her araç için km limiti günlük 500
    public static int getDailyKmLimit() {
        return 500;
    }
}


