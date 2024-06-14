import {map, Observable} from "rxjs";
import {Car} from "../../../utils/types";
import {Injectable, NgZone} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {OAuthService} from "angular-oauth2-oidc";
import {environment} from "../../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class CarService {
    private apiUrl = environment.apiUrl;

    constructor(private http: HttpClient,private oAuthService: OAuthService, private ngZone: NgZone) {}

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

    getCarLocation(id: string): Observable<{ latitude: number, longitude: number }> {
        return new Observable(observer => {
            const url = `${this.apiUrl}/cars/${id}/locations`;
            const eventSource = new EventSource(url);

            eventSource.onmessage = (event) => {
                this.ngZone.run(() => {
                    const data = JSON.parse(event.data);
                    const location = data[data.length- 1];
                    observer.next({ latitude: location.latitude, longitude: location.longitude });
                });
            };

            eventSource.onerror = (error) => {
                this.ngZone.run(() => {
                    observer.error(error);
                });
            };

            return () => {
                eventSource.close();
            };
        });
    }
}