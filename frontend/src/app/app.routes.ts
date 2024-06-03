import { Routes } from '@angular/router';
import {CarDetailsComponent} from "../car-details/car-details.component";
import {AllCarsComponent} from "./all-cars/all-cars.component";

export const routes: Routes = [
    { path: '', redirectTo: '/all-cars', pathMatch: 'full' },
    { path: 'all-cars', component: AllCarsComponent },
    { path: 'car-details/:id', component: CarDetailsComponent }
];
