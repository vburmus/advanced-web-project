import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {Car} from "../../utils/types";
import {CarCardComponent} from "./car-card/car-card.component";
import {VtMapAllCarsComponent} from "./vt-map-all-cars/vt-map-all-cars.component";
import {Router} from "@angular/router";
import {CarService} from "../common/car-service/car.service";

@Component({
    selector: 'vt-all-cars',
    standalone: true,
    imports: [
        NgForOf,
        CarCardComponent,
        CarCardComponent,
        VtMapAllCarsComponent,
        NgIf
    ],
    template: `
        <ng-template ngIf="cars">
            <vt-map-all-cars *ngIf="cars" [cars]="cars"/>
            <div class="cards-container">
                <vt-car-card *ngFor="let car of cars" [car]="car" (click)="onCarClick(car.id)"/>
            </div>
        </ng-template>
    `,
    styleUrl: './all-cars.component.scss'
})
export class AllCarsComponent implements OnInit {
    cars!: Car[];

    constructor(private carService: CarService, private router: Router) {
    }

    ngOnInit(): void {
        setTimeout(() => {
            this.carService.getNearbyCars().subscribe((cars) => {
                this.cars = cars;
            });
        }, 1000);
    }

    onCarClick(carId: number): void {
        this.router.navigate(['/car-details', carId]);
    }
}
