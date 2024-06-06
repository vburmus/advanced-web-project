import {bootstrapApplication} from '@angular/platform-browser';
import {AppComponent} from './app/app.component';
import {provideHttpClient} from "@angular/common/http";
import {provideOAuthClient} from "angular-oauth2-oidc";
bootstrapApplication(AppComponent, {
    providers: [
        provideHttpClient(),
        provideOAuthClient()
    ]
})    .catch((err) => console.error(err));
