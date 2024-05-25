package com.wust.advanced.web.utils.dto;

import java.time.LocalDate;


public record DriverDto(
        Long id,
        String drivingLicenseNumber,
        String drivingLicenseCountryCode,
        LocalDate dateOfBirth,
        FMUserDto user) {
}