import {ComponentFixture, TestBed} from '@angular/core/testing';
import {VtNavbarComponent} from './vt-navbar.component';
import {By} from '@angular/platform-browser';
import {Router} from '@angular/router';

describe('VtNavbarComponent', () => {
    let component: VtNavbarComponent;
    let fixture: ComponentFixture<VtNavbarComponent>;
    let routerSpy: jasmine.SpyObj<Router>;

    beforeEach(async () => {
        routerSpy = jasmine.createSpyObj('Router', ['navigate']);

        await TestBed.configureTestingModule({
            imports: [
                VtNavbarComponent
            ],
            providers: [
                {provide: Router, useValue: routerSpy}
            ]
        })
            .compileComponents();

        fixture = TestBed.createComponent(VtNavbarComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should have cursor: pointer style on logo', () => {
        const logo = fixture.debugElement.query(By.css('img'));
        const styles = getComputedStyle(logo.nativeElement);
        expect(styles.cursor).toBe('pointer');
    });

    it('should navigate to /all-cars when logo is clicked', () => {
        const logo = fixture.debugElement.query(By.css('img'));
        logo.triggerEventHandler('click', null);

        expect(routerSpy.navigate).toHaveBeenCalledWith(['/all-cars']);
    });
});
