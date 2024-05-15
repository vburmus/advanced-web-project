package com.wust.advanced.web.location.service;

import com.wust.advanced.web.location.model.Location;
import com.wust.advanced.web.location.repository.LocationRepository;
import com.wust.advanced.web.utils.EntityToDtoMapper;
import com.wust.advanced.web.utils.dto.LocationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private EntityToDtoMapper entityToDtoMapper;
    @InjectMocks
    private LocationService locationService;

    @Test
    void create() {
        // Given
        Location location = Location.builder()
                                    .id(1L)
                                    .longitude(10.0)
                                    .latitude(20.0)
                                    .build();
        LocationDto locationDto = new LocationDto(1L, 10.0, 20.0);
        when(locationRepository.save(Mockito.any(Location.class))).thenReturn(location);
        when(entityToDtoMapper.locationToLocationDTO(location)).thenReturn(locationDto);

        // When
        LocationDto result = locationService.create(location);

        // Then
        assertEquals(locationDto, result);
    }

    @Test
    void readAllByCarId() {
        // Given
        Long carId = 1L;
        Pageable pageable = Pageable.ofSize(10);
        List<Location> locations = new ArrayList<>();
        locations.add(Location.builder().id(1L).longitude(10.0).latitude(20.0).build());
        locations.add(Location.builder().id(2L).longitude(30.0).latitude(40.0).build());
        Page<Location> locationPage = new PageImpl<>(locations);

        when(locationRepository.findAllByCar_Id(carId, pageable)).thenReturn(locationPage);

        List<LocationDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(new LocationDto(1L, 10.0, 20.0));
        expectedDtos.add(new LocationDto(2L, 30.0, 40.0));

        when(entityToDtoMapper.locationToLocationDTO(locations.get(0))).thenReturn(expectedDtos.get(0));
        when(entityToDtoMapper.locationToLocationDTO(locations.get(1))).thenReturn(expectedDtos.get(1));

        // When
        Page<LocationDto> resultPage = locationService.readAllByCarId(carId, pageable);

        // Then
        assertEquals(expectedDtos.size(), resultPage.getContent().size());
        assertEquals(expectedDtos.get(0), resultPage.getContent().get(0));
        assertEquals(expectedDtos.get(1), resultPage.getContent().get(1));
    }
}