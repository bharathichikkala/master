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
    selector: 'update-rental',
    templateUrl: './update-rental.component.html',
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

export class UpdateRentalComponent {

    rentalObj: any = {
        "customerDetailsId": {
            "id": null,
            "name": null,
            "emailId": null,
            "phone": null,
            "alternatePhoneNo": null,
            "city": null,
            "state": null,
            "pincode": null,
            "address": null,
        },
        "dispatchStatusId": {
            id: null
        },
        "rentalServiceType": {
            id: null
        }
    }
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
    public orderDate: Object;
    constructor(
        private readonly route: ActivatedRoute,
        private readonly fb: FormBuilder,
        private readonly router: Router, private readonly rentalService: RentalService,
        private readonly cdr: ChangeDetectorRef) {
    }
    rentalUpdateForm: FormGroup; serviceTypes: any; allStatuses: any;
    ngOnInit() {
        this.rentalUpdateForm = new FormGroup({
            'comments': new FormControl(null, Validators.compose([Validators.maxLength(1000)])),
            'name': new FormControl(null, Validators.compose([Validators.required])),
            'email': new FormControl(null, Validators.compose([Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')])),
            'phoneNumber': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('^[0-9]{10,10}$')])),
            'alterPhoneNumber': new FormControl(null, Validators.compose([Validators.pattern('[0-9]{10,10}$')])),
            'requestedDate': new FormControl(null, Validators.compose([Validators.required])),
            'landMark': new FormControl(null, Validators.compose([Validators.required])),
            'city': new FormControl(null),
            'status': new FormControl(null, Validators.compose([Validators.required])),
            'serviceType': new FormControl(null, Validators.compose([Validators.required])),
        }, UpdateRentalComponent.numbersCompare);
        this.rentalObj.dispatchStatusId.id = null;
        this.rentalObj.rentalServiceType.id = null;
        this.rentalObj.customerDetailsId.state = null;
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
        this.route.params.forEach((params: Params) => {
            if (params['id']) {
                this.rentalService.getRentalDetails(params['id']).subscribe(data => {
                    if (data.error == null) {
                        this.rentalObj = data.data;
                        if (this.rentalObj.requestedDate) {
                            const dArr = this.rentalObj.requestedDate.split("-");
                            this.rentalObj.requestedDate = { date: { year: dArr[0] * 1, month: dArr[1] * 1, day: dArr[2] * 1 } };
                            this.rentalUpdateForm.get('requestedDate').setValue(this.rentalObj.requestedDate)
                        }
                    }
                })
            }
        })
        this.rentalService.getAllServiceTypes().subscribe(data => {
            if (data.error == null) {
                this.serviceTypes = data.data;
            }
        })
        this.rentalService.getAllStatus().subscribe(data => {
            if (data.error == null) {
                this.allStatuses = data.data;
                this.allStatuses = [this.allStatuses[1], this.allStatuses[0], this.allStatuses[2], this.allStatuses[3]]
            }
        })
    }
    pricePattern = '^[0-9]+([.][0-9]+)?$';
    twoDigit(number) {
        return (number < 10 ? '0' : '') + number
    }
    static numbersCompare(formGroup):UpdateRentalComponent|undefined {
        const returnObj: any = {};
        if (formGroup.controls.phoneNumber.value === formGroup.controls.alterPhoneNumber.value) {
            returnObj.equal = true
            return returnObj
        }
    }
    submitted: boolean; rentalSuccess; rentalFail;
    updateRentalProduct() {
        this.submitted = false;
        if (this.rentalUpdateForm.valid) {
            this.loading = true;
            const reqYear = this.rentalObj.requestedDate.date.year;
            const reqMonth = this.twoDigit(this.rentalObj.requestedDate.date.month);
            const reqDay = this.twoDigit(this.rentalObj.requestedDate.date.day);
            const requestedDate = `${reqYear}-${reqMonth}-${reqDay}`;
           
            const postObj = {
                "id": this.rentalObj.id,
                "comments": this.rentalObj.comments,
                "customerDetailsId": {
                    "id": this.rentalObj.customerDetailsId.id,
                    "name": this.rentalObj.customerDetailsId.name,
                    "emailId": this.rentalObj.customerDetailsId.emailId,
                    "phone": this.rentalObj.customerDetailsId.phone,
                    "alternatePhoneNo": this.rentalObj.customerDetailsId.alternatePhoneNo,
                    'city': this.rentalObj.customerDetailsId.city,
                    "landmark": this.rentalObj.customerDetailsId.landmark
                },
                "requestedDate": requestedDate,
                "rentalServiceType": {
                    "id": this.rentalObj.rentalServiceType.id
                },
                "dispatchStatusId": {
                    "id": this.rentalObj.dispatchStatusId.id
                }
            };
            this.rentalService.updateRentalDetails(postObj, this.rentalObj.id).subscribe(data => {
                if (data.error == null) {
                    this.rentalSuccess = 'Customer Details Updated Successfully';
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
    getProductName(event) {
        const sku = event.target.value
        this.rentalService.getDetailsByskuCode(sku).subscribe(data => {
            if (data.error == null) {
                this.rentalUpdateForm.get('productName').setValue(data.data.productName)
            }
            else {
                this.rentalFail = "Sku code does not exists";
                setTimeout(() => {
                    this.rentalFail = '';
                }, 2000);
            }
        })
    }
    // HomePage
    gotoHomePage() {
        this.router.navigate(['/rental-products/rentals'])
    }


}
