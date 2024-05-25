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
      vin: 'VIN123',
      plateNumber: 'ABC123',
      countryCode: 'US',
      driver: {
        id: 1,
        name: 'John',
        surname: 'Doe',
        drivingLicenseNumber: 'DL123',
        drivingLicenseCountryCode: 'US',
        dateOfBirth: '1990-01-01'
      },
      locations: []
    };
    fixture.detectChanges();

    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('h2').textContent).toContain('Car 1');
    expect(compiled.querySelector('p:nth-child(2)').textContent).toContain('VIN: VIN123');
    expect(compiled.querySelector('p:nth-child(3)').textContent).toContain('Plate Number: ABC123');
    expect(compiled.querySelector('p:nth-child(4)').textContent).toContain('Country Code: US');
  });
});
