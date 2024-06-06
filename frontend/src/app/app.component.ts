import {Component, OnInit} from '@angular/core';
import {AllCarsComponent} from "./all-cars/all-cars.component";
import {VtNavbarComponent} from "./common/vt-navbar/vt-navbar.component";
import {GoogleApiService} from "../utils/auth/google-api.service";
import {CommonModule} from "@angular/common";
import {RouterOutlet} from "@angular/router";
import {CarDetailsComponent} from "./car-details/car-details.component";

@Component({
    selector: 'vt-root',
    standalone: true,

    imports: [RouterOutlet, AllCarsComponent, VtNavbarComponent, CommonModule, CarDetailsComponent],
    templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
    userName: string = '';

    constructor(private readonly googleApi: GoogleApiService) {
    }

    ngOnInit(): void {
        this.googleApi.userProfileSubject.subscribe(info => {
            this.userName = info['info'].name;
        });
    }

    isLoggedIn(): boolean {
        return this.googleApi.isLoggedIn();
    }

    logout() {
        this.googleApi.signOut();
    }
}
