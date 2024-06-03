import {ComponentFixture, TestBed} from '@angular/core/testing';
import {CarCardComponent} from './car-card.component';

describe('CarCardComponent', () => {
    let component: CarCardComponent;
    let fixture: ComponentFixture<CarCardComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [CarCardComponent],
        }).compileComponents();

        fixture = TestBed.createComponent(CarCardComponent);
        component = fixture.componentInstance;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should display car details', () => {
        component.car = {
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
        fixture.detectChanges();

        const compiled = fixture.nativeElement;
        expect(compiled.querySelector('h2').textContent).toContain('Car 1');
        expect(compiled.querySelector('p:nth-child(2)').textContent).toContain('VIN: VIN1');
        expect(compiled.querySelector('p:nth-child(3)').textContent).toContain('Plate Number: PLATE1');
        expect(compiled.querySelector('p:nth-child(4)').textContent).toContain('Country Code: US');
    });
});
