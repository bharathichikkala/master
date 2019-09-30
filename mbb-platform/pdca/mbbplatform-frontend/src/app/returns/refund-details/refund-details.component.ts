import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import 'rxjs/add/operator/toPromise';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormsModule, FormGroup, Validators, FormControl, AbstractControl, FormArray } from '@angular/forms';
import { endponitConfig } from '../../../environments/endpoint';
declare var $;
import { ReturnService } from '../returns.service';
import { IMyOptions } from 'mydatepicker';

@Component({
    selector: 'refund-view',
    templateUrl: './refund-details.component.html',
    styles: [`.table-borderless > tbody > tr > td,
    .table-borderless > tbody > tr > th,
    .table-borderless > tbody > tr > td,
    .table-borderless > tfoot > tr > td,
    .table-borderless > tfoot > tr > th,
    .table-borderless > thead > tr > td,
    .table-borderless > thead > tr > th {
        border: none !important;
    }`]
})

export class RefundComponent implements OnInit {
    loading = false;
    refundObj: any = {}
    constructor(
        public returnService: ReturnService,
        private readonly http: HttpClient,
        private readonly route: ActivatedRoute,
        private readonly fb: FormBuilder,
        private readonly router: Router,
    ) {
        this.refundForm = new FormGroup({
            'status': new FormControl(null, Validators.compose([Validators.required])),
            'enteredDate': new FormControl(null, Validators.compose([Validators.required])),
            'amount': new FormControl(null, Validators.compose([Validators.required, Validators.pattern(this.pricePattern)])),
            'dedAmount1': new FormControl(null, Validators.compose([Validators.required, Validators.pattern(this.pricePattern)])),
            'dedAmount2': new FormControl(null, Validators.compose([Validators.required, Validators.pattern(this.pricePattern)])),
            'dedAmount3': new FormControl(null, Validators.compose([Validators.required, Validators.pattern(this.pricePattern)])),
            // 'deductionAmount': new FormControl(null, Validators.compose([Validators.required, Validators.pattern(this.pricePattern)])),
            'refundedAmount': new FormControl(null, Validators.compose([Validators.required, Validators.pattern(this.pricePattern)])),
            'bankName': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(60)])),
            'transactionNumber': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(50)])),
            'comments': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(1000)])),

        });
    }

    refundForm: FormGroup;
    itemDetails: any = [];
    dateFormat = 'dd-mm-yyyy'
    private datePickerOptions: IMyOptions = {
        dateFormat: this.dateFormat,
        showTodayBtn: false,
        showClearDateBtn: false,
        editableDateField: false,
        height: '30px',
        selectionTxtFontSize: '14px',
        indicateInvalidDate: true,
    };
    getRefundValue() {
        let sum = 0;
        if (this.refundObj.amount) {
            sum = this.refundObj.amount - this.totalDeduction;
            //this.refundForm.get('refundedAmount').setValue(sum.toFixed(2))
        }
    }
    checkInvoiceAmount: boolean;
    checkAmount() {
        if (this.refundObj.amount != null) {
            if (this.refundObj.amount < this.refundObj.refundedAmount) {
                this.checkInvoiceAmount = true
            } else {
                this.checkInvoiceAmount = false
            }
        }
    }
    totalDeduction: any;
    getTotalValue() {
        let sum = 0;
        if (this.refundForm.value.dedAmount1 == null) {
            this.refundForm.value.dedAmount1 = 0
        }
        if (this.refundForm.value.dedAmount2 == null) {
            this.refundForm.value.dedAmount2 = 0
        }
        if (this.refundForm.value.dedAmount3 == null) {
            this.refundForm.value.dedAmount3 = 0
        }
        sum = this.refundForm.value.dedAmount1 + this.refundForm.value.dedAmount2 + this.refundForm.value.dedAmount3;
        this.totalDeduction = sum.toFixed(2);
        //this.getRefundValue();
        return sum.toFixed(2);
    }

    dateData: any = '';
    dispatchId; orderId; dispatchDate;
    d = new Date(); details;
    ngOnInit() {
        this.refundObj.status = 'Refunded';
        this.route.params.forEach((params: Params) => {
            if (params['id'] !== undefined) {
                this.dispatchId = params['id'];
                this.orderId = params['orderId'];
                this.dispatchDate = params['date1'];
            }
        });
        this.returnService.refundView(this.dispatchId).subscribe((data: any) => {
            if (data.length !== 0 && data.data != null) {
                this.details = data.data;
                this.refundForm.get('refundedAmount').setValue(this.details.refundedAmount)
            }
        })
        const value = this.dispatchDate;
        const dispatchYear = value.substring(0, 4);
        const dispatchMonth = value.substring(5, 7);
        const dispatchDay = value.substring(8, 10);
        this.datePickerOptions = {
            showClearDateBtn: false,
            showTodayBtn: false,
            openSelectorOnInputClick: true,
            inline: false,
            editableDateField: false,
            dateFormat: this.dateFormat,
            selectionTxtFontSize: '14px',
            indicateInvalidDate: true,
            disableUntil: { year: dispatchYear, month: dispatchMonth, day: dispatchDay - 1 },
            disableSince: { year: this.d.getFullYear(), month: this.d.getMonth() + 1, day: this.d.getDate() + 1 },
        }
    }
    pricePattern = '^[0-9]+([.][0-9]+)?$';
    twoDigit(number) {
        return (number < 10 ? '0' : '') + number
    }
    submitted = false;
    refundSuccess = 'Refund details added successfully';
    refundFail: any;
    success; fail; status;
    addRefundDetails() {
        this.submitted = false;
        if (this.refundForm.valid) {
            this.loading = true;
            const refYear = this.refundObj.enteredDate.date.year;
            const refMonth = this.twoDigit(this.refundObj.enteredDate.date.month);
            const refDay = this.twoDigit(this.refundObj.enteredDate.date.day);
            const date = `${refYear}-${refMonth}-${refDay}`
            const postObj = {
                "refundedDate": date,
                "amount": '' + this.refundObj.amount,
                "transactionNumber": this.refundObj.transactionNumber,
                "refundStatus": true,
                "bankName": this.refundObj.bankName,
                "refundedAmount": this.refundObj.refundedAmount,
                "comments": this.refundObj.comments,
                "dispatchId": {
                    "id": parseInt(this.dispatchId)
                },
                'paymentGatewayCharges': this.refundObj.dedAmount1,
                'courierCharges': this.refundObj.dedAmount2,
                'others': this.refundObj.dedAmount3,
            };
            this.returnService.addUpdateDetails(this.dispatchId, postObj).subscribe(data => {
                if (data.error == null) {
                    this.success = true;
                    setTimeout(() => {
                        this.loading = false;
                        this.success = false;
                        this.router.navigate(['./returns'])
                    }, 3000)
                } else {
                    this.refundFail = data.error.message;
                    this.fail = true;
                    setTimeout(() => {
                        this.fail = false;
                    }, 3000)
                }
            })
        }
        else {
            this.submitted = true;
        }

    }

    gotoHomePage() {
        this.router.navigate(['./returns']);
    }
}



