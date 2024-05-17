package com.wust.advanced.web.user.driver.service;

import com.wust.advanced.web.user.driver.model.Driver;
import com.wust.advanced.web.user.driver.repository.DriverRepository;
import com.wust.advanced.web.utils.EntityToDtoMapper;
import com.wust.advanced.web.utils.dto.DriverDto;
import com.wust.advanced.web.utils.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private EntityToDtoMapper entityToDtoMapper;

    @InjectMocks
    private DriverService driverService;

    private Driver driver;
    private DriverDto driverDto;

    @Captor
    private ArgumentCaptor<Example<Driver>> exampleCaptor;

    @BeforeEach
    void setUp() {
        driver = Driver.builder()
                       .id(1L)
                       .name("John")
                       .surname("Doe")
                       .drivingLicenseNumber("ABC123")
                       .drivingLicenseCountryCode("USA")
                       .dateOfBirth(LocalDate.of(1980, 1, 1))
                       .build();

        driverDto = new DriverDto(
                1L,
                "John",
                "Doe",
                "ABC123",
                "USA",
                LocalDate.of(1980, 1, 1)
        );
    }

    @Test
    void readAll() {
        Page<Driver> driversPage = new PageImpl<>(List.of(driver));
        when(driverRepository.findAll(any(Pageable.class))).thenReturn(driversPage);
        when(entityToDtoMapper.driverToDriverDto(driver)).thenReturn(driverDto);

        Pageable pageable = PageRequest.of(0, 10);
        Page<DriverDto> result = driverService.readAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(driverDto, result.getContent().get(0));
    }

    @Test
    void readById() {
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(entityToDtoMapper.driverToDriverDto(driver)).thenReturn(driverDto);

        DriverDto result = driverService.readById(1L);

        assertEquals(driverDto, result);
    }

    @Test
    void readByIdNotFound() {
        when(driverRepository.findById(1L)).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            driverService.readById(1L);
        });

        assertEquals(String.format(DriverService.DRIVER_NOT_FOUND, 1L), exception.getMessage());
    }

    @Test
    void create() {
        when(entityToDtoMapper.driverDtoToDriver(driverDto)).thenReturn(driver);
        when(driverRepository.exists(any(Example.class))).thenReturn(false);
        when(driverRepository.save(driver)).thenReturn(driver);
        when(entityToDtoMapper.driverToDriverDto(driver)).thenReturn(driverDto);

        DriverDto result = driverService.create(driverDto);

        assertEquals(driverDto, result);
        verify(driverRepository).save(driver);
    }

    @Test
    void createDriverExists() {
        when(entityToDtoMapper.driverDtoToDriver(driverDto)).thenReturn(driver);
        when(driverRepository.exists(any(Example.class))).thenReturn(true);

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            driverService.create(driverDto);
        });

        assertEquals(String.format(DriverService.DRIVER_ALREADY_EXISTS,
                driver.getDrivingLicenseCountryCode(), driver.getDrivingLicenseNumber()), exception.getMessage());
    }

    @Test
    void isDriverExists() {
        when(driverRepository.exists(any(Example.class))).thenReturn(true);

        boolean exists = driverService.isDriverExists(driver);

        assertTrue(exists);
        verify(driverRepository).exists(exampleCaptor.capture());

        Example<Driver> capturedExample = exampleCaptor.getValue();
        Driver capturedDriver = capturedExample.getProbe();
        assertEquals(driver.getDrivingLicenseNumber(), capturedDriver.getDrivingLicenseNumber());
        assertEquals(driver.getDrivingLicenseCountryCode(), capturedDriver.getDrivingLicenseCountryCode());
        assertEquals(driver.getDateOfBirth(), capturedDriver.getDateOfBirth());
    }
}