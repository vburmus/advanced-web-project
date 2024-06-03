import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {NgStyle} from "@angular/common";

@Component({
    selector: 'vt-navbar',
    standalone: true,
    imports: [
        NgStyle
    ],
    template: `
        <header class="vt-header">
            <img src="favicon.ico" [ngStyle]="{'cursor': 'pointer'}" width="80" alt="logo" (click)="logoClicked()">
            <h1>Fleet Management</h1>
        </header>
    `,
    styleUrl: './vt-navbar.component.scss'
})
export class VtNavbarComponent {
    constructor(private router: Router) {
    }

    logoClicked() {
        this.router.navigate(['/all-cars']);
    }
}
