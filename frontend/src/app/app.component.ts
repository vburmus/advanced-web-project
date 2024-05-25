import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {AllCarsComponent} from "./all-cars/all-cars.component";
import {VtNavbarComponent} from "./common/vt-navbar/vt-navbar.component";

@Component({
  selector: 'vt-root',
  standalone: true,
  imports: [RouterOutlet, AllCarsComponent, VtNavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
}
