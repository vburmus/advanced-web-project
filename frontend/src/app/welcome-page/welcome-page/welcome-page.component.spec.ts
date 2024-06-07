import { ComponentFixture, TestBed } from '@angular/core/testing';
import { WelcomePageComponent } from './welcome-page.component';
import { GoogleApiService } from '../../../utils/auth/google-api.service';
import { Subject } from 'rxjs';
import { UserInfo } from 'angular-oauth2-oidc';

describe('WelcomePageComponent', () => {
  let component: WelcomePageComponent;
  let fixture: ComponentFixture<WelcomePageComponent>;
  let googleApiService: jasmine.SpyObj<GoogleApiService>;

  beforeEach(() => {
    googleApiService = jasmine.createSpyObj('GoogleApiService', ['signIn', 'userProfileSubject']);
    googleApiService.userProfileSubject = new Subject<UserInfo>();

    TestBed.configureTestingModule({
      providers: [{ provide: GoogleApiService, useValue: googleApiService }],
    }).compileComponents();

    fixture = TestBed.createComponent(WelcomePageComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call Google API sign-in method', () => {
    component.logIn();
    expect(googleApiService.signIn).toHaveBeenCalled();
  });
});
