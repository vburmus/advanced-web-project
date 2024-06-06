import {Injectable} from '@angular/core';
import {AuthConfig, OAuthService, UserInfo} from 'angular-oauth2-oidc';
import {Subject} from 'rxjs';
import {environment} from "../../environments/environment";

const authCodeFlowConfig: AuthConfig = {
    issuer: 'https://accounts.google.com',
    strictDiscoveryDocumentValidation: false,
    redirectUri: window.location.origin,
    clientId: environment.clientId,
    scope: 'openid profile email https://www.googleapis.com/auth/gmail.readonly',
    showDebugInformation: true,
};


@Injectable({
    providedIn: 'root'
})
export class GoogleApiService {
    userProfileSubject = new Subject<UserInfo>();

    constructor(private readonly oAuthService: OAuthService) {
        oAuthService.configure(authCodeFlowConfig);
        oAuthService.logoutUrl = "https://www.google.com/accounts/Logout";
        oAuthService.loadDiscoveryDocument().then(() => {
            oAuthService.tryLoginImplicitFlow().then(() => {
                if (!oAuthService.hasValidAccessToken()) {
                    oAuthService.initLoginFlow()
                } else {
                    oAuthService.loadUserProfile().then((userProfile) => {
                        this.userProfileSubject.next(userProfile as UserInfo)
                    })
                }
            })
        });
    }

    isLoggedIn(): boolean {
        return this.oAuthService.hasValidAccessToken()
    }

    signOut() {
        this.oAuthService.logOut()
    }
}