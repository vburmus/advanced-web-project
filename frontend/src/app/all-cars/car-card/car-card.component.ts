import {Component, Input} from '@angular/core';
import {Car} from "../../../utils/types";

@Component({
  selector: 'vt-car-card',
  standalone: true,
  imports: [],
  templateUrl: './car-card.component.html',
  styleUrl: './car-card.component.scss'
})
export class CarCardComponent {
  @Input() car!: Car;

  constructor() {
  }

}
