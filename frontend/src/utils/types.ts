export interface Car {
    id: number;
    vin: string;
    plateNumber: string;
    countryCode: string;
    driver: Driver;
    locations: Location[];
}

export interface Driver {
    id: number;
    name: string;
    surname: string;
    drivingLicenseNumber: string;
    drivingLicenseCountryCode: string;
    dateOfBirth: string;
}

export interface Location {
    id: number;
    longitude: number;
    latitude: number;
}
