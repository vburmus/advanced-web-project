package com.wust.advanced.web.user.driver.repository;

import com.wust.advanced.web.user.driver.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {
    boolean existsByUser_Id(Long id);
}