import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {GoogleApiService} from "../../../utils/auth/google-api.service";
import {RouterOutlet} from "@angular/router";
import {VtNavbarComponent} from "../../common/vt-navbar/vt-navbar.component";

@Component({
    selector: 'vt-welcome-page',
    standalone: true,
    imports: [
        RouterOutlet,
        VtNavbarComponent
    ],
    templateUrl: './welcome-page.component.html',
    styleUrl: './welcome-page.component.scss'
})
export class WelcomePageComponent implements OnInit {
    @Output() usernameEmitted = new EventEmitter<string>();

    constructor(private readonly googleApi: GoogleApiService) {
    }
    ngOnInit(): void {
        this.googleApi.userProfileSubject.subscribe(info => {
            this.usernameEmitted.emit(info['info'].name);
        });
    }

    logIn(): void {
        this.googleApi.signIn();
        this.googleApi.userProfileSubject.subscribe(info => {
            this.usernameEmitted.emit(info['info'].name);
        });
    }
}
