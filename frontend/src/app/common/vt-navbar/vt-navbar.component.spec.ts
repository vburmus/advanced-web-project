import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VtNavbarComponent } from './vt-navbar.component';

describe('VtNavbarComponent', () => {
  let component: VtNavbarComponent;
  let fixture: ComponentFixture<VtNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VtNavbarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VtNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
