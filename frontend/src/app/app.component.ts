import {Component} from '@angular/core';
import {AllCarsComponent} from "./all-cars/all-cars.component";
import {VtNavbarComponent} from "./common/vt-navbar/vt-navbar.component";
import {GoogleApiService} from "../utils/auth/google-api.service";
import {UserInfo} from "angular-oauth2-oidc";
import {lastValueFrom} from "rxjs";
import {CommonModule} from "@angular/common";
import {CarDetailsComponent} from "../car-details/car-details.component";
import {RouterOutlet} from "@angular/router";

@Component({
    selector: 'vt-root',
    standalone: true,

    imports: [RouterOutlet, AllCarsComponent, VtNavbarComponent, CommonModule, CarDetailsComponent],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss'
})
export class AppComponent {
    title = 'angular-google-oauth-example';

    mailSnippets: string[] = []
    userInfo?: UserInfo

    constructor(private readonly googleApi: GoogleApiService) {
        googleApi.userProfileSubject.subscribe(info => {
            this.userInfo = info
        })
    }

    isLoggedIn(): boolean {
        return this.googleApi.isLoggedIn()
    }

    logout() {
        this.googleApi.signOut()
    }

    async getEmails() {
        if (!this.userInfo) {
            return;
        }

        const userId = this.userInfo.sub
        const messages = await lastValueFrom(this.googleApi.emails(userId))
        messages.messages.forEach((element: any) => {
            const mail = lastValueFrom(this.googleApi.getMail(userId, element.id))
            mail.then(mail => {
                this.mailSnippets.push(mail.snippet)
            })
        });
    }
}
