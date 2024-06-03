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
    drivingLicenseNumber: string;
    drivingLicenseCountryCode: string;
    dateOfBirth: string;
    user: FMUser;
}

export interface FMUser {
    id: number;
    name: string;
    surname: string;
}

export interface Location {
    id: number;
    longitude: number;
    latitude: number;
}
