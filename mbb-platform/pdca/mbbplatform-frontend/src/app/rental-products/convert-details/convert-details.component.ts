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
    selector: 'convert-rental',
    templateUrl: './convert-details.component.html',
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

export class ConvertedRentalComponent {
    rentalObj: any = {
        customerDetailsId: {
            address: null
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
        openSelectorOnInputClick: true,

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
    public orderDate: Object;
    constructor(
        private readonly route: ActivatedRoute,
        private readonly fb: FormBuilder,
        private readonly router: Router, private readonly rentalService: RentalService,
        private readonly cdr: ChangeDetectorRef) {
    }
    rentalConvertForm: FormGroup; rentalId: any; requestedDate: any;
    dArr: any = [];
    ngOnInit() {
        this.rentalConvertForm = new FormGroup({
            'orderId': new FormControl('', Validators.compose([Validators.required])),
            'orderDate': new FormControl('', Validators.compose([Validators.required])),
            'expireDate': new FormControl('', Validators.compose([Validators.required])),
            'skuCode': new FormControl('', Validators.compose([Validators.required])),
            'productName': new FormControl(null, Validators.compose([Validators.required])),
            'deliveredBy': new FormControl(null, Validators.compose([Validators.required])),
            // 'qrCode': new FormControl(null),
            'rentalAmount': new FormControl(null, Validators.compose([Validators.required, Validators.pattern(this.pricePattern)])),
            'depositAmount': new FormControl(null, Validators.compose([Validators.pattern(this.pricePattern)])),
            'rentalDays': new FormControl(null, Validators.compose([Validators.required])),
            'doctorDetails': new FormControl(null, Validators.compose([Validators.maxLength(1000)])),
            'address': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(1000)])),
            'comments': new FormControl(null, Validators.compose([Validators.maxLength(1000)])),
        });
        this.route.params.forEach((params: Params) => {
            if (params['id']) {
                this.rentalId = params['id'];
                this.rentalService.getRentalDetails(params['id']).subscribe(data => {
                    if (data.error == null) {
                        this.rentalObj = data.data;
                        this.requestedDate = this.rentalObj.requestedDate;
                        const value = this.requestedDate;
                        const dispatchYear = value.substring(0, 4);
                        const dispatchMonth = value.substring(5, 7);
                        const dispatchDay = value.substring(8, 10);
                        this.datePickerOptions = {
                            openSelectorOnInputClick: true,
                            inline: false,
                            showClearDateBtn: false,
                            editableDateField: false,
                            dateFormat: this.dateFormat,
                            height: '30px',
                            selectionTxtFontSize: '14px',
                            indicateInvalidDate: true,
                            disableSince: { year: (new Date()).getFullYear(), month: (new Date()).getMonth() + 1, day: (new Date()).getDate() + 1 },
                            disableUntil: { year: dispatchYear, month: dispatchMonth, day: dispatchDay - 1 }
                        }
                    }
                })
            }
        })
        this.getIdChange();

    }
    pricePattern = '^[0-9]+([.][0-9]+)?$';
    twoDigit(number) {
        return (number < 10 ? '0' : '') + number
    }
    check = false;
    readonly = true;
    disable() {
        if (this.rentalConvertForm.get('phoneNumber').valid) {
            this.readonly = false;
        } else {
            this.readonly = true;
        }
    }

    disableUntilSelectStartDate(str: IMyDateModel) {
        if (str) {
            this.expiryDatePickerOptions = {
                componentDisabled: false,
            };
        }
        if (this.rentalConvertForm.get('expireDate').value && this.rentalConvertForm.get('orderDate').value) {
            const start = moment(`${str.date.year}-${this.twoDigit(str.date.month)}-${this.twoDigit(str.date.day)}`);
            const end = moment(`${this.rentalConvertForm.get('expireDate').value.date.year}-
                ${this.twoDigit(this.rentalConvertForm.get('expireDate').value.date.month)}-
                ${this.twoDigit(this.rentalConvertForm.get('expireDate').value.date.day)}`);
            this.rentalConvertForm.get('rentalDays').setValue(end.diff(start, "days"))
        }
    }
    getDays(expireDate: IMyDateModel) {
        if (this.rentalConvertForm.get('orderDate').value) {
            const start = moment(`${this.rentalConvertForm.get('orderDate').value.date.year}-
            ${this.twoDigit(this.rentalConvertForm.get('orderDate').value.date.month)}-${this.twoDigit(this.rentalConvertForm.get('orderDate').value.date.day)}`);
            const end = moment(`${expireDate.date.year}-${this.twoDigit(expireDate.date.month)}-${this.twoDigit(expireDate.date.day)}`);
            this.rentalConvertForm.get('rentalDays').setValue(end.diff(start, "days"))
        }
    }
    submitted: boolean; rentalSuccess; rentalFail;
    convertProduct() {
        this.submitted = false;
        if (this.rentalConvertForm.valid) {
            this.loading = true;

            const refYear = this.rentalObj.orderDate.date.year;
            const refMonth = this.twoDigit(this.rentalObj.orderDate.date.month);
            const refDay = this.twoDigit(this.rentalObj.orderDate.date.day);
            const date = `${refYear}-${refMonth}-${refDay}`;

            const expYear = this.rentalObj.expireDate.date.year;
            const expMonth = this.twoDigit(this.rentalObj.expireDate.date.month);
            const expDay = this.twoDigit(this.rentalObj.expireDate.date.day);
            const expireDate = `${expYear}-${expMonth}-${expDay}`;
            this.rentalObj.id = this.rentalId;
            this.rentalObj.orderDate = date;
            this.rentalObj.rentalStartDate = date;
            this.rentalObj.rentalEndDate = expireDate;
            this.rentalObj.rentalDays = parseInt(this.rentalObj.rentalDays);
            this.rentalObj.productName = this.rentalConvertForm.get('productName').value;
            this.rentalObj.dispatchStatusId.id = 9;
            this.rentalService.checkInvoice(this.rentalObj.invoiceNumber).subscribe(data => {
                if (data.error == null) {
                    this.rentalService.updateRentalDetails(this.rentalObj, this.rentalObj.id).subscribe(data => {
                        if (data.error == null) {
                            this.rentalSuccess = 'Rental Product added Successfully';
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
    prodName;
    selectProductName(obj) {
        this.prodName = obj;
        this.filterProductsList = [];
        this.rentalService.getSkuCodeByProductName(this.prodName).subscribe(data => {
            if (data.error == null) {
                this.rentalConvertForm.get('skuCode').setValue(data.data.skuCode);
                this.rentalConvertForm.get('productName').setValue(this.prodName);
            }
            else {
                this.rentalFail = "Product Name does not exists";
                this.rentalConvertForm.get('skuCode').setValue('')
                setTimeout(() => {
                    this.rentalFail = '';
                }, 2000);
            }
        })
    }
    productNames;
    getIdChange() {
        this.rentalService.getAllProductsNames().subscribe((data: any) => {
            if (data.data != null) {
                this.productNames = data.data
            }
        })
    }
    filterProductsList: any = [];
    getProductNamesbasedonSearch(obj) {
        const name = obj.target.value;
        this.filterProductsList = [];
        if (name) {
            this.productNames.find((data) => {
                if ((data.toLowerCase()).includes(name.toLowerCase())) {
                    this.filterProductsList.push(data);
                } else {
                    this.rentalConvertForm.get('skuCode').setValue('')
                }
            })
        }

    }

    // getSKUCode(event) {
    //     const sku = event.target.value
    //     this.rentalService.getDetailsByskuCode(sku).subscribe(data => {
    //         if (data.error == null) {
    //             this.rentalConvertForm.get('productName').setValue(data.data.productName)
    //         }
    //         else {
    //             this.rentalFail = "Sku code does not exists";
    //             setTimeout(() => {
    //                 this.rentalFail = '';
    //             }, 2000);
    //         }
    //     })
    // }
    // HomePage
    gotoHomePage() {
        this.router.navigate(['/rental-products/rentals'])
    }


}
