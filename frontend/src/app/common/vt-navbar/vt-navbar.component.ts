import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgForOf, NgIf, NgStyle} from "@angular/common";
import {Router} from "@angular/router";

@Component({
    selector: 'vt-navbar',
    standalone: true,
    imports: [
        NgForOf,
        NgIf,
        NgStyle
    ],
    templateUrl: './vt-navbar.component.html',
    styleUrl: './vt-navbar.component.scss'
})
export class VtNavbarComponent {
    @Input() user!: string;
    @Output() logOutEmitter = new EventEmitter<void>();

    constructor(private router: Router) {
    }

    logOut() {
        this.logOutEmitter.emit(undefined);
    }

    logoClicked() {
        this.router.navigate(['/all-cars']);
    }
}