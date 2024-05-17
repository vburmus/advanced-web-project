package com.wust.advanced.web.user.driver.controller;

import com.wust.advanced.web.user.driver.service.DriverService;
import com.wust.advanced.web.utils.dto.DriverDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @GetMapping
    public ResponseEntity<Page<DriverDto>> readAll(Pageable pageable) {
        return ResponseEntity.ok(driverService.readAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> readById(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.readById(id));
    }

    @PostMapping
    public ResponseEntity<DriverDto> create(@RequestBody DriverDto driverDto) {
        return ResponseEntity.ok(driverService.create(driverDto));
    }
}