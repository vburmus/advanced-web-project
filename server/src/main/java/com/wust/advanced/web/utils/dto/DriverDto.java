package com.wust.advanced.web.utils.dto;

import java.time.LocalDate;


public record DriverDto(
        FMUserDto user,
        String drivingLicenseNumber,
        String drivingLicenseCountryCode,
        LocalDate dateOfBirth) {
}