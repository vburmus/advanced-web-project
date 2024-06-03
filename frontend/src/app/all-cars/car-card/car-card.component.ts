import {Component, Input} from '@angular/core';
import {Car} from "../../../utils/types";

@Component({
    selector: 'vt-car-card',
    standalone: true,
    imports: [],
    template: `
        <div class="card">
            <h2>Car {{ car.id }}</h2>
            <p>VIN: {{ car.vin }}</p>
            <p>Plate Number: {{ car.plateNumber }}</p>
            <p>Country Code: {{ car.countryCode }}</p>
        </div>
    `,
    styleUrl: './car-card.component.scss'
})
export class CarCardComponent {
    @Input() car!: Car;

}
