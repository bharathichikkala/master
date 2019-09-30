import { Component, EventEmitter, Input, OnInit, Output, ChangeDetectorRef } from '@angular/core';
import { FadeInTop } from '../../shared/animations/fade-in-top.decorator';

import { ActivatedRoute, Params, Router } from '@angular/router';
import 'rxjs/add/operator/toPromise';
import { Observable } from 'rxjs/Observable';
import { FormBuilder, FormGroup, Validators, FormControl, AbstractControl, FormArray, FormsModule } from '@angular/forms';
import { RentalService } from '../rental-products.service';
import { endponitConfig } from '../../../environments/endpoint';

import { IMyOptions, IMyDateModel } from 'mydatepicker';




@Component({
    selector: 'add-rental',
    templateUrl: './add-rental.component.html',
    styles: [`
    input[type=number]::-webkit-inner-spin-button, 
    input[type=number]::-webkit-outer-spin-button { 
    -webkit-appearance: none; 
    margin: 0; 
     }
    .table-borderless > tbody > tr > td,
    .table-borderless > tbody > tr > th,
    .table-borderless > tbody > tr > td,
    .table-borderless > tfoot > tr > td,
    .table-borderless > tfoot > tr > th,
    .table-borderless > thead > tr > td,
    .table-borderless > thead > tr > th {
        border: none !important;
    }
    `]
})

export class AddRentalComponent {

    rentalObj: any = {}
    dateFormat = 'dd-mm-yyyy'
    loading = false;
    private datePickerOptions: IMyOptions = {
        dateFormat: this.dateFormat,
        showTodayBtn: false,
        showClearDateBtn: false,
        editableDateField: false,
        height: '30px',
        selectionTxtFontSize: '14px',
        indicateInvalidDate: true,
    };
    private readonly model: Object = { date: { year: 2018, month: 10, day: 9 } };
    private readonly dateOfBirth: Date;

    constructor(
        private readonly route: ActivatedRoute,
        private readonly fb: FormBuilder,
        private readonly router: Router, private readonly rentalService: RentalService,
        private readonly cdr: ChangeDetectorRef) {
    }
    rentalForm: FormGroup; serviceTypes: any; allStatuses: any;
    ngOnInit() {
        this.rentalObj.status = 5;
        this.rentalForm = new FormGroup({
            'comments': new FormControl(null, Validators.compose([Validators.maxLength(1000)])),
            'name': new FormControl(null, Validators.compose([Validators.required])),
            'email': new FormControl(null, Validators.compose([Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')])),
            'phoneNumber': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('^[0-9]{10,10}$')])),
            'alterPhoneNumber': new FormControl(null, Validators.compose([Validators.pattern('[0-9]{10,10}$')])),
            'requestDate': new FormControl(null, Validators.compose([Validators.required])),
            'city': new FormControl(null),
            'landMark': new FormControl(null, Validators.compose([Validators.required])),
            'status': new FormControl(null, Validators.compose([Validators.required])),
            'serviceType': new FormControl(null, Validators.compose([Validators.required])),
        }, AddRentalComponent.numbersCompare);
        this.rentalObj.serviceType = null;
        this.rentalObj.state = null;
        this.datePickerOptions = {
            openSelectorOnInputClick: true,
            inline: false,
            editableDateField: false,
            showClearDateBtn: false,
            dateFormat: this.dateFormat,
            selectionTxtFontSize: '14px',
            indicateInvalidDate: true,
            disableSince: { year: (new Date()).getFullYear(), month: (new Date()).getMonth() + 1, day: (new Date()).getDate() + 1 }
        }
        this.rentalService.getAllServiceTypes().subscribe(data => {
            if (data.error == null) {
                this.serviceTypes = data.data;
            }
        })
        this.rentalService.getAllStatus().subscribe(data => {
            if (data.error == null) {
                this.allStatuses = data.data;
            }
        })
        this.allStatuses = '';
        this.serviceTypes = '';
    }
    pricePattern = '^[0-9]+([.][0-9]+)?$';
    twoDigit(number) {
        return (number < 10 ? '0' : '') + number
    }
    static numbersCompare(formGroup):AddRentalComponent|undefined {
        const returnObj: any = {};
        if (formGroup.controls.phoneNumber.value === formGroup.controls.alterPhoneNumber.value) {
            returnObj.equal = true
            return returnObj
        }
    }

    submitted: boolean; rentalSuccess; rentalFail;comment;
    addRentalProduct() {
        this.submitted = false;
        if (this.rentalForm.valid) {
            this.loading = true;
            if (this.rentalObj.comments) {
                this.comment = this.rentalObj.comments
            } else {
                this.comment = null;
            }
            const refYear = this.rentalObj.requestDate.date.year;
            const refMonth = this.twoDigit(this.rentalObj.requestDate.date.month);
            const refDay = this.twoDigit(this.rentalObj.requestDate.date.day);
            const date = `${refYear}-${refMonth}-${refDay}`;
            const postObj = {
                "comments": this.comment,
                "customerDetailsId": {
                    "name": this.rentalObj.name,
                    "emailId": this.rentalObj.email,
                    "phone": this.rentalObj.phoneNumber,
                    "alternatePhoneNo": this.rentalObj.alterPhoneNumber,
                    "city": this.rentalObj.city,
                    "landmark": this.rentalObj.landMark,
                },
                "requestedDate": date,
                "rentalServiceType": {
                    "id": this.rentalObj.serviceType
                },
                "dispatchStatusId": {
                    "id": this.rentalObj.status
                }
            };
            this.rentalService.addRentalDetails(postObj).subscribe(data => {
                if (data.error == null) {
                    this.rentalSuccess = 'Customer Details Added Successfully';
                    setTimeout(() => {
                        this.rentalSuccess = '';
                        this.loading = false;
                        this.router.navigate(['./rental-products/rentals'])
                    }, 3000)
                } else {
                    this.rentalFail = data.error.message;
                    this.loading = false;
                    setTimeout(() => {
                        this.rentalFail = '';
                    }, 3000)
                }
            })
        } else {
            this.submitted = true;
        }
    }   
    // HomePage
    gotoHomePage() {
        this.router.navigate(['/rental-products/rentals'])
    }


}
