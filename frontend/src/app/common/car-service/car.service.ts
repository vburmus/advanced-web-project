import {map, Observable} from "rxjs";
import {Car} from "../../../utils/types";
import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {OAuthService} from "angular-oauth2-oidc";
import {environment} from "../../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class CarService {
    private apiUrl = environment.apiUrl;

    constructor(private http: HttpClient,private oAuthService: OAuthService) {}

    getNearbyCars(): Observable<Car[]> {
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${this.oAuthService.getIdToken()}`
        });

        return this.http.get<{ content: Car[] }>(this.apiUrl + '/cars', { headers }).pipe(
            map(response => response.content)
        );
    }

    getCarById(id: string): Observable<Car> {
        const headers = new HttpHeaders({
            'Authorization': `Bearer ${this.oAuthService.getIdToken()}`
        });

        return this.http.get<Car>(this.apiUrl + '/cars/' + id, { headers });
    }
}