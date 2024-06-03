import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {Car} from "../../utils/types";
import {CarCardComponent} from "./car-card/car-card.component";
import {VtMapAllCarsComponent} from "./vt-map-all-cars/vt-map-all-cars.component";
import {Router} from "@angular/router";
import {CarService} from "../common/car-service/CarService";

@Component({
    selector: 'vt-all-cars',
    standalone: true,
    imports: [
        NgForOf,
        CarCardComponent,
        CarCardComponent,
        VtMapAllCarsComponent
    ],
    template: `
        <vt-map-all-cars [cars]="cars"/>
        <div class="cards-container">
            <vt-car-card *ngFor="let car of cars" [car]="car" (click)="onCarClick(car.id)"/>
        </div>
    `,
    styleUrl: './all-cars.component.scss'
})
export class AllCarsComponent implements OnInit {
    cars!: Car[];

    constructor(private carService: CarService, private router: Router) {
    }

    ngOnInit(): void {
        this.carService.getNearbyCars().subscribe((cars) => {
            this.cars = cars;
        });
    }

    onCarClick(carId: number): void {
        this.router.navigate(['/car-details', carId]);
    }
}
