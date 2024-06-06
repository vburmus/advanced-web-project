import {Component, Input, OnInit} from '@angular/core';
import {GoogleMap, MapMarker} from '@angular/google-maps';
import {Car, Location} from "../../../utils/types";
import {NgForOf, NgIf} from "@angular/common";

@Component({
    selector: 'vt-map-all-cars',
    standalone: true,
    imports: [
        GoogleMap,
        MapMarker,
        NgForOf,
        NgIf
    ],
    templateUrl: './vt-map-all-cars.component.html',
    styleUrl: './vt-map-all-cars.component.scss'
})
export class VtMapAllCarsComponent implements OnInit {
    @Input() cars!: Car[];

    clickedMarkerTitle: string | null = null;
    center!: google.maps.LatLngLiteral;
    zoom = 15;

    mapOptions: google.maps.MapOptions = {
        zoomControl: true,
        scrollwheel: false,
        disableDoubleClickZoom: true,
        maxZoom: 18,
        minZoom: 5,
        streetViewControl: false,
        mapTypeControl: false
    };

    carIcon = {
        url: 'assets/car-icon.png'
    };

    ngOnInit(): void {
        this.center = {lat: this.cars[0].locations[this.cars[0].locations.length - 1].latitude, lng: this.cars[0].locations[this.cars[0].locations.length - 1].longitude};
    }

    getMarkers(): { position: google.maps.LatLngLiteral, title: string }[] {
        return this.cars.map(car => {
            const lastLocation: Location = car.locations[car.locations.length - 1];
            return {
                position: {lat: lastLocation.latitude, lng: lastLocation.longitude},
                title: `Car ${car.id}`
            };
        });
    }

    markerClicked(title: string) {
        this.clickedMarkerTitle = title;
    }

}
