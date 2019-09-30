import { Component, ViewChild } from '@angular/core';
import { Response, Headers } from '@angular/http'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { endponitConfig } from '../../../environments/endpoint';
import { DemoProductsService } from '../demo-products.service';
import { ModalDirective } from 'ngx-bootstrap';
import { IMyOptions, IMyDateModel, IMyDate } from 'mydatepicker';
import * as moment from 'moment';
import { retry } from 'rxjs/operators';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';

declare var $;
@Component({
    selector: 'add-product',
    templateUrl: './add-product.component.html',
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
export class AddDemoProductComponent {
    loading = false;
    adminType: any;
    productDetailsForm: FormGroup;
    submitted: boolean = false;
    dateFormat = 'dd-mm-yyyy'
    warranty: boolean = true;
    inWarranty: any = "No";
    checklist = [];
    paymentMode: any = null;
    paymentDetailsStatus: any = null;
    serviceEntryDate: any
    successAlertMessages: any;
    serialNumberStatus: boolean = true;
    paymentModes = [];
    productsList: any;
    returnPolicy = [{ "name": "Yes", "value": true }, { "name": "No", "value": false }]
    warrantySelect = [{ "name": "Yes", "value": true }, { "name": "No", "value": false }]
    paymentStatuses = [{ "name": "Completed", "value": true }, { "name": "Pending", "value": false }]
    quotationId: any;
    selectedItems = [];
    checkedData = [];
    detailsErrorMessage: any
    skuDetails = [];
    showCustomerDetailsForm: boolean = true;
    sparePartsTotal: any = 0
    productName: any;
    dropdownSettings = {}
    d = new Date();
    constructor(
        public demoProductsService: DemoProductsService,
        private readonly http: HttpClient,
        public router: Router) {
        this.adminType = sessionStorage.getItem('userRole') == 'SUPERADMIN' ? true : false;
    }
    private readonly datePickerOptions: IMyOptions = {
        showClearDateBtn: false,
        openSelectorOnInputClick: true,
        inline: false,
        editableDateField: false,
        dateFormat: this.dateFormat,
        selectionTxtFontSize: '14px',
        indicateInvalidDate: true,
        disableSince: { year: (new Date()).getFullYear(), month: (new Date()).getMonth() + 1, day: (new Date()).getDate() + 1 }
    };

    ngOnInit() {
        this.productDetailsForm = new FormGroup({
            'skuCode': new FormControl(null, Validators.required),
            'productName': new FormControl(null, Validators.required),
            'productPrice': new FormControl(null),
            'demoPrice': new FormControl(null),
            'comments': new FormControl(null),
            'customerDetailsId': new FormGroup({
                'name': new FormControl(null, Validators.required),
                'emailId': new FormControl(null, Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')),
                'phone': new FormControl(null, Validators.compose([Validators.required, Validators.pattern(/^[\d]{10,10}$/)])),
                'alternatePhone': new FormControl(null, Validators.compose([Validators.pattern('[0-9]{10,10}')])),
                'address': new FormControl(null, Validators.required),
                'city': new FormControl(null),
                'landmark': new FormControl(null),
                'state': new FormControl(null),
                'pincode': new FormControl(null)
            })
        }, AddDemoProductComponent.facilityCompare);

        this.demoProductsService.getAllProducts().subscribe(data => {
            if (data.error == null) {
                this.productsList = data.data;
            }
        })
    }
    static facilityCompare(formGroup) {
        let returnObj: any = {};
        if (formGroup.controls.customerDetailsId.controls.phone.value == formGroup.controls.customerDetailsId.controls.alternatePhone.value) {
            returnObj.equal = true
            return returnObj
        }
    }

    productSelection;
    filteredproductNames = [];
    demoInvoiceId;
    dispatchedDemoInvoices;
    getProductNames() {
        this.productDetailsForm.get('skuCode').setValue(null);
        this.productSelection = false;
        this.filteredproductNames = [];
        if (this.productName) {
            this.productsList.find((data) => {
                if ((data.toLowerCase()).includes(this.productName.toLowerCase())) {
                    this.filteredproductNames.push(data)
                }
            })
        }
    }
    changeSku() {
        this.productDetailsForm.get('productName').setValue(null);
    }
    selectProductName(obj) {
        this.productSelection = true;
        this.productName = obj;
        this.filteredproductNames = [];
        const ob = {
            'name': obj
        }
        this.demoProductsService.getSkuCode(ob).subscribe(data => {
            if (data.error == null) {
                this.productDetailsForm.get('skuCode').setValue(data.data.skuCode);
            }
        })
    }

    errorAlertMessages: any;
    getDetailsBySku(event) {
        const sku = event.target.value
        if (sku != '') {
            this.demoProductsService.getDetailsByskuCode(sku).subscribe(data => {
                if (data.error == null) {
                    this.productDetailsForm.get('productName').setValue(data.data.productName)
                    this.filteredproductNames = [];
                }
                else {
                    this.errorAlertMessages = data.error.message;
                    setTimeout(() => {
                        this.errorAlertMessages = '';
                        this.productDetailsForm.get('productName').setValue(null);
                    }, 2000);
                }
            })
        }
        else {
            this.productDetailsForm.get('productName').setValue(null);
        }
    }
    // static facilityCompare(formGroup) {
    //     let returnObj: any = {};
    //     alert(formGroup.controls.customerDetailsId.phone.value)
    //     if (formGroup.controls.customerDetailsId.phone.value == formGroup.controls.customerDetailsId.alternatePhone.value) {
    //         // alert("jhjhjh")
    //         returnObj.equal = true
    //         return returnObj
    //     }
    // }
    gotoHomePage() {
        this.router.navigate(['/demo-products'])
    }
    successMessage: any;
    errorMessage: any;
    addProduct() {
        if (this.productDetailsForm.valid) {
            const obj = {
                "skuCode": this.productDetailsForm.get('skuCode').value,
                "productName": this.productDetailsForm.get('productName').value,
                // "productPrice": this.productDetailsForm.get('productPrice').value,
                "demoPrice": this.productDetailsForm.get('demoPrice').value,
                "orderDate": "",
                "comments": this.productDetailsForm.get('comments').value,
                "customerDetailsId": {
                    "name": this.productDetailsForm.get('customerDetailsId').get('name').value,
                    "emailId": this.productDetailsForm.get('customerDetailsId').get('emailId').value,
                    "phone": this.productDetailsForm.get('customerDetailsId').get('phone').value,
                    // "city": this.productDetailsForm.get('customerDetailsId').get('city').value,
                    // "state": this.productDetailsForm.get('customerDetailsId').get('state').value,
                    // "pincode": this.productDetailsForm.get('customerDetailsId').get('pincode').value,
                    "address": this.productDetailsForm.get('customerDetailsId').get('address').value,
                    "landmark": this.productDetailsForm.get('customerDetailsId').get('landmark').value,
                    "alternatePhoneNo": this.productDetailsForm.get('customerDetailsId').get('alternatePhone').value
                }
            }
            this.demoProductsService.addDemoProduct(obj).subscribe(data => {
                if (data.error == null) {
                    this.successMessage = "Product added successsfully"
                    setTimeout(() => {
                        this.successMessage = "";
                        this.router.navigate(['/demo-products'])
                    }, 500)
                }
                else {
                    this.errorMessage = data.error.message;
                    setTimeout(() => {
                        this.errorMessage = "";
                        // this.router.navigate(['/demo-products'])
                    }, 3000)
                }
            })

        }
        else {
            this.submitted = true;
        }
    }
}
