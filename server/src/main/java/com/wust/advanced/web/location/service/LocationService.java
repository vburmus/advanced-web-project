package com.wust.advanced.web.location.service;

import com.wust.advanced.web.location.model.Location;
import com.wust.advanced.web.location.repository.LocationRepository;
import com.wust.advanced.web.utils.EntityToDtoMapper;
import com.wust.advanced.web.utils.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;
    private final EntityToDtoMapper entityToDtoMapper;

    public LocationDto create(Location location) {
        return entityToDtoMapper.locationToLocationDTO(locationRepository.save(location));
    }

    public Page<LocationDto> readAllByCarId(Long carId, Pageable pageable) {
        return locationRepository.findAllByCar_Id(carId, pageable).map(entityToDtoMapper::locationToLocationDTO);
    }

    public void deleteAllByCarId(Long carId) {
        locationRepository.deleteAllByCar_Id(carId);
    }
}