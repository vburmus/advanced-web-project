package com.wust.advanced.web.car.service;

import com.wust.advanced.web.car.model.Car;
import com.wust.advanced.web.car.repository.CarRepository;
import com.wust.advanced.web.user.driver.model.Driver;
import com.wust.advanced.web.user.driver.service.DriverService;
import com.wust.advanced.web.location.model.Location;
import com.wust.advanced.web.location.service.LocationService;
import com.wust.advanced.web.utils.EntityToDtoMapper;
import com.wust.advanced.web.utils.dto.CarDto;
import com.wust.advanced.web.utils.dto.LocationDto;
import com.wust.advanced.web.utils.exceptions.ItemExistsException;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private EntityToDtoMapper entityToDtoMapper;

    @Mock
    private LocationService locationService;

    @Mock
    private DriverService driverService;

    @InjectMocks
    private CarService carService;

    private Car car;
    private CarDto carDto;
    private Location location;
    private LocationDto locationDto;
    private Driver driver;

    @Captor
    private ArgumentCaptor<Example<Car>> exampleCaptor;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId(1L);
        car.setVin("1HGBH41JXMN109186");
        car.setPlateNumber("XYZ123");
        car.setCountryCode("USA");

        carDto = new CarDto(
                1L,
                "1HGBH41JXMN109186",
                "XYZ123",
                "USA",
                null,
                null
        );

        location = new Location();
        location.setId(1L);

        locationDto = new LocationDto(
                1L,
                40.7128,
                -74.0060
        );

        driver = new Driver();
        driver.setId(1L);
    }

    @Test
    void create() {
        when(entityToDtoMapper.carDTOToCar(carDto)).thenReturn(car);
        when(carRepository.exists(any(Example.class))).thenReturn(false);
        when(carRepository.save(car)).thenReturn(car);
        when(entityToDtoMapper.carToCarDTO(car)).thenReturn(carDto);

        CarDto result = carService.create(carDto);

        assertEquals(carDto, result);
        verify(carRepository).save(car);
    }

    @Test
    void createCarExists() {
        when(entityToDtoMapper.carDTOToCar(carDto)).thenReturn(car);
        when(carRepository.exists(any(Example.class))).thenReturn(true);

        ItemExistsException exception = assertThrows(ItemExistsException.class, () -> {
            carService.create(carDto);
        });

        assertEquals(String.format(CarService.CAR_WITH_VIN_ALREADY_EXISTS, car.getVin()), exception.getMessage());
    }

    @Test
    void readAll() {
        Page<Car> carsPage = new PageImpl<>(List.of(car));
        when(carRepository.findAll(any(Pageable.class))).thenReturn(carsPage);
        when(entityToDtoMapper.carToCarDTO(car)).thenReturn(carDto);

        Pageable pageable = PageRequest.of(0, 10);
        Page<CarDto> result = carService.readAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(carDto, result.getContent().get(0));
    }

    @Test
    void readById() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(entityToDtoMapper.carToCarDTO(car)).thenReturn(carDto);

        CarDto result = carService.readById(1L);

        assertEquals(carDto, result);
    }

    @Test
    void readByIdNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            carService.readById(1L);
        });

        assertEquals(String.format(CarService.CAR_WITH_ID_NOT_FOUND, 1L), exception.getMessage());
    }

    @Test
    void isCarExists() {
        when(carRepository.exists(any(Example.class))).thenReturn(true);

        boolean exists = carService.isCarExists(car);

        assertTrue(exists);
        verify(carRepository).exists(exampleCaptor.capture());

        Example<Car> capturedExample = exampleCaptor.getValue();
        Car capturedCar = capturedExample.getProbe();
        assertEquals(car.getVin(), capturedCar.getVin());
        assertEquals(car.getPlateNumber(), capturedCar.getPlateNumber());
        assertEquals(car.getCountryCode(), capturedCar.getCountryCode());
    }

    @Test
    void updateLocation() {
        when(entityToDtoMapper.locationDTOToLocation(locationDto)).thenReturn(location);
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(locationService.create(location)).thenReturn(locationDto);

        LocationDto result = carService.updateLocation(locationDto, 1L);

        assertEquals(locationDto, result);
        verify(locationService).create(location);
        assertEquals(car, location.getCar());
    }

    @Test
    void updateLocationCarNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            carService.updateLocation(locationDto, 1L);
        });

        assertEquals(String.format(CarService.CAR_WITH_ID_NOT_FOUND, 1L), exception.getMessage());
    }

    @Test
    void assignDriver() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(driverService.getById(1L)).thenReturn(driver);
        when(carRepository.save(car)).thenReturn(car);
        when(entityToDtoMapper.carToCarDTO(car)).thenReturn(carDto);

        CarDto result = carService.assignDriver(1L, 1L);

        assertEquals(carDto, result);
        verify(carRepository).save(car);
        assertEquals(driver, car.getDriver());
    }

    @Test
    void assignDriverCarNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            carService.assignDriver(1L, 1L);
        });

        assertEquals(String.format(CarService.CAR_WITH_ID_NOT_FOUND, 1L), exception.getMessage());
    }
}