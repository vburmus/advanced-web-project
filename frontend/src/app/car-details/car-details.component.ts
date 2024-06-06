import {Component, OnInit} from '@angular/core';
import {GoogleMap, MapMarker} from "@angular/google-maps";
import {NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router} from "@angular/router";
import {Car} from "../../utils/types";
import {CarService} from "../common/car-service/car.service";

@Component({
    selector: 'vt-car-details',
    standalone: true,
    imports: [
        GoogleMap,
        MapMarker,
        NgForOf,
        NgIf
    ],
    templateUrl: './car-details.component.html',
    styleUrl: './car-details.component.scss'
})
export class CarDetailsComponent implements OnInit {
    car!: Car;
    center!: google.maps.LatLngLiteral;

    mapOptions: google.maps.MapOptions = {
        zoomControl: true,
        scrollwheel: false,
        disableDoubleClickZoom: true,
        maxZoom: 18,
        minZoom: 13,
        streetViewControl: false,
        mapTypeControl: false
    };
    zoom = 15;
    carIcon = {
        url: 'assets/car-icon.png'
    };

    constructor(private route: ActivatedRoute, private router: Router, private carService: CarService) {
    }

    ngOnInit() {
        this.route.paramMap.subscribe(params => {
            const carId = params.get('id');
            this.carService.getCarById(carId!).subscribe(car => {
                this.car = car;
                this.center = {lat: car.locations[this.car.locations.length - 1].latitude, lng: car.locations[this.car.locations.length - 1].longitude};
            });
        });
    }

    getCarLastLocation(): google.maps.LatLngLiteral {
        const lastLocation = this.car.locations[this.car.locations.length - 1];
        return {lat: lastLocation.latitude, lng: lastLocation.longitude};
    }
}
