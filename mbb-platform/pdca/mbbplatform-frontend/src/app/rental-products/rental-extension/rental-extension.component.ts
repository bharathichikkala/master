import { Component, EventEmitter, Input, OnInit, Output, ChangeDetectorRef } from '@angular/core';
import { FadeInTop } from '../../shared/animations/fade-in-top.decorator';

import { ActivatedRoute, Params, Router } from '@angular/router';
import 'rxjs/add/operator/toPromise';
import { Observable } from 'rxjs/Observable';
import { FormBuilder, FormGroup, Validators, FormControl, AbstractControl, FormArray, FormsModule } from '@angular/forms';
import { RentalService } from '../rental-products.service';
import { endponitConfig } from '../../../environments/endpoint';

import { IMyOptions, IMyDateModel } from 'mydatepicker';
import * as moment from 'moment';




@Component({
    selector: 'rental-extension',
    templateUrl: './rental-extension.component.html',
    styles: [`
    input[type=number]::-webkit-inner-spin-button, 
    input[type=number]::-webkit-outer-spin-button { 
    -webkit-appearance: none; 
    margin: 0; 
     }   
    `]
})

export class AddRentalExtensionComponent {

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
    };
    private expiryDatePickerOptions: IMyOptions = {
        dateFormat: this.dateFormat,
        showTodayBtn: false,
        showClearDateBtn: false,
        editableDateField: false,
        height: '30px',
        selectionTxtFontSize: '14px',
        indicateInvalidDate: true,
        openSelectorOnInputClick: true,
        componentDisabled: true,
        disableUntil: { year: (new Date()).getFullYear(), month: (new Date()).getMonth() + 1, day: (new Date()).getDate() },
    };
    private readonly model: Object = { date: { year: 2018, month: 10, day: 9 } };
    private readonly dateOfBirth: Date;

    constructor(
        private readonly route: ActivatedRoute,
        private readonly fb: FormBuilder,
        private readonly router: Router, private readonly rentalService: RentalService,
        private readonly cdr: ChangeDetectorRef) {
    }
    rentalExtensionForm: FormGroup; serviceTypes: any; allStatuses: any; rentalProductId: any;
    ngOnInit() {
        this.rentalExtensionForm = new FormGroup({
            'comments': new FormControl(null, Validators.compose([Validators.maxLength(1000)])),
            'extendedDate': new FormControl(null, Validators.compose([Validators.required])),
            'orderDate': new FormControl(null, Validators.compose([Validators.required])),
            'invoiceNumber': new FormControl(null, Validators.compose([Validators.required])),
            'rentalDays': new FormControl(null, Validators.compose([Validators.required,
                //Validators.pattern(/^[3-9][\d]$|^[1-2][\d][\d]$|^3[0-5][\d]^|^3[6][0-5]$/)
            ])),
            'rentalPrice': new FormControl(null, Validators.compose([Validators.required, Validators.pattern(this.pricePattern)])),
        });
        this.datePickerOptions = {
            openSelectorOnInputClick: true,
            inline: false,
            editableDateField: false,
            showClearDateBtn: false,
            dateFormat: this.dateFormat,
            selectionTxtFontSize: '14px',
            indicateInvalidDate: false,
            //  componentDisabled: true
            disableSince: { year: (new Date()).getFullYear() + 2, month: (new Date()).getMonth() + 1, day: (new Date()).getDate() + 1 },
            disableUntil: { year: (new Date()).getFullYear() - 3, month: (new Date()).getMonth() + 1, day: (new Date()).getDate() + 1 }
        }
        this.route.params.forEach((params: Params) => {
            if (params['id']) {
                this.rentalProductId = params['id'];
            }
        })

    }
    pricePattern = '^[0-9]+([.][0-9]+)?$';
    twoDigit(number) {
        return (number < 10 ? '0' : '') + number
    }
    disableUntilSelectStartDate(str: IMyDateModel) {
        if (str) {
            this.expiryDatePickerOptions = {
                componentDisabled: false,
            };
        }
        if (this.rentalExtensionForm.get('extendedDate').value && this.rentalExtensionForm.get('orderDate').value) {
            const start = moment(`${str.date.year}-${this.twoDigit(str.date.month)}-${this.twoDigit(str.date.day)}`);
            const end = moment(`${this.rentalExtensionForm.get('extendedDate').value.date.year}-
                ${this.twoDigit(this.rentalExtensionForm.get('extendedDate').value.date.month)}-${this.twoDigit(this.rentalExtensionForm.get('extendedDate').value.date.day)}`);
            this.rentalExtensionForm.get('rentalDays').setValue(end.diff(start, "days"))
        }
    }
    getDays(expireDate: IMyDateModel) {
        if (this.rentalExtensionForm.get('orderDate').value) {
            const start = moment(`${this.rentalExtensionForm.get('orderDate').value.date.year}-
                 ${this.twoDigit(this.rentalExtensionForm.get('orderDate').value.date.month)}-${this.twoDigit(this.rentalExtensionForm.get('orderDate').value.date.day)}`);
            const end = moment(`${expireDate.date.year}-${this.twoDigit(expireDate.date.month)}-${this.twoDigit(expireDate.date.day)}`);
            this.rentalExtensionForm.get('rentalDays').setValue(end.diff(start, "days"))
        }
    }
    // changeExtendDate() {
    //     const days = this.rentalExtensionForm.get('rentalDays').value;
    //     var extendDate = new Date(new Date().getTime() + days * 24 * 60 * 60 * 1000);
    //     const dd: any = extendDate.getDate();
    //     const mm: any = extendDate.getMonth() + 1;
    //     const yyyy = extendDate.getFullYear();
    //     const date = { date: { year: yyyy, month: mm, day: dd } };
    //     this.rentalExtensionForm.get('extendedDate').setValue(date)
    // }
    submitted: boolean; rentalSuccess; rentalFail;
    addRentalExtension() {
        this.submitted = false;
        if (this.rentalExtensionForm.valid) {
            this.loading = true;
            const refYear = this.rentalObj.extendedDate.date.year;
            const refMonth = this.twoDigit(this.rentalObj.extendedDate.date.month);
            const refDay = this.twoDigit(this.rentalObj.extendedDate.date.day);
            const date = `${refYear}-${refMonth}-${refDay}`;

            const fromYear = this.rentalObj.orderDate.date.year;
            const fromMonth = this.twoDigit(this.rentalObj.orderDate.date.month);
            const fromDay = this.twoDigit(this.rentalObj.orderDate.date.day);
            const fromDate = `${fromYear}-${fromMonth}-${fromDay}`;

            const postObj = {
                "extendedDate": fromDate,
                "extensionEndDate": date,
                "invoiceNumber": this.rentalObj.invoiceNumber,
                "days": parseInt(this.rentalObj.days),
                "price": parseFloat(this.rentalObj.price),
                "rentalProductId": {
                    "id": this.rentalProductId
                },
                "comments": this.rentalObj.comments
            };
            this.rentalService.addRentalExtension(postObj, this.rentalProductId).subscribe(data => {
                if (data.error == null) {
                    this.rentalSuccess = 'Rental extension details Added Successfully';
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
