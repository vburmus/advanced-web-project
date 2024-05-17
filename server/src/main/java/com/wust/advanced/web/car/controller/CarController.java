package com.wust.advanced.web.car.controller;

import com.wust.advanced.web.car.service.CarService;
import com.wust.advanced.web.location.service.LocationService;
import com.wust.advanced.web.utils.dto.CarDto;
import com.wust.advanced.web.utils.dto.LocationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final LocationService locationService;
    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarDto> create(@RequestBody @Valid CarDto carDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(carService.create(carDTO));
    }

    @GetMapping
    public ResponseEntity<Page<CarDto>> readAll(Pageable pageable) {
        return ResponseEntity.ok(carService.readAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.readById(id));
    }

    @PostMapping("/{id}/location")
    public ResponseEntity<LocationDto> updateLocation(@RequestBody @Valid LocationDto locationDTO,
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(carService.updateLocation(locationDTO, id));
    }
    @GetMapping(path = "/{id}/locations", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Page<LocationDto>>> locationStream(@PathVariable Long id,
                                                                   @PageableDefault Pageable pageable) {
        Page<LocationDto> initialPage = locationService.readAllByCarId(id, pageable);

        Flux<ServerSentEvent<Page<LocationDto>>> initialData = Flux.just(ServerSentEvent.<Page<LocationDto>>builder()
                                                                                        .data(initialPage)
                                                                                        .build());
        Flux<ServerSentEvent<Page<LocationDto>>> periodicUpdates = Flux.interval(Duration.ofSeconds(5))
                                                                       .flatMap(sequence -> {
                                                                           Page<LocationDto> updatedPage =
                                                                                   locationService.readAllByCarId(id, pageable);
                                                                           return Flux.just(ServerSentEvent.<Page<LocationDto>>builder()
                                                                                                           .id(String.valueOf(sequence))
                                                                                                           .data(updatedPage)
                                                                                                           .build());
                                                                       });

        return Flux.concat(initialData, periodicUpdates);
    }

    @GetMapping("/{id}/assign-driver/{driverId}")
    public ResponseEntity<CarDto> assignDriver(@PathVariable Long id, @PathVariable Long driverId) {
        return ResponseEntity.ok(carService.assignDriver(id, driverId));
    }
}