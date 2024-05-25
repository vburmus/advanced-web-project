package com.wust.advanced.web.location.repository;

import com.wust.advanced.web.location.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findAllByCar_Id(Long carId, Pageable pageable);
    void deleteAllByCar_Id(Long carId);
}