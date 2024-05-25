package com.wust.advanced.web.user.driver.model;

import com.wust.advanced.web.car.model.Car;
import com.wust.advanced.web.user.model.FMUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20)
    private String drivingLicenseNumber;
    @Column(nullable = false, length = 3)
    private String drivingLicenseCountryCode;
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    @OneToOne(mappedBy = "driver")
    private Car car;
    @OneToOne
    private FMUser user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        return new EqualsBuilder().appendSuper(super.equals(o))
                                  .append(drivingLicenseNumber, driver.drivingLicenseNumber)
                                  .append(drivingLicenseCountryCode, driver.drivingLicenseCountryCode)
                                  .append(dateOfBirth, driver.dateOfBirth)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode())
                                          .append(drivingLicenseNumber)
                                          .append(drivingLicenseCountryCode)
                                          .append(dateOfBirth)
                                          .toHashCode();
    }
}