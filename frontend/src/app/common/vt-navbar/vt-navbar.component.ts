import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgForOf, NgIf, NgStyle} from "@angular/common";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

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
export class VtNavbarComponent implements OnInit {
    @Input() userObservable!: Observable<string | undefined>;
    @Output() logOutEmitter = new EventEmitter<void>();

    username: string | undefined;

    constructor(private router: Router) {
    }

    ngOnInit(): void {
        if (this.userObservable) {
            this.userObservable.subscribe(username => {
                this.username = username;
            });
        }
    }

    logOut() {
        this.logOutEmitter.emit();
    }

    logoClicked() {
        this.router.navigate(['']);
    }
}