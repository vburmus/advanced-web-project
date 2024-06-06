import { Routes } from '@angular/router';
import {AllCarsComponent} from "./all-cars/all-cars.component";
import {CarDetailsComponent} from "./car-details/car-details.component";

export const routes: Routes = [
    { path: '', component: AllCarsComponent },
    { path: 'car-details/:id', component: CarDetailsComponent }
];
