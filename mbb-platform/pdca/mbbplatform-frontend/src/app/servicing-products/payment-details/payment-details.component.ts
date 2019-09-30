import { Component, OnInit, ViewChild } from '@angular/core';
import { Response, Headers } from '@angular/http'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { endponitConfig } from '../../../environments/endpoint';
import { ProductsService } from '../service-products.service';
import { ModalDirective } from 'ngx-bootstrap';
import { IMyOptions, IMyDateModel, IMyDate } from 'mydatepicker';
import * as moment from 'moment';
import { retry } from 'rxjs/operators';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';

@Component({
    selector: 'payment-details',
    templateUrl: './payment-details.component.html',
    styles: [`
    .borderless td, .borderless th {
        border: none;
    }
    input[type=number]::-webkit-inner-spin-button, 
    input[type=number]::-webkit-outer-spin-button { 
    -webkit-appearance: none; 
    margin: 0; 
}
        `]
})
export class PaymentDetailsComponent implements OnInit {
    adminType: any;
    paymentDetailsForm: FormGroup;
    quotationId: any;
    paymentStatusValue: any;
    paymentStatuses = [{ "name": "Completed", "value": true }, { "name": "Pending", "value": false }]
    paymentModes = [];
    dateFormat = 'dd-mm-yyyy';
    constructor(
        public productService: ProductsService,
        private readonly http: HttpClient,
        public router: Router) {
        this.adminType = sessionStorage.getItem('userRole') === 'SUPERADMIN' ? true : false;
    }
    productDetails: any;
    private paymentDatePickerOptions: IMyOptions = {
        showClearDateBtn: false,
        openSelectorOnInputClick: true,
        inline: false,
        editableDateField: false,
        dateFormat: this.dateFormat,
        selectionTxtFontSize: '14px',
        indicateInvalidDate: true,
        disableSince: { year: (new Date()).getFullYear(), month: (new Date()).getMonth() + 1, day: (new Date()).getDate() + 1 }
    }
    paymentId: any;
    paymentDetailsExists = false;
    serviceEntryDate: any;
    ngOnInit() {
        this.paymentStatusValue = false
        this.paymentDetailsForm = new FormGroup({
            'paymentModeId': new FormGroup({
                'id': new FormControl(null, Validators.required)
            }),
            'paymentStatus': new FormControl(false, Validators.compose([Validators.required])),
            'bankReferenceNumber': new FormControl(null),
            'comments': new FormControl(null),
            'paymentDate': new FormControl(null, Validators.required)
        });
        // this.paymentDetailsForm.get('paymentStatus').setVal
        this.productService.getAllPaymentModes().subscribe(data => {
            this.paymentModes = data.data
        })
        this.quotationId = localStorage.getItem("paymentId")
        this.paymentId = localStorage.getItem("paymentsId")
        this.productService.getDetailsById(this.quotationId).subscribe(data => {
            if (data.error == null) {
                this.serviceEntryDate = data.data.servicingproduct.createdTime.substring(0, 10).split("-");
                this.paymentDatePickerOptions = {
                    showClearDateBtn: false,
                    openSelectorOnInputClick: true,
                    inline: false,
                    editableDateField: false,
                    dateFormat: this.dateFormat,
                    selectionTxtFontSize: '14px',
                    indicateInvalidDate: true,
                    disableUntil: { year: Number(this.serviceEntryDate[0]), month: Number(this.serviceEntryDate[1]), day: Number(this.serviceEntryDate[2]) - 1 },
                    disableSince: { year: (new Date()).getFullYear(), month: (new Date()).getMonth() + 1, day: (new Date()).getDate() + 1 }
                };
                this.productDetails = data.data;
                if (data.data.paymentDetails !== null) {
                    this.paymentDetailsForm.get('paymentModeId').get('id').setValue(data.data.paymentDetails.paymentModeId.id);
                    this.paymentDetailsForm.get('bankReferenceNumber').setValue(data.data.paymentDetails.transactionReferenceNumber);
                    this.paymentDetailsForm.get('comments').setValue(data.data.paymentDetails.comments);
                    const d = data.data.paymentDetails.paymentDate.split("-");
                    const date = { date: { year: Number(d[0]), month: Number(d[1]), day: Number(d[2]) } }
                    this.paymentDetailsForm.get('paymentDate').setValue(date);
                    this.paymentDetailsExists = true;
                }
            }
        })



    }
    paymentDetailsStatus: any;
    paymentDetailsSuccessMessage: any;
    paymentDetailsErrorMessage: any;
    paymentDetailsSubmitted: any;
    homeScreen: any = '/servicing-products';
    addPaymentDetails() {
        let obj;
        if (this.paymentDetailsForm.valid) {
            let month, days, arr, date
            if (this.paymentDetailsForm.value.paymentDate.formatted) {
                arr = this.paymentDetailsForm.value.paymentDate.formatted.split("-");
                // date = arr[2] + '-' + arr[1] + '-' + arr[0]
                date = `${arr[2] - arr[1] - arr[0]}`;
            }
            else {
                if (this.paymentDetailsForm.value.paymentDate.date.month < 10) {
                    month = "0" + this.paymentDetailsForm.value.paymentDate.date.month
                }
                else {
                    month = this.paymentDetailsForm.value.paymentDate.date.month
                }
                if (this.paymentDetailsForm.value.paymentDate.date.day < 10) {
                    days = "0" + this.paymentDetailsForm.value.paymentDate.date.day
                }
                else {
                    days = this.paymentDetailsForm.value.paymentDate.date.day
                }
                date = `${this.paymentDetailsForm.value.paymentDate.date.year}-${month}-${days}`;
            }

            if (!this.paymentDetailsExists) {
                if (this.paymentDetailsForm.value.comments == null) {
                    this.paymentDetailsForm.value.comments = "";
                }
                if (this.paymentDetailsForm.value.bankReferenceNumber == null) {
                    this.paymentDetailsForm.value.bankReferenceNumber = "";
                }
                obj = {
                    "transactionReferenceNumber": this.paymentDetailsForm.value.bankReferenceNumber,
                    "paymentModeId": {
                        'id': this.paymentDetailsForm.value.paymentModeId.id
                    },
                    "paymentStatus": this.paymentDetailsForm.value.paymentStatus,
                    "paymentDate": date,
                    "comments": this.paymentDetailsForm.value.comments
                }
                this.productService.addPaymentDetails(this.paymentId, obj).subscribe(data => {
                    if (data.error == null) {
                        this.paymentDetailsSuccessMessage = 'Payment details added successfully';
                        setTimeout(() => {
                            this.paymentDetailsSuccessMessage = '';
                            this.router.navigate([this.homeScreen])
                        }, 3000)
                    }
                    else {
                        this.paymentDetailsErrorMessage = data.error.message;
                        setTimeout(() => {
                            this.paymentDetailsErrorMessage = ''
                        }, 3000)
                    }
                })
            }
            else {
                obj = {
                    "id": this.paymentId,
                    "transactionReferenceNumber": this.paymentDetailsForm.value.bankReferenceNumber,
                    "paymentModeId": {
                        'id': this.paymentDetailsForm.value.paymentModeId.id
                    },
                    "paymentStatus": this.paymentDetailsForm.value.paymentStatus,
                    "paymentDate": date,
                    "comments": this.paymentDetailsForm.value.comments
                }
                this.productService.updatePaymentDetails(this.paymentId, obj).subscribe(data => {
                    if (data.error == null) {
                        // this.paymentDetailsSuccessMessage = ''
                        this.paymentDetailsSuccessMessage = "Payment details updated successfully"
                        setTimeout(() => {
                            this.paymentDetailsSuccessMessage = ''
                            this.router.navigate([this.homeScreen])
                        }, 3000)
                    }
                    else {
                        this.paymentDetailsErrorMessage = data.error.message;
                        setTimeout(() => {
                            this.paymentDetailsErrorMessage = ''
                        }, 3000)
                    }
                })
            }
        }
        else {
            this.paymentDetailsSubmitted = true;
        }
    }
    gotoHomePage() {
        this.router.navigate([this.homeScreen])
    }
}
