import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {AllCarsComponent} from "./all-cars/all-cars.component";
import {VtNavbarComponent} from "./common/vt-navbar/vt-navbar.component";
import {CarDetailsComponent} from "../car-details/car-details.component";

@Component({
  selector: 'vt-root',
  standalone: true,
    imports: [RouterOutlet, AllCarsComponent, VtNavbarComponent, CarDetailsComponent],
  templateUrl: './app.component.html'
})
export class AppComponent {
}
