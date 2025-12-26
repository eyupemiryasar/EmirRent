package com.emirrent.service;

import com.emirrent.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final List<Vehicle> allVehicles = new ArrayList<>();

    private static final List<String> LOCATIONS = List.of(
        "Sabiha Gökçen",
        "Ataşehir",
        "Kadıköy",
        "Bayrampaşa",
        "Fatih"
    );

    @PostConstruct
    public void initData() {
        long idCounter = 1L;

        for (String location : LOCATIONS) {
            // ECONOMY MODELS (2 instances each per location)
            allVehicles.add(new EconomyVehicle(idCounter++, "Fiat", "Egea", getImageUrl("Fiat", "Egea"), FuelType.GASOLINE, GearType.MANUAL, 1200, location));
            allVehicles.add(new EconomyVehicle(idCounter++, "Fiat", "Fiorino", getImageUrl("Fiat", "Fiorino"), FuelType.DIESEL,   GearType.MANUAL, 1250, location));
            allVehicles.add(new EconomyVehicle(idCounter++, "Fiat", "Egea Cross", getImageUrl("Fiat", "Egea Cross"), FuelType.GASOLINE, GearType.AUTO, 1300, location));
            allVehicles.add(new EconomyVehicle(idCounter++, "Renault", "Clio", getImageUrl("Renault", "Clio"), FuelType.GASOLINE, GearType.MANUAL, 1400, location));
            allVehicles.add(new EconomyVehicle(idCounter++, "Renault", "Megane", getImageUrl("Renault", "Megane"), FuelType.GASOLINE, GearType.AUTO, 1500, location));

            // COMFORT MODELS (2 instances each per location)
            allVehicles.add(new ComfortVehicle(idCounter++, "Ford", "Puma", getImageUrl("Ford", "Puma"), 2000, location));
            allVehicles.add(new ComfortVehicle(idCounter++, "Toyota", "Proace", getImageUrl("Toyota", "Proace"), 2050, location));
            allVehicles.add(new ComfortVehicle(idCounter++, "Volkswagen", "T-Roc", getImageUrl("Volkswagen", "T-Roc"), 2100, location));
            allVehicles.add(new ComfortVehicle(idCounter++, "Toyota", "Corolla Hybrid", getImageUrl("Toyota", "Corolla Hybrid"), 2200, location));
            allVehicles.add(new ComfortVehicle(idCounter++, "Ford", "Focus", getImageUrl("Ford", "Focus"), 2500, location));

            // LUXURY MODELS (2 instances each per location)
            allVehicles.add(new LuxuryVehicle(idCounter++, "Volkswagen", "Tiguan", getImageUrl("Volkswagen", "Tiguan"), 3000, location));
            allVehicles.add(new LuxuryVehicle(idCounter++, "BYD", "Seal U", getImageUrl("BYD", "Seal U"), 3500, location));
            allVehicles.add(new LuxuryVehicle(idCounter++, "Mercedes", "Vito", getImageUrl("Mercedes", "Vito"), 4000, location));
            allVehicles.add(new LuxuryVehicle(idCounter++, "Volvo", "XC90", getImageUrl("Volvo", "XC90"), 5000, location));
        }
    }

    private String getImageUrl(String brand, String model) {
        String key = (brand + " " + model).toLowerCase();

        return switch (key) {
            case "fiat egea" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleGroupImage%2F2025%2F2%2F11%2F4d28a832-7488-4ae3-bc52-276b7ccf72a0%2F9d708784-495b-4c4b-806e-eff88d13b7ed.png&w=750&q=75";
            case "fiat fiorino" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleGroupImage%2F2025%2F2%2F11%2F7877d947-6620-455b-8a03-d8728e54320f%2F3e77743d-73b4-4169-a405-b001971996ce.png&w=750&q=75";
            case "fiat egea cross" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleGroupImage%2F2025%2F3%2F3%2F04f7ea78-e011-49db-9e20-818a681242d3%2Fb1e53989-3440-46f0-a23a-f28523ea8b85.png&w=750&q=75";
            case "renault clio" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleGroupImage%2F2025%2F2%2F20%2F22560a3e-878c-4938-a5a0-3a9d96f63f2e%2Ff37f6b3d-21c2-4351-9bef-2f533e902690.png&w=750&q=75";
            case "renault megane" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleGroupImage%2F2025%2F2%2F17%2Faa8a1f8c-1d34-4ae5-940b-801136e34b84%2F151853eb-bbc0-4165-8c9b-c0a7ac0abdc9.png&w=750&q=75";

            case "ford puma" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleGroupImage%2F2025%2F2%2F11%2Fa60abd81-34a6-4404-9c6b-d2d1673601bd%2Fd14e9146-277b-47ec-860c-5cf90d929aed.png&w=750&q=75";
            case "toyota proace" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleContentImages%2F2025%2F3%2F5%2Fa117ee08-9c98-42c9-a1be-53263d693a44%2F190c6d90-5a29-44c0-9633-4ac1bdbe2e81.png&w=1920&q=75";
            case "volkswagen t-roc" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleContentImages%2F2025%2F2%2F24%2F42f686f4-86bc-4fb9-bbf1-4e5c839c094d%2F5257ddb7-96d5-4c38-9c8c-4f0c8d6d5d96.png&w=1920&q=75";
            case "toyota corolla hybrid" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleContentImages%2F2025%2F3%2F3%2F52ac55e5-f40c-4155-ba1e-2597f11c9401%2Fa36e50bb-4e9b-4b61-8704-5715b995b20a.png&w=1920&q=75";
            case "ford focus" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleContentImages%2F2025%2F3%2F5%2F0daa355d-be34-4a42-9bb4-c5e855b0609f%2F963c04d6-8d1d-4c2a-99be-f6c1d9338429.png&w=1920&q=75";

            case "volkswagen tiguan" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleContentImages%2F2025%2F2%2F24%2Fec0f01d7-900c-4590-b0d0-c9d67f312cdf%2Fca237ea9-8491-46b6-9e71-2f113af691fe.png&w=1920&q=75";
            case "byd seal u" ->
                "https://www.karrentacar.com.tr/dosya/2561/sinif/61-56-47-byd-seal-u-dm-i.png";
            case "mercedes vito" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleContentImages%2F2025%2F2%2F26%2F3b6920c7-09fd-4739-b3b8-7b76ddf837c9%2F9a22e595-6512-4816-b9b9-414be53bddcd.png&w=1920&q=75";
            case "volvo xc90" ->
                "https://www.garenta.com/_next/image?url=https%3A%2F%2Fs3.garenta.com.tr%2Fvehicledocuments%2FvehicleContentImages%2F2025%2F2%2F24%2Fc6cb2f39-6ecc-44ea-b9f5-63a6acb0c709%2F2091a557-0e90-4530-980a-7d40091a7081.png&w=1920&q=75";
            default ->
                "https://via.placeholder.com/600x350?text=EmirRent+Car";
        };
    }

    public List<String> getLocations() {
        return LOCATIONS;
    }

    public Vehicle getVehicleById(Long id) {
        return allVehicles.stream()
            .filter(v -> v.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public long calculateRentalDays(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !end.isAfter(start)) {
            return 1L;
        }

        long hours = ChronoUnit.HOURS.between(start, end);
        long days = hours / 24;
        long remainingHours = hours % 24;

        if (remainingHours >= 3) {
            days += 1;
        }

        return Math.max(days, 1);
    }

    public List<VehicleSearchResult> searchVehicles(String pickupLocation,
                                                    LocalDateTime pickupDate,
                                                    LocalDateTime dropoffDate) {
        long days = calculateRentalDays(pickupDate, dropoffDate);

        // Filter by location
        List<Vehicle> filtered = allVehicles.stream()
            .filter(v -> v.getLocation().equalsIgnoreCase(pickupLocation))
            .collect(Collectors.toList());

        // Map to results with total price
        List<VehicleSearchResult> results = filtered.stream()
            .map(v -> new VehicleSearchResult(
                v,
                days,
                v.calculatePricePerDay() * (int) days
            ))
            // Sort by total price ascending
            .sorted(Comparator.comparingInt(VehicleSearchResult::getTotalPrice))
            .collect(Collectors.toList());

        return results;
    }

    // DTO for displaying in UI
    public static class VehicleSearchResult {
        private final Vehicle vehicle;
        private final long days;
        private final int totalPrice;

        public VehicleSearchResult(Vehicle vehicle, long days, int totalPrice) {
            this.vehicle = vehicle;
            this.days = days;
            this.totalPrice = totalPrice;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public long getDays() {
            return days;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public int getPricePerDay() {
            return vehicle.calculatePricePerDay();
        }
    }
}


