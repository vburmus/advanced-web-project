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
                    drivingLicenseNumber: 'DL1',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1980-01-01',
                    user: {
                        id: 1,
                        name: 'John',
                        surname: 'Doe'
                    }
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
                    drivingLicenseNumber: 'DL2',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1982-01-01',
                    user: {
                        id: 1,
                        name: 'John',
                        surname: 'Doe'
                    }
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
                    drivingLicenseNumber: 'DL3',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1985-01-01',
                    user: {
                        id: 1,
                        name: 'John',
                        surname: 'Doe'
                    }
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
                    drivingLicenseNumber: 'DL4',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1990-01-01',
                    user: {
                        id: 1,
                        name: 'John',
                        surname: 'Doe'
                    }
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
                    drivingLicenseNumber: 'DL5',
                    drivingLicenseCountryCode: 'US',
                    dateOfBirth: '1995-01-01',
                    user: {
                        id: 1,
                        name: 'John',
                        surname: 'Doe'
                    }
                },
                locations: [
                    { id: 5, longitude: 17.060965, latitude: 51.107152 }
                ]
            }
        ];

        return of(mockCars);
    }

    getCarById(id: string): Observable<Car> {
        const mockCar: Car = {
            id: 1,
            vin: 'VIN1',
            plateNumber: 'PLATE1',
            countryCode: 'US',
            driver: {
                id: 1,
                drivingLicenseNumber: 'DL1',
                drivingLicenseCountryCode: 'US',
                dateOfBirth: '1980-01-01',
                user: {
                    id: 1,
                    name: 'John',
                    surname: 'Doe'
                }
            },
            locations: [
                { id: 1, longitude: 17.058006, latitude: 51.110754 }
            ]
        };

        return of(mockCar);
    }
}