import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Params,Router } from '@angular/router';
import 'rxjs/add/operator/toPromise';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators,FormsModule, FormControl, AbstractControl, FormArray } from '@angular/forms';
import { endponitConfig } from '../../../environments/endpoint';
declare var $;
import { ReturnService } from '../returns.service';
import { IMyOptions } from 'mydatepicker';

@Component({
    selector: 'return-view',
    templateUrl: './return-details.component.html',
})

export class ReturnDetailsComponent implements OnInit {
    constructor(
        public returnService: ReturnService,
        private readonly http: HttpClient,
        private readonly route: ActivatedRoute,
        private readonly fb: FormBuilder,
        private readonly router: Router,
    ) {
        this.returnForm = new FormGroup({
            'enteredDate': new FormControl(null, Validators.compose([Validators.required])),
            'type': new FormControl(null, Validators.compose([Validators.required])),
            'reason': new FormControl(null, Validators.compose([Validators.required])),
            'status': new FormControl(null, Validators.compose([Validators.required])),
            'prodStatus': new FormControl(null, Validators.compose([Validators.required])),
            'refNumber': new FormControl(null, Validators.compose([Validators.required,Validators.maxLength(50)])),
            'trackNumber': new FormControl(null, Validators.compose([Validators.maxLength(50)])),
            'others': new FormControl(null),
            'comments': new FormControl(null, Validators.compose([Validators.required,Validators.maxLength(1000)])),
        });
    }
  loading=false;
    returnForm: FormGroup;
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
    returnObj: any = { others: null };
    dateData: any = '';  dispatchId; orderId;
     returnTypes; allReasons; dispatchDate;
    d = new Date();
    ngOnInit() {
        this.returnObj.type = '';
        this.returnObj.reason = '';
        this.returnObj.status = "QC Done";
        this.returnObj.prodStatus = '';
        this.returnObj.pickInitiated = '';
        const others = this.returnForm.get('others');
        this.returnForm.get('reason').valueChanges.subscribe(
            (value) => {
                if (value === 13) {
                    others.setValidators(Validators.compose([Validators.required]));
                }
                else {
                    others.clearValidators()
                    others.updateValueAndValidity()
                }
            });       
        this.returnService.getReturns().subscribe((data: any) => {
            if (data.length !== 0 && data.data != null) {
                this.returnTypes = data.data;
            }
        })
        this.returnService.getReasons().subscribe((data: any) => {
            if (data.length !== 0 && data.data != null) {
                this.allReasons = data.data;
            }
        })
        this.route.params.forEach((params: Params) => {
            if (params['dispatchId'] !== undefined && params['orderId'] !== undefined) {
                this.dispatchId = params['dispatchId'];
                this.orderId = params['orderId'];
                this.dispatchDate = params['date'];
            }
            else {
                this.router.navigate(['./returns']);
            }
        });
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
    public productStatuses = {
        "data": [           
            {
                "id": 1,
                "name": "Good"
            },
            {
                "id": 2,
                "name": "Bad"
            }

        ],
        "error": null,
        "code": 0
    }
    twoDigit(number) {
        return (number < 10 ? '0' : '') + number
    }

    returnSuccess = 'Return details added successfully';
    returnFail: any;
    success; fail;
    submitted = false;
    status: boolean;
    pickInitiated:any;
    addReturnDetails() {
        this.submitted = false;       
        if (this.returnForm.valid) {
            this.loading=true;
            if (this.returnObj.status === 'QC Done') {
                this.status = true
            }
            if (this.returnObj.pickInitiated !== '') {
                const pickYear=this.returnObj.pickInitiated.date.year;
                const pickMonth=this.twoDigit(this.returnObj.pickInitiated.date.month);
                const pickDay=this.twoDigit(this.returnObj.pickInitiated.date.day);
                this.pickInitiated = `${pickYear}-${pickMonth}-${pickDay}`
            } else {
                this.pickInitiated = ''
            }
            const returnYear=this.returnObj.enteredDate.date.year;
            const returnMonth=this.twoDigit(this.returnObj.enteredDate.date.month);
            const returnDay=this.twoDigit(this.returnObj.enteredDate.date.day);
            const date = `${returnYear}-${returnMonth}-${returnDay}`
            const postObj: any = {
                "returnRequestOn": date,
                "typeOfReturn": {
                    "id": this.returnObj.type
                },
                "returnReasons": {
                    "id": this.returnObj.reason
                },
                "returnStatus": this.status,
                "comments": this.returnObj.comments,
                "dispatchId": {
                    "id": parseInt(this.dispatchId)
                },
                "productStatus": this.returnObj.prodStatus,
                "unicommerceRefferenceNo": this.returnObj.refNumber,
                "pickupInitiated": this.pickInitiated,
                "trackingNumber": this.returnObj.trackNumber
            };
            if (this.returnObj.others != null) {
                postObj.otherReasons = this.returnObj.others;
            }
            this.returnService.addReturnDetails(postObj).subscribe(data => {
                if (data.error == null) {
                    this.success = true;
                    setTimeout(() => {
                        this.loading=false;
                        this.success = false;
                        this.router.navigate(['./returns'])
                    }, 3000)
                } else {
                    this.returnFail = data.error.message;
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



