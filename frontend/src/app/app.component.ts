import {Component, OnInit} from '@angular/core';
import {AllCarsComponent} from "./all-cars/all-cars.component";
import {VtNavbarComponent} from "./common/vt-navbar/vt-navbar.component";
import {CommonModule} from "@angular/common";
import {RouterOutlet} from "@angular/router";
import {CarDetailsComponent} from "./car-details/car-details.component";
import {WelcomePageComponent} from "./welcome-page/welcome-page/welcome-page.component";
import {GoogleApiService} from "../utils/auth/google-api.service";
import {Observable, of} from "rxjs";

@Component({
    selector: 'vt-root',
    standalone: true,

    imports: [RouterOutlet, AllCarsComponent, VtNavbarComponent, CommonModule, CarDetailsComponent, WelcomePageComponent],
    templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
    username: string | undefined;
    isLoggedIn: boolean = false;

    userObservable: Observable<string | undefined> = of(undefined);

    constructor(private readonly googleApi: GoogleApiService) {
    }

    ngOnInit(): void {
        this.username = this.googleApi.getUserName();
        this.userObservable = of(this.username);
        this.isLoggedIn = this.googleApi.isLoggedIn();
    }

    logInHandler(userName: string): void {
        this.isLoggedIn = true;
        this.username = userName;
        this.userObservable = of(userName);
    }

    logout() {
        this.googleApi.signOut();
    }
}
