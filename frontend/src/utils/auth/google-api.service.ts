import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {AuthConfig, OAuthService, UserInfo} from 'angular-oauth2-oidc';
import {Observable, Subject} from 'rxjs';

const authCodeFlowConfig: AuthConfig = {
    issuer: 'https://accounts.google.com',
    strictDiscoveryDocumentValidation: false,
    redirectUri: window.location.origin,
    clientId: 'clientId',
    scope: 'openid profile email https://www.googleapis.com/auth/gmail.readonly',
    showDebugInformation: true,
};


@Injectable({
    providedIn: 'root'
})
export class GoogleApiService {

    gmail = 'https://gmail.googleapis.com'

    userProfileSubject = new Subject<UserInfo>()

    constructor(private readonly oAuthService: OAuthService, private readonly httpClient: HttpClient) {
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

    emails(userId: string): Observable<any> {
        return this.httpClient.get(`${this.gmail}/gmail/v1/users/${userId}/messages`, {headers: this.authHeader()})
    }

    getMail(userId: string, mailId: string): Observable<any> {
        return this.httpClient.get(`${this.gmail}/gmail/v1/users/${userId}/messages/${mailId}`, {headers: this.authHeader()})
    }

    isLoggedIn(): boolean {
        return this.oAuthService.hasValidAccessToken()
    }

    signOut() {
        this.oAuthService.logOut()
    }

    private authHeader(): HttpHeaders {
        return new HttpHeaders({
            'Authorization': `Bearer ${this.oAuthService.getAccessToken()}`
        })
    }
}