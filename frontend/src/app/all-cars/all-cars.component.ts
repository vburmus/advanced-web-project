import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {Car} from "../../utils/types";
import {CarService} from "./car-service/CarService";
import {CarCardComponent} from "./car-card/car-card.component";
import {VtMapAllCarsComponent} from "./vt-map-all-cars/vt-map-all-cars.component";

@Component({
  selector: 'vt-all-cars',
  standalone: true,
  imports: [
    NgForOf,
    CarCardComponent,
    CarCardComponent,
    VtMapAllCarsComponent
  ],
  templateUrl: './all-cars.component.html',
  styleUrl: './all-cars.component.scss'
})
export class AllCarsComponent implements OnInit {
  cars!: Car[];

  constructor(private carService: CarService) {
  }

  ngOnInit(): void {
    this.carService.getNearbyCars().subscribe((cars) => {
      this.cars = cars;
    });
  }

}
