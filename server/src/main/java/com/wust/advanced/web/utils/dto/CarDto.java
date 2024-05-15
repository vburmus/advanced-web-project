package com.wust.advanced.web.utils.dto;

import jakarta.validation.constraints.Null;

import java.util.List;

public record CarDto(
        Long id,
        String vin,
        String plateNumber,
        String countryCode,
        @Null
        DriverDto driver,
        @Null
        List<LocationDto> locations) {
}