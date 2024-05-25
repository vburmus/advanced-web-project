import {Component, Input, OnInit} from '@angular/core';
import {GoogleMap, MapMarker} from '@angular/google-maps';
import {Car, Location} from "../../../utils/types";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'vt-map-all-cars',
  standalone: true,
  imports: [
    GoogleMap,
    MapMarker,
    NgForOf,
    NgIf
  ],
  templateUrl: './vt-map-all-cars.component.html',
  styleUrl: './vt-map-all-cars.component.scss'
})
export class VtMapAllCarsComponent implements OnInit {
  @Input() cars!: Car[];

  clickedMarkerTitle: string | null = null;
  center: google.maps.LatLngLiteral = { lat: 51.110754, lng: 17.058006 };
  zoom = 15;

  mapOptions: google.maps.MapOptions = {
    zoomControl: false,
    scrollwheel: true,
    disableDoubleClickZoom: true,
    maxZoom: 18,
    minZoom: 13,
    streetViewControl: false,
    mapTypeControl: false
  }

  carIcon = {
    url: 'assets/car-icon.png'
  };

  ngOnInit(): void {
  }

  getMarkers(): { position: google.maps.LatLngLiteral, title: string }[] {
    return this.cars.map(car => {
      const lastLocation: Location = car.locations[car.locations.length - 1];
      return {
        position: { lat: lastLocation.latitude, lng: lastLocation.longitude },
        title: `Car ${car.id}`
      };
    });
  }

  markerClicked(title: string) {
    this.clickedMarkerTitle = title;
  }

}
