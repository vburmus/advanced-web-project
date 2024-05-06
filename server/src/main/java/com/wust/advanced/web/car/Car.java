package com.wust.advanced.web.car;

import com.wust.advanced.web.driver.Driver;
import com.wust.advanced.web.location.Location;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String vin;
    @Column(length = 15)
    private String plateNumber;
    @Column(length = 3)
    private String countryCode;
    @OneToOne
    private Driver driver;
    @OneToMany
    private List<Location> locations;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return new EqualsBuilder().append(id, car.id)
                                  .append(vin, car.vin)
                                  .append(plateNumber, car.plateNumber)
                                  .append(countryCode, car.countryCode)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(vin).append(plateNumber).append(countryCode).toHashCode();
    }
}