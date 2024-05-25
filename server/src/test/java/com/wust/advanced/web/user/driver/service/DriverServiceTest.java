package com.wust.advanced.web.user.driver.service;

import com.wust.advanced.web.user.driver.model.Driver;
import com.wust.advanced.web.user.driver.repository.DriverRepository;
import com.wust.advanced.web.user.model.FMUser;
import com.wust.advanced.web.user.service.UserService;
import com.wust.advanced.web.utils.EntityToDtoMapper;
import com.wust.advanced.web.utils.dto.DriverDto;
import com.wust.advanced.web.utils.exceptions.ItemExistsException;
import com.wust.advanced.web.utils.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private EntityToDtoMapper entityToDtoMapper;
    @Mock
    private UserService userService;

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
                       .drivingLicenseNumber("ABC123")
                       .drivingLicenseCountryCode("USA")
                       .dateOfBirth(LocalDate.of(1980, 1, 1))
                       .build();

        driverDto = new DriverDto(
                1L,
                "ABC123",
                "USA",
                LocalDate.of(1980, 1, 1),
                null
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
    void createSuccessfulCreation() {
        // given
        DriverDto driverDto = new DriverDto(1L, "licenseNumber", "licenseCountry", LocalDate.now(), null);
        Driver driver = new Driver();
        when(entityToDtoMapper.driverDtoToDriver(driverDto)).thenReturn(driver);
        Authentication authentication = mock(Authentication.class);
        DefaultOidcUser oidcUser = mock(DefaultOidcUser.class);
        when(authentication.getPrincipal()).thenReturn(oidcUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(oidcUser.getEmail()).thenReturn("test@example.com");
        FMUser user = new FMUser();
        when(userService.getByEmail("test@example.com")).thenReturn(user);
        when(driverRepository.exists(any())).thenReturn(false);
        when(driverRepository.save(driver)).thenReturn(driver);
        when(entityToDtoMapper.driverToDriverDto(driver)).thenReturn(driverDto);

        // when
        DriverDto result = driverService.create(driverDto);

        // then
        assertEquals(driverDto, result);
        verify(driverRepository).save(driver);
    }

    @Test
    void createDriverExists() {
        // given
        DriverDto driverDto = new DriverDto(1L, "licenseNumber", "licenseCountry", LocalDate.now(), null);
        Driver driver = Driver.builder().drivingLicenseNumber("licenseNumber").drivingLicenseCountryCode("licenseCountry").dateOfBirth(LocalDate.now()).build();
        when(entityToDtoMapper.driverDtoToDriver(driverDto)).thenReturn(driver);
        Authentication authentication = mock(Authentication.class);
        DefaultOidcUser oidcUser = mock(DefaultOidcUser.class);
        when(authentication.getPrincipal()).thenReturn(oidcUser);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(oidcUser.getEmail()).thenReturn("test@example.com");
        FMUser user = new FMUser();
        when(userService.getByEmail("test@example.com")).thenReturn(user);
        when(driverRepository.exists(any())).thenReturn(true);

        // when
        ItemExistsException exception = assertThrows(ItemExistsException.class, () -> {
            driverService.create(driverDto);
        });
        //then
        assertEquals("Driver with license licenseCountry:licenseNumber or such user already exists ",
                exception.getMessage());
        verify(driverRepository, never()).save(driver);
    }

    @Test
    void createNoAuthentication() {
        // given
        DriverDto driverDto = new DriverDto(1L, "licenseNumber", "licenseCountry", LocalDate.now(), null);
        Driver driver = new Driver();
        when(entityToDtoMapper.driverDtoToDriver(driverDto)).thenReturn(driver);
        SecurityContextHolder.getContext().setAuthentication(null);

        // when
        AuthenticationCredentialsNotFoundException exception = assertThrows(AuthenticationCredentialsNotFoundException.class, () -> {
            driverService.create(driverDto);
        });
        //then
        assertEquals("Expected authentication principal is not present", exception.getMessage());
        verify(driverRepository, never()).save(driver);
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