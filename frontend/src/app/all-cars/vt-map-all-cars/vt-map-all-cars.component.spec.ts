import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VtMapAllCarsComponent } from './vt-map-all-cars.component';

describe('VtMapComponent', () => {
  let component: VtMapAllCarsComponent;
  let fixture: ComponentFixture<VtMapAllCarsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VtMapAllCarsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VtMapAllCarsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
