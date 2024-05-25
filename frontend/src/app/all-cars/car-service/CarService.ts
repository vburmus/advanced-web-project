import {Observable, of} from "rxjs";
import {Car} from "../../../utils/types";
import {Injectable} from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class CarService {
    constructor() {}

    getNearbyCars(): Observable<Car[]> {
        const mockCars: Car[] = [
            {
                id: 1,
                vin: 'VIN1',
                plateNumber: 'PLATE1',
                countryCode: 'US',
                driver: {
                    id: 1,
                    name: 'John',
                    surname: 'Doe',
                    drivingLicenseNumber: 'DL1',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1980-01-01'
                },
                locations: [
                    { id: 1, longitude: 17.058006, latitude: 51.110754 }
                ]
            },
            {
                id: 2,
                vin: 'VIN2',
                plateNumber: 'PLATE2',
                countryCode: 'US',
                driver: {
                    id: 2,
                    name: 'Jane',
                    surname: 'Doe',
                    drivingLicenseNumber: 'DL2',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1982-01-01'
                },
                locations: [
                    { id: 2, longitude: 17.063450, latitude: 51.110285 }
                ]
            },
            {
                id: 3,
                vin: 'VIN3',
                plateNumber: 'PLATE3',
                countryCode: 'US',
                driver: {
                    id: 3,
                    name: 'Alice',
                    surname: 'Smith',
                    drivingLicenseNumber: 'DL3',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1985-01-01'
                },
                locations: [
                    { id: 3, longitude: 17.062570, latitude: 51.112313 }
                ]
            },
            {
                id: 4,
                vin: 'VIN4',
                plateNumber: 'PLATE4',
                countryCode: 'US',
                driver: {
                    id: 4,
                    name: 'Bob',
                    surname: 'Johnson',
                    drivingLicenseNumber: 'DL4',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1990-01-01'
                },
                locations: [
                    { id: 4, longitude: 17.055268, latitude: 51.112973 }
                ]
            },
            {
                id: 5,
                vin: 'VIN5',
                plateNumber: 'PLATE5',
                countryCode: 'US',
                driver: {
                    id: 5,
                    name: 'Charlie',
                    surname: 'Brown',
                    drivingLicenseNumber: 'DL5',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1995-01-01'
                },
                locations: [
                    { id: 5, longitude: 17.060965, latitude: 51.107152 }
                ]
            }
        ];

        return of(mockCars);
    }
}