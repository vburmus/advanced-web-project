package com.wust.advanced.web.car.controller;

import com.wust.advanced.web.location.service.LocationService;
import com.wust.advanced.web.utils.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final LocationService locationService;
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
}