package com.wust.advanced.web.utils;

import com.wust.advanced.web.car.model.Car;
import com.wust.advanced.web.location.model.Location;
import com.wust.advanced.web.user.driver.model.Driver;
import com.wust.advanced.web.user.model.FMUser;
import com.wust.advanced.web.utils.dto.CarDto;
import com.wust.advanced.web.utils.dto.DriverDto;
import com.wust.advanced.web.utils.dto.FMUserDto;
import com.wust.advanced.web.utils.dto.LocationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityToDtoMapper {
    CarDto carToCarDTO(Car car);
    Car carDTOToCar(CarDto carDto);
    Location locationDTOToLocation(LocationDto locationDto);
    LocationDto locationToLocationDTO(Location location);
    Driver driverDtoToDriver(DriverDto driverDto);
    DriverDto driverToDriverDto(Driver driver);
    FMUserDto userToUserDto(FMUserDto fmUser);
    FMUser userDtoToUser(FMUserDto fmUserDto);
}