package com.wust.advanced.web.location.repository;

import com.wust.advanced.web.location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByCar_Id(Long carId);
    void deleteAllByCar_Id(Long carId);
}