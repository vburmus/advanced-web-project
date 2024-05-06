package com.wust.advanced.web.driver;

import com.wust.advanced.web.user.FMUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Driver extends FMUser {
    @Column(nullable = false, length = 20)
    private String drivingLicenseNumber;
    @Column(nullable = false, length = 3)
    private String drivingLicenseCountryCode;
    @Column(nullable = false)
    private LocalDate dateOfBirth;

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