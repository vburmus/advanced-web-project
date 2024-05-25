package com.wust.advanced.web.car.service;

import com.wust.advanced.web.car.model.Car;
import com.wust.advanced.web.car.repository.CarRepository;
import com.wust.advanced.web.location.model.Location;
import com.wust.advanced.web.location.service.LocationService;
import com.wust.advanced.web.user.driver.service.DriverService;
import com.wust.advanced.web.utils.EntityToDtoMapper;
import com.wust.advanced.web.utils.dto.CarDto;
import com.wust.advanced.web.utils.dto.LocationDto;
import com.wust.advanced.web.utils.exceptions.ItemExistsException;
import com.wust.advanced.web.utils.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@RequiredArgsConstructor
public class CarService {
    public static final String CAR_WITH_VIN_ALREADY_EXISTS = "Car with vin: %s already exists";
    public static final String CAR_WITH_ID_NOT_FOUND = "Car with id: %d not found";
    private final CarRepository carRepository;
    private final EntityToDtoMapper entityToDtoMapper;
    private final LocationService locationService;
    private final DriverService driverService;

    public CarDto create(CarDto carDTO) {
        Car car = entityToDtoMapper.carDTOToCar(carDTO);
        if (isCarExists(car))
            throw new ItemExistsException(String.format(CAR_WITH_VIN_ALREADY_EXISTS, car.getVin()));
        return entityToDtoMapper.carToCarDTO(carRepository.save(car));
    }

    public Page<CarDto> readAll(Pageable pageable) {
        return carRepository.findAll(pageable).map(entityToDtoMapper::carToCarDTO);
    }

    public CarDto readById(Long id) {
        return entityToDtoMapper.carToCarDTO(readCarById(id));
    }

    public boolean isCarExists(Car car) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                                               .withIgnorePaths("id")
                                               .withIgnorePaths("driver")
                                               .withIgnorePaths("locations")
                                               .withMatcher("vin", exact())
                                               .withMatcher("plateNumber", exact())
                                               .withMatcher("countryCode", exact());
        return carRepository.exists(Example.of(car, matcher));
    }

    public LocationDto updateLocation(LocationDto locationDTO, Long id) {
        Location location = entityToDtoMapper.locationDTOToLocation(locationDTO);
        Car car = readCarById(id);
        location.setCar(car);
        return locationService.create(location);
    }

    public CarDto assignDriver(Long id, Long driverId) {
        Car car = readCarById(id);
        car.setDriver(driverService.getById(driverId));
        return entityToDtoMapper.carToCarDTO(carRepository.save(car));
    }

    @Transactional
    public void deleteLocations(Long id) {
        Car car = readCarById(id);
        locationService.deleteAllByCarId(car.getId());
    }

    private Car readCarById(Long id) {
        return carRepository.findById(id)
                            .orElseThrow(() -> new ItemNotFoundException(String.format(CAR_WITH_ID_NOT_FOUND, id)));
    }
}