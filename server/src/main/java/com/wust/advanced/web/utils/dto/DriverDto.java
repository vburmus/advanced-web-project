package com.wust.advanced.web.utils.dto;

import java.time.LocalDate;


public record DriverDto(
        Long id,
        String name,
        String surname,
        String drivingLicenseNumber,
        String drivingLicenseCountryCode,
        LocalDate dateOfBirth) {
}