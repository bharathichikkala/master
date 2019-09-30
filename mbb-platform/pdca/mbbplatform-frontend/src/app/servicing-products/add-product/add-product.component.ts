import { Component, ViewChild } from '@angular/core';
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
export class AddServiceProductComponent {
    loading = false;
    adminType: any;
    spForm: FormGroup;
    hideQuotationForm = false;
    sparePartsForm: FormGroup;
    qutationDetailsForm: FormGroup;
    paymentDetailsForm: FormGroup;
    invoiceForm: FormGroup;
    submitted = false;
    dateFormat = 'dd-mm-yyyy'
    warranty = true;
    inWarranty: any = "No";
    checklist = [];
    paymentMode: any = null;
    paymentDetailsStatus: any = null;
    total: any = 0;
    serviceEntryDate: any
    successAlertMessages: any;
    serialNumberStatus = true;
    paymentModes = [];
    returnPolicy = [{ "name": "Yes", "value": true }, { "name": "No", "value": false }]
    warrantySelect = [{ "name": "Yes", "value": true }, { "name": "No", "value": false }]
    paymentStatuses = [{ "name": "Completed", "value": true }, { "name": "Pending", "value": false }]
    quotationId: any;
    selectedItems = [];
    checkedData = [];
    detailsErrorMessage: any
    skuDetails = [];
    showCustomerDetailsForm = true;
    sparePartsTotal: any = 0
    dropdownSettings = {}
    d = new Date();
    constructor(
        public productService: ProductsService,
        private readonly http: HttpClient,
        public router: Router) {
        this.adminType = sessionStorage.getItem('userRole') === 'SUPERADMIN' ? true : false;
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
    public productDetails: any = {
        'orderId': '',
        'orderDate': '',
        'skuCode': '',
        'productName': '',
        'productSerialNumber': '',
        'qrCode': '',
        'warranty': '',
        'warrantyInYears': '',
        'tenDaysReturnPolicy': '',
        'runTime': '',
        'customerRemarks': '',
        'receivedBy': '',
        'checklistItems': '',
        'servicingStatusesId': {
            "id": null
        },
        'customerDetailsId': {
            'name': '',
            'phone': '',
            'emailId': '',
            'city': '',
            'state': '',
            'pincode': '',
            'address': ''
        }
    }
    public quatationDetails = {
        'serviceCharges': null,
        'courierCharges': null,
        'otherCharges': null,
        'igst': null,
        'cgst': null,
        'sgst': null,
        'utgst': null,
        'unicommerceReferenceNumber': null,
        'transactionReferenceNumber': null,
        'paymentDate': null,
        'comments': '',
        'paymentStatus': null,
        'paymentModeId': null,
        'servicingProduct': null,
        'servicingStatusId': null
    }
    ngOnInit() {
        this.spForm = new FormGroup({
            'orderId': new FormControl(null, Validators.required),
            'orderDate': new FormControl(null, Validators.required),
            'skuCode': new FormControl(null, Validators.required),
            'productName': new FormControl(null, Validators.required),
            'productSerialNumber': new FormControl(null),
            'qrCode': new FormControl(null),
            'warranty': new FormControl(null, Validators.required),
            'warrantyInDays': new FormControl(),
            'tenDaysReturnPolicy': new FormControl(null),
            'runTime': new FormControl(null),
            'customerRemarks': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(1000)])),
            'receivedBy': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(64)])),
            'customerDetails': new FormGroup({
                'name': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(64)])),
                'email': new FormControl(null, Validators.compose([
                    Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')])),
                'phone': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('[0-9]{10,10}')])),
                'city': new FormControl(null, Validators.required),
                'state': new FormControl(null, Validators.required),
                'pincode': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('[0-9]{6,6}')])),
                'address': new FormControl(null, Validators.required)
            })
        });
        this.qutationDetailsForm = new FormGroup({
            'serviceCharges': new FormControl(null),
            'courierCharges': new FormControl(null),
            'otherCharges': new FormControl(null),
            'igst': new FormControl(null),
            'cgst': new FormControl(null),
            'sgst': new FormControl(null),
            'utgst': new FormControl(null),
            'comments': new FormControl(null),
            'paymentStatus': new FormControl(null),
            'paymentModeId': new FormGroup({
                'id': new FormControl(null)
            }),
            'items': new FormArray([])
        });

        this.paymentDetailsForm = new FormGroup({
            'paymentModeId': new FormGroup({
                'id': new FormControl(null, Validators.required)
            }),
            'paymentStatus': new FormControl(null, Validators.compose([Validators.required])),
            'bankReferenceNumber': new FormControl(null),
            'comments': new FormControl(null),
            'paymentDate': new FormControl(null, Validators.required)
        });

        this.invoiceForm = new FormGroup({
            'unicommerceReferenceNumber': new FormControl(null)
        });


        (this.qutationDetailsForm.get('items') as FormArray).push(this.createItem());
        this.skuDetails.push({
            "sku": '',
            "productName": '',
            "price": null,
        })

        this.dropdownSettings = {
            singleSelection: false,
            idField: 'id',
            textField: 'accessory',
            unSelectAllText: 'UnSelect All',
            itemsShowLimit: 0,
        };
        this.productService.getAllPaymentModes().subscribe(data => {
            this.paymentModes = data.data
        })

    }
    getTotal() {
        const formObj = this.qutationDetailsForm.get('items') as FormArray
        let sum = 0;
        for (let i = 0; i < formObj.value.length; i++) {
            sum = sum + formObj.value[i].price
        }
        this.sparePartsTotal = sum
    }
    sparePartsSubmitted = false;
    createItem() {
        this.skuDetails.push({
            "sku": '',
            "productName": '',
            "price": null,
        })
        return new FormGroup({
            'sku': new FormControl(null, Validators.required),
            'productName': new FormControl(null, Validators.required),
            'price': new FormControl(null, Validators.required),
        })


    }
    checkedIds = [];
    checkedValues(a) {
        if (!this.checkedIds.includes(a)) {
            this.checkedIds.push(a)
        }
        else {
            for (let i = 0; i < this.checkedIds.length; i++) {
                if (this.checkedIds[i] === a) {
                    this.checkedIds.splice(i, 1)
                }
            }
        }
    }
    onDeleteItem(index) {
        const v = this.skuDetails[index].price;
        this.sparePartsTotal -= v;
        (this.qutationDetailsForm.get('items') as FormArray).removeAt(index);
        this.skuDetails.splice(index, 1);
    }
    addProduct() {

    }

    getDetailsBySku(event) {
        const sku = event.target.value
        this.productService.getDetailsByskuCode(sku).subscribe(data => {
            if (data.error == null) {
                this.productDetails.productName = data.data.productName
                this.productDetails.qrCode = data.data.qrCode
                if (data.data.serialNumberStaus) {
                    this.serialNumberStatus = false;
                    this.productDetails.productSerialNumber = data.data.serialNumber
                    this.spForm.get('productSerialNumber').setValidators(Validators.required)
                }
                else {
                    this.serialNumberStatus = true;
                    this.productDetails.productSerialNumber = '';
                }
            }
            else {
                this.errorAlertMessages = "Sku code does not exists";
                setTimeout(() => {
                    this.errorAlertMessages = '';
                }, 3000);
            }
        })
        this.getChecklist(sku)
    }
    noChecklist = true;
    getChecklist(sku) {
        this.productService.getAccessoriesBySku(sku).subscribe(data => {
            this.checklist = data.data;
            if (this.checklist.length > 0) {
                this.noChecklist = false;
            }
            else {
                this.noChecklist = true
            }
        })
    }
    onAddItem() {
        (this.qutationDetailsForm.get('items') as FormArray).push(this.createItem());
    }
    onItemSelect(e) {
    }
    checkWarranty(e) {
        if (e.target.value == "true") {
            this.productDetails.warrantyInYears = null
            this.warranty = false;
            // this.spForm.controls['warrantyInDays'].setValidators(Validators.compose([Validators.required]))
            this.productDetails.warrantyInYears = null
            this.spForm.get('warrantyInDays').setValue(null)

        }
        else {
            this.warranty = true;
            this.spForm.controls["warrantyInDays"].clearValidators()//
            this.spForm.controls["warrantyInDays"].updateValueAndValidity()
            this.productDetails.warrantyInYears = null
            this.spForm.get('warrantyInDays').setValue(null)
        }
    }
    quotationTotal: any = 0;
    addQuotationDetails() {
        let s = 0;
        s = s + this.quatationDetails.serviceCharges;
        s = s + this.quatationDetails.courierCharges;
        s = s + this.quatationDetails.otherCharges;
        s += this.quatationDetails.cgst;
        s += this.quatationDetails.igst;
        s += this.quatationDetails.sgst;
        s += this.quatationDetails.utgst;
        this.quotationTotal = s;
        this.total += this.quotationTotal;
    }
    totalValue() {
        return this.quotationTotal + this.sparePartsTotal
    }
    getProductName(e) {
        this.productService.getProductDetails(e.target.value).subscribe(data => {
            if (data.error == null) {
                if (data.data.orderDate != null) {
                    const date = data.data.orderDate.split('-')
                    this.productDetails.orderDate = { 'date': { 'year': Number(date[0]), 'month': Number(date[1]), 'day': Number(date[2]) } }
                }
                this.productDetails.productName = data.data.productName;
                this.productDetails.skuCode = data.data.skucode
                if (data.data.customerDetails != null) {
                    this.productDetails.customerDetailsId.name = data.data.customerDetails.name;
                    this.productDetails.customerDetailsId.emailId = data.data.customerDetails.emailId;
                    this.productDetails.customerDetailsId.phone = data.data.customerDetails.phone;
                    this.productDetails.customerDetailsId.address = data.data.customerDetails.address;
                    this.productDetails.customerDetailsId.city = data.data.customerDetails.city;
                    this.productDetails.customerDetailsId.state = data.data.customerDetails.state;
                    this.productDetails.customerDetailsId.pincode = data.data.customerDetails.pincode;
                }
                if (data.data.serialNumberStaus) {
                    this.serialNumberStatus = false;
                    this.productDetails.productSerialNumber = data.data.serialNumber
                }
                else {
                    this.serialNumberStatus = true;
                    this.productDetails.productSerialNumber = '';
                }
                this.productDetails.qrCode = data.data.qrCode
                this.getChecklist(Number(this.productDetails.skuCode))
            }

        })

    }
    onDateChanged(e) {

        this.productDetails.orderDate = e.date

    }
    quotationform = false;
    submitForm() {
        const orderdate = this.productDetails.orderDate;
        if (this.spForm.valid) {
            this.productDetails.orderDate = orderdate
            let day = this.productDetails.orderDate.date.day
                , month = this.productDetails.orderDate.date.month;
            if (this.productDetails.orderDate.date.month < 10) {
                month = "0" + this.productDetails.orderDate.date.month;
            }
            if (this.productDetails.orderDate.date.day <= 10) {
                day = "0" + this.productDetails.orderDate.date.day;
            }
            const date = `${this.productDetails.orderDate.date.year}-${month}-${day}`;
            this.productDetails.orderDate = date;
            this.productService.addServicingProduct(this.productDetails).subscribe(data => {
                if (data.error == null) {
                    this.quotationform = true;
                    this.showCustomerDetailsForm = false;
                    this.quotationId = data.data.id
                    this.addChecklist(this.quotationId)
                    this.serviceEntryDate = data.data.createdTime.substring(0, 10).split("-")
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
                    }

                }
            })

        }
        else {
            this.submitted = true;
            this.productDetails.orderDate = orderdate
        }
    }
    public paymentDetailsSubmitted = false;
    public paymentDetailsErrorMessage: any;
    public paymentDetailsSuccessMessage: any;
    showInvoiceForm = false;
    hidePaymentDetailsForm = false;
    addPaymentDetails() {
        let obj;
        if (this.paymentDetailsForm.valid) {
            const arr = this.paymentDetailsForm.value.paymentDate.formatted.split("-");
            const date = `${arr[2] - arr[1] - arr[0]}`;
            obj = {
                "transactionReferenceNumber": this.paymentDetailsForm.value.bankReferenceNumber,
                "paymentModeId": {
                    'id': this.paymentDetailsForm.value.paymentModeId.id
                },
                "paymentStatus": this.paymentDetailsStatus,
                "paymentDate": date,
                "comments": this.paymentDetailsForm.value.comments
            }
            this.productService.addPaymentDetails(this.sparePartsId, obj).subscribe(data => {
                if (data.error == null) {

                    this.paymentDetailsSuccessMessage = ''
                    this.hidePaymentDetailsForm = true;
                    this.showInvoiceForm = true;
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
            this.paymentDetailsSubmitted = true;
        }
    }
    public invoiceDetailsMessage: any;
    public invoiceEroorMessage: any;
    addUnicommerceNumber() {
        if (this.invoiceForm.value.unicommerceReferenceNumber == null) {
            this.invoiceForm.value.unicommerceReferenceNumber = "";
        }
        this.productService.addInvoiceDetails(this.sparePartsId, this.invoiceForm.value).subscribe(data => {
            if (data.error == null) {
                this.invoiceDetailsMessage = "Details Added Successfully";
                setTimeout(() => {
                    this.invoiceDetailsMessage = '';
                    this.router.navigate(['/servicing-products'])
                }, 3000)
            }
            else {
                this.invoiceEroorMessage = data.error.message;
                setTimeout(() => {
                    this.invoiceEroorMessage = '';
                }, 3000)
            }
        })
    }
    addChecklist(id) {
        for (let i = 0; i < this.checklist.length; i++) {
            for (let j = 0; j < this.checkedIds.length; j++) {
                if (this.checklist[i].id === this.checkedIds[j]) {
                    this.checkedData.push({
                        "skuCode": this.checklist[i].skuCode,
                        "accessory": this.checklist[i].accessory,
                        "quantity": this.checklist[i].quantity,
                        "conditionCheck": this.checklist[i].conditionCheck,
                        "accessoriesId": {
                            "id": this.checklist[i].id
                        }
                    })
                }
            }
        }
        this.productService.addChecklistItems(id, this.checkedData).subscribe(data => {

        })
    }
    public errorAlertMessages: any;
    getSkuDetails(s, index) {
        const formObj: any = this.qutationDetailsForm.get('items') as FormArray;
        formObj.controls[index].get('productName').setValue('')
        formObj.controls[index].get('price').setValue('')
        let skuCodeExists = false;
        for (let i = 0; i < this.qutationDetailsForm.value.items.length; i++) {
            if (this.qutationDetailsForm.value.items[i].sku === s.target.value && i !== index) {
                this.errorAlertMessages = "Sku code already exists"
                setTimeout(() => {
                    this.errorAlertMessages = ''
                }, 3000)
                skuCodeExists = true;
                break;
            }
        }
        if (!skuCodeExists) {
            this.productService.getDetailsBySku(s.target.value).subscribe(data => {
                if (data.error == null) {
                    formObj.controls[index].get('productName').setValue(data.data.productName)
                    formObj.controls[index].get('price').setValue('')
                }
                else {
                    this.errorAlertMessages = "Sku code does not exist"
                    formObj.controls[index].get('price').setValue('')
                    setTimeout(() => {
                        this.errorAlertMessages = ''
                    }, 3000)
                }

            })
        }

    }
    gotoHomePage() {
        this.router.navigate(['/servicing-products'])
    }
    sparePartsId: any;
    quotationDetailsSubmitted = false;
    showPaymentDetailsForm = false;
    addDetails() {
        const status = this.quatationDetails.paymentStatus
        for (let i = 0; i < this.skuDetails.length; i++) {
            if (this.skuDetails[i].sku == '') {
                this.skuDetails.splice(i, 1)
            }
        }
        if (this.qutationDetailsForm.valid) {
            if (this.quatationDetails.serviceCharges == null) {
                this.quatationDetails.serviceCharges = 0;
            }
            if (this.quatationDetails.courierCharges == null) {
                this.quatationDetails.courierCharges = 0;
            }
            if (this.quatationDetails.otherCharges == null) {
                this.quatationDetails.otherCharges = 0
            }
            if (this.quatationDetails.igst == null) {
                this.quatationDetails.igst = 0
            }
            if (this.quatationDetails.cgst == null) {
                this.quatationDetails.cgst = 0
            }
            if (this.quatationDetails.sgst == null) {
                this.quatationDetails.sgst = 0
            }
            if (this.quatationDetails.utgst == null) {
                this.quatationDetails.utgst = 0
            }
            this.productService.addQutationDetails(this.quotationId, this.quatationDetails).subscribe(data => {
                if (data.error == null) {
                    this.quatationDetails.paymentStatus = status
                    this.sparePartsId = data.data.id;
                    localStorage.setItem("sparePartsId", this.sparePartsId);
                    this.productService.addSparePartsDetails(this.sparePartsId, this.skuDetails).subscribe(data1 => {
                        if (data1.error == null) {
                            this.hideQuotationForm = true
                            this.showPaymentDetailsForm = true;
                            this.showInvoiceForm = true;
                        }
                        else {
                            this.detailsErrorMessage = data1.error.message;
                            setTimeout(() => {
                                this.detailsErrorMessage = ''
                            }, 3000)
                        }
                    })
                }
                else {
                    this.detailsErrorMessage = data.error.message;
                    setTimeout(() => {
                        this.detailsErrorMessage = ''
                    }, 3000)
                }

            })
        }
        else {
            this.quotationDetailsSubmitted = true;
            this.sparePartsSubmitted = true
        }

    }

}
