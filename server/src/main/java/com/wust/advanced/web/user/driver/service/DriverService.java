package com.wust.advanced.web.user.driver.service;

import com.wust.advanced.web.user.driver.model.Driver;
import com.wust.advanced.web.user.driver.repository.DriverRepository;
import com.wust.advanced.web.user.model.FMUser;
import com.wust.advanced.web.user.service.UserService;
import com.wust.advanced.web.utils.EntityToDtoMapper;
import com.wust.advanced.web.utils.dto.DriverDto;
import com.wust.advanced.web.utils.exceptions.ItemExistsException;
import com.wust.advanced.web.utils.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@RequiredArgsConstructor
public class DriverService {
    public static final String DRIVER_NOT_FOUND = "Driver with id %s not found";
    public static final String DRIVER_ALREADY_EXISTS = "Driver with license %s:%s or such user already exists ";
    private final DriverRepository driverRepository;
    private final EntityToDtoMapper entityToDtoMapper;
    private final UserService userService;

    public Page<DriverDto> readAll(Pageable pageable) {
        return driverRepository.findAll(pageable).map(entityToDtoMapper::driverToDriverDto);
    }

    public DriverDto readById(Long id) {
        return entityToDtoMapper.driverToDriverDto(getById(id));
    }

    public Driver getById(Long id) {
        return driverRepository.findById(id)
                               .orElseThrow(() -> new ItemNotFoundException(String.format(DRIVER_NOT_FOUND, id)));
    }

    @Transactional
    public DriverDto create(DriverDto driverDto) {
        Driver driver = entityToDtoMapper.driverDtoToDriver(driverDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt token)) {
            throw new AuthenticationCredentialsNotFoundException("Expected authentication principal is not present");
        }
        FMUser user = FMUser.builder()
                .name(token.getClaimAsString("given_name"))
                .surname(token.getClaimAsString("family_name")).build();
        userService.create(user);
        driver.setUser(user);
        if (isDriverExists(driver)) {
            throw new ItemExistsException(String.format(DRIVER_ALREADY_EXISTS,
                    driver.getDrivingLicenseCountryCode(), driver.getDrivingLicenseNumber()));
        }

        return entityToDtoMapper.driverToDriverDto(driverRepository.save(driver));
    }

    public boolean isDriverExists(Driver driver) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withMatcher("drivingLicenseNumber", exact())
                                               .withMatcher("drivingLicenseCountryCode", exact())
                                               .withMatcher("dateOfBirth", exact());
        return driverRepository.exists(Example.of(driver, matcher));
    }
}