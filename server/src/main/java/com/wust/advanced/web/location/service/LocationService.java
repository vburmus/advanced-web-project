package com.wust.advanced.web.location.service;

import com.wust.advanced.web.location.model.Location;
import com.wust.advanced.web.location.repository.LocationRepository;
import com.wust.advanced.web.utils.EntityToDtoMapper;
import com.wust.advanced.web.utils.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final EntityToDtoMapper entityToDtoMapper;

    public LocationDto create(Location location) {
        return entityToDtoMapper.locationToLocationDTO(locationRepository.save(location));
    }

    public List<LocationDto> readAllByCarId(Long carId) {
        return locationRepository.findAllByCar_Id(carId).stream().map(entityToDtoMapper::locationToLocationDTO).toList();
    }

    public void deleteAllByCarId(Long carId) {
        locationRepository.deleteAllByCar_Id(carId);
    }
}