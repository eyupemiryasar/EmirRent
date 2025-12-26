package com.emirrent.controller;

import com.emirrent.model.VehicleCategory;
import com.emirrent.service.CarService;
import com.emirrent.service.CarService.VehicleSearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Controller
public class CarController {

    private final CarService carService;
    private static final List<String> TIME_SLOTS = List.of(
        "00:00","00:30","01:00","01:30","02:00","02:30",
        "03:00","03:30","04:00","04:30","05:00","05:30",
        "06:00","06:30","07:00","07:30","08:00","08:30",
        "09:00","09:30","10:00","10:30","11:00","11:30",
        "12:00","12:30","13:00","13:30","14:00","14:30",
        "15:00","15:30","16:00","16:30","17:00","17:30",
        "18:00","18:30","19:00","19:30","20:00","20:30",
        "21:00","21:30","22:00","22:30","23:00","23:30"
    );

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(required = false) String pickupLocation,
                        @RequestParam(required = false) String dropoffLocation,
                        @RequestParam(required = false) String pickupDate,
                        @RequestParam(required = false) String pickupTime,
                        @RequestParam(required = false) String dropoffDate,
                        @RequestParam(required = false) String dropoffTime) {

        LocalDateTime pickup = buildDateTime(pickupDate, pickupTime);
        LocalDateTime dropoff = buildDateTime(dropoffDate, dropoffTime);

        model.addAttribute("locations", carService.getLocations());
        model.addAttribute("timeSlots", TIME_SLOTS);

        if (pickupLocation != null && pickup != null && dropoff != null) {
            long days = calculateDays(pickup, dropoff);
            List<VehicleSearchResult> results = carService.searchVehicles(pickupLocation, pickup, dropoff);

            List<VehicleSearchResult> economy = results.stream()
                .filter(r -> r.getVehicle().getCategory() == VehicleCategory.ECONOMY)
                .collect(Collectors.toList());

            List<VehicleSearchResult> comfort = results.stream()
                .filter(r -> r.getVehicle().getCategory() == VehicleCategory.COMFORT)
                .collect(Collectors.toList());

            List<VehicleSearchResult> luxury = results.stream()
                .filter(r -> r.getVehicle().getCategory() == VehicleCategory.LUXURY)
                .collect(Collectors.toList());

            model.addAttribute("pickupLocation", pickupLocation);
            model.addAttribute("dropoffLocation", dropoffLocation);
            model.addAttribute("pickupDateTime", pickup);
            model.addAttribute("dropoffDateTime", dropoff);
            model.addAttribute("days", days);

            model.addAttribute("economyResults", economy);
            model.addAttribute("comfortResults", comfort);
            model.addAttribute("luxuryResults", luxury);
        }

        return "index";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(required = false) String pickupLocation,
                         @RequestParam(required = false) String dropoffLocation,
                         @RequestParam(required = false) String pickupDate,
                         @RequestParam(required = false) String pickupTime,
                         @RequestParam(required = false) String dropoffDate,
                         @RequestParam(required = false) String dropoffTime) {

        return index(model, pickupLocation, dropoffLocation, pickupDate, pickupTime, dropoffDate, dropoffTime);
    }

    @PostMapping("/payment-page")
    public String paymentPage(Model model,
                              @RequestParam Long vehicleId,
                              @RequestParam(required = false) String pickupLocation,
                              @RequestParam(required = false) String dropoffLocation,
                              @RequestParam(required = false) String pickupDate,
                              @RequestParam(required = false) String pickupTime,
                              @RequestParam(required = false) String dropoffDate,
                              @RequestParam(required = false) String dropoffTime) {

        var vehicle = carService.getVehicleById(vehicleId);
        if (vehicle == null) {
            return "redirect:/?error=vehicle-not-found";
        }

        LocalDateTime pickup = buildDateTime(pickupDate, pickupTime);
        LocalDateTime dropoff = buildDateTime(dropoffDate, dropoffTime);
        if (pickup == null || dropoff == null) {
            return "redirect:/?error=invalid-dates";
        }

        long days = calculateDays(pickup, dropoff);
        int pricePerDay = vehicle.calculatePricePerDay();
        int totalPrice = (int) (pricePerDay * days);

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("pickupLocation", pickupLocation);
        model.addAttribute("dropoffLocation", dropoffLocation);
        model.addAttribute("pickupDateTime", pickup);
        model.addAttribute("dropoffDateTime", dropoff);
        model.addAttribute("days", days);
        model.addAttribute("pricePerDay", pricePerDay);
        model.addAttribute("totalPrice", totalPrice);

        return "payment";
    }

    @PostMapping("/payment-review")
    public String paymentReview(Model model,
                                @RequestParam Long vehicleId,
                                @RequestParam(required = false) String pickupLocation,
                                @RequestParam(required = false) String dropoffLocation,
                                @RequestParam(required = false) String pickupDate,
                                @RequestParam(required = false) String pickupTime,
                                @RequestParam(required = false) String dropoffDate,
                                @RequestParam(required = false) String dropoffTime,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam int age,
                                @RequestParam int licenseAge,
                                @RequestParam String cardHolder,
                                @RequestParam String cardNumber,
                                @RequestParam String cardExpiry,
                                @RequestParam String cardCvv) {

        var vehicle = carService.getVehicleById(vehicleId);
        if (vehicle == null) {
            return "redirect:/?error=vehicle-not-found";
        }

        LocalDateTime pickup = buildDateTime(pickupDate, pickupTime);
        LocalDateTime dropoff = buildDateTime(dropoffDate, dropoffTime);
        if (pickup == null || dropoff == null) {
            return "redirect:/?error=invalid-dates";
        }

        if (vehicle.getMinAge() > age || vehicle.getMinLicenseYear() > licenseAge) {
            return "redirect:/?error=invalid-age";
        }

        long days = calculateDays(pickup, dropoff);
        int pricePerDay = vehicle.calculatePricePerDay();
        int totalPrice = (int) (pricePerDay * days);

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("pickupLocation", pickupLocation);
        model.addAttribute("dropoffLocation", dropoffLocation);
        model.addAttribute("pickupDateTime", pickup);
        model.addAttribute("dropoffDateTime", dropoff);
        model.addAttribute("days", days);
        model.addAttribute("pricePerDay", pricePerDay);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("age", age);
        model.addAttribute("licenseAge", licenseAge);
        model.addAttribute("cardHolder", cardHolder);
        model.addAttribute("cardNumber", cardNumber);
        model.addAttribute("cardExpiry", cardExpiry);
        model.addAttribute("cardCvv", cardCvv);

        return "payment-confirm";
    }

    @PostMapping("/process-payment")
    public String processPayment(Model model,
                                 @RequestParam Long vehicleId,
                                 @RequestParam(required = false) String pickupLocation,
                                 @RequestParam(required = false) String dropoffLocation,
                                 @RequestParam(required = false) String pickupDate,
                                 @RequestParam(required = false) String pickupTime,
                                 @RequestParam(required = false) String dropoffDate,
                                 @RequestParam(required = false) String dropoffTime,
                                 @RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam int age,
                                 @RequestParam int licenseAge,
                                 @RequestParam String cardHolder,
                                 @RequestParam String cardNumber,
                                 @RequestParam String cardExpiry,
                                 @RequestParam String cardCvv) {

        var vehicle = carService.getVehicleById(vehicleId);
        if (vehicle == null) {
            return "redirect:/?error=vehicle-not-found";
        }

        LocalDateTime pickup = buildDateTime(pickupDate, pickupTime);
        LocalDateTime dropoff = buildDateTime(dropoffDate, dropoffTime);
        if (pickup == null || dropoff == null) {
            return "redirect:/?error=invalid-dates";
        }

        long days = calculateDays(pickup, dropoff);
        int pricePerDay = vehicle.calculatePricePerDay();
        int totalPrice = (int) (pricePerDay * days);

        String pnrCode = "PNR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        model.addAttribute("vehicle", vehicle);
        model.addAttribute("pickupLocation", pickupLocation);
        model.addAttribute("dropoffLocation", dropoffLocation);
        model.addAttribute("pickupDateTime", pickup);
        model.addAttribute("dropoffDateTime", dropoff);
        model.addAttribute("days", days);
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("age", age);
        model.addAttribute("licenseAge", licenseAge);
        model.addAttribute("cardHolder", cardHolder);
        model.addAttribute("cardNumber", cardNumber);
        model.addAttribute("cardExpiry", cardExpiry);
        model.addAttribute("cardCvv", cardCvv);

        model.addAttribute("pnrCode", pnrCode);

        return "success";
    }

    private long calculateDays(LocalDateTime pickupDate, LocalDateTime dropoffDate) {
        if (pickupDate == null || dropoffDate == null || !dropoffDate.isAfter(pickupDate)) {
            return 1L;
        }
        long hours = ChronoUnit.HOURS.between(pickupDate, dropoffDate);
        long days = hours / 24;
        long remainder = hours % 24;
        if (remainder >= 3) {
            days += 1;
        }
        return Math.max(days, 1);
    }

    private LocalDateTime buildDateTime(String datePart, String timePart) {
        if (datePart == null || timePart == null || datePart.isBlank() || timePart.isBlank()) {
            return null;
        }
        try {
            LocalDate date = LocalDate.parse(datePart, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime time = LocalTime.parse(timePart, DateTimeFormatter.ofPattern("HH:mm"));
            return LocalDateTime.of(date, time);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}


