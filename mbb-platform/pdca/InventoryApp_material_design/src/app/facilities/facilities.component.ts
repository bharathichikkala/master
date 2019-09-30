import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../auth.service';
import { MatDialog } from '@angular/material';
import { FormsModule, FormBuilder, FormGroup, Validators, FormControl, AbstractControl, FormArray } from '@angular/forms';
@Component({
    selector: 'dialog-content-example-dialog',
    templateUrl: './facilities.component.html'
})

export class FacilityComponent {
    msgText: any;
    value;
    facilities: any = [];
    form: FormGroup
    userType;
    constructor(private router: Router) { }
    ngOnInit() {
        this.value = JSON.parse(localStorage.getItem('facility')).id
        this.userType = localStorage.getItem('userRole')
        const a = JSON.parse(localStorage.getItem('facilities'));
        this.facilities = a
        this.msgText = localStorage.getItem('msg');
        this.form = new FormGroup({
            facility: new FormControl(null)
        })
    }
    submitted = false;facilitySuccess;
    home() {
        this.facilities = JSON.parse(localStorage.getItem('facilities'));
        let ob;
        for (let i = 0; i < this.facilities.length; i++) {
            if (this.facilities[i].id == this.value) {
                ob = this.facilities[i];
            }
        }
        localStorage.setItem('facility', JSON.stringify(ob));
        if (this.userType == 'DISPATCHER') {
            this.facilitySuccess = 'Facility Changed Successfully';
            setTimeout(() => {
                this.facilitySuccess = '';
                this.router.navigate(['/dispatch/home'])
            }, 2000);
        }
        else {
            this.facilitySuccess = 'Facility Changed Successfully';
            setTimeout(() => {
                this.facilitySuccess = '';
                this.router.navigate(['/inventory/view'])
            }, 2000);
        }
    }
}