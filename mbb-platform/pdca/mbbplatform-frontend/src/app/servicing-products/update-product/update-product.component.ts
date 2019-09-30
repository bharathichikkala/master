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
    selector: 'update-product',
    templateUrl: './update-product.component.html',
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
export class UpdateServicingProductComponent {
    loading = false;
    adminType: any;
    spForm: FormGroup;
    sparePartsForm: FormGroup;
    qutationDetailsForm: FormGroup;
    submitted = false;
    dateFormat = 'dd-mm-yyyy'
    warranty;
    inWarranty: any = "No";
    hidePaymentDetailsForm = false;;
    checklist = [];
    paymentDetailsStatus: any = null;
    total: any = 0;
    serialNumberStatus = true;
    paymentModes = [];
    returnPolicy = [{ "name": "Yes", "value": true }, { "name": "No", "value": false }]
    warrantySelect = [{ "name": "Yes", "value": true }, { "name": "No", "value": false }]
    paymentStatuses = [{ "name": "Completed", "value": true }, { "name": "Pending", "value": false }]
    quotationId: any;
    selectedItems = [];
    checkedData = [];
    skuDetails = [];
    showCustomerDetailsForm = true;
    sparePartsTotal: any = 0
    dropdownSettings = {}
    d = new Date();
    serviceEntryDate: any;
    paymentMode: any = null;
    paymentDetailsForm: FormGroup;
    invoiceForm: FormGroup;
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
    };
    public productDetails: any = {
        'orderId': '',
        'orderDate': '',
        'skuCode': '',
        'productName': '',
        'productSerialNumber': '',
        'qrCode': '',
        'warranty': '',
        'warrantyInYears': '',
        'tenDaysReturnPolicy': null,
        'runTime': '',
        'customerRemarks': '',
        'receivedBy': '',
        'checklistItems': '',
        'servicingStatusesId': {
            "id": null
        },
        'customerDetailsId': {
            'id': null,
            'name': '',
            'phone': '',
            'emailId': '',
            'city': '',
            'state': '',
            'pincode': '',
            'address': ''
        }
    }
    successAlertMessages: any;
    public quatationDetails = {
        'id': null,
        'serviceCharges': null,
        'courierCharges': null,
        'otherCharges': null,
        'igst': null,
        'cgst': null,
        'sgst': null,
        'utgst': null,
        'transactionReferenceNumber': null,
        'paymentDate': '',
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
        this.dropdownSettings = {
            singleSelection: false,
            idField: 'id',
            textField: 'accessory',
            // selectAllText: 'Select All',
            unSelectAllText: 'UnSelect All',
            itemsShowLimit: 0,
            // allowSearchFilter: true
        };
        this.productService.getAllPaymentModes().subscribe(data => {
            this.paymentModes = data.data

        })
        this.getServicingProduct()
    }
    updatingId: any;
    details;
    getServicingProduct() {
        const ob = localStorage.getItem("servicingId");
        this.productService.getDetailsById(ob).subscribe(data => {
            this.details = data.data
            const date = this.details.servicingproduct.orderDate.split('-');
            this.updatingId = this.details.servicingproduct.id
            this.serviceEntryDate = this.details.servicingproduct.createdTime.substring(0, 10).split("-");
            this.productDetails.orderId = this.details.servicingproduct.orderId;
            this.productDetails.orderDate = this.details.servicingproduct.orderDate;
            this.productDetails.orderDate = { 'date': { 'year': Number(date[0]), 'month': Number(date[1]), 'day': Number(date[2]) } }
            this.productDetails.skuCode = this.details.servicingproduct.skuCode;
            this.productDetails.productName = this.details.servicingproduct.productName;
            this.productDetails.productSerialNumber = this.details.servicingproduct.productSerialNumber;
            this.productDetails.warranty = this.details.servicingproduct.warranty;
            this.warranty = !this.details.servicingproduct.warranty;
            this.productDetails.warrantyInYears = this.details.servicingproduct.warrantyInYears;
            this.productDetails.servicingStatusesId.id = this.details.servicingproduct.servicingStatusesId.id
            this.productDetails.qrCode = this.details.servicingproduct.qrCode;
            this.productDetails.tenDaysReturnPolicy = this.details.servicingproduct.tenDaysReturnPolicy;
            this.productDetails.runTime = this.details.servicingproduct.runTime;
            this.productDetails.customerDetailsId.id = this.details.servicingproduct.customerDetailsId.id;
            this.productDetails.customerDetailsId.name = this.details.servicingproduct.customerDetailsId.name;
            this.productDetails.customerDetailsId.phone = this.details.servicingproduct.customerDetailsId.phone;
            this.productDetails.customerDetailsId.emailId = this.details.servicingproduct.customerDetailsId.emailId;
            this.productDetails.customerDetailsId.address = this.details.servicingproduct.customerDetailsId.address;
            this.productDetails.customerDetailsId.city = this.details.servicingproduct.customerDetailsId.city;
            this.productDetails.customerDetailsId.state = this.details.servicingproduct.customerDetailsId.state;
            this.productDetails.customerDetailsId.pincode = this.details.servicingproduct.customerDetailsId.pincode;
            this.productDetails.customerRemarks = this.details.servicingproduct.customerRemarks;
            this.productDetails.receivedBy = this.details.servicingproduct.receivedBy
            if (this.details.quotationDetails != null) {
                this.quatationDetails.id = this.details.quotationDetails.id
                this.quatationDetails.igst = this.details.quotationDetails.igst;
                this.quatationDetails.cgst = this.details.quotationDetails.cgst;
                this.quatationDetails.sgst = this.details.quotationDetails.sgst;
                this.quatationDetails.utgst = this.details.quotationDetails.utgst;
                this.quatationDetails.serviceCharges = this.details.quotationDetails.serviceCharges;
                this.quatationDetails.courierCharges = this.details.quotationDetails.courierCharges;
                this.quatationDetails.otherCharges = this.details.quotationDetails.otherCharges;
            }
            if (this.details.spareParts != null) {
                for (let i = 0; i < this.details.spareParts.length; i++) {
                    (this.qutationDetailsForm.get('items') as FormArray).push(this.createItem(this.details.spareParts[i]))
                }
            }
            if (this.details.invoiceDetails != null) {
                this.invoiceForm.get('unicommerceReferenceNumber').setValue(this.details.invoiceDetails.unicommerceReferenceNumber);

            }
            if (this.details.paymentDetails != null) {
                let date1, arr;
                if (this.details.paymentDetails.paymentDate != null) {
                    date1 = this.details.paymentDetails.paymentDate;
                    arr = date1.split("-");
                    this.paymentDetailsForm.get('paymentDate').setValue({ 'date': { 'year': Number(arr[0]), 'month': Number(arr[1]), 'day': Number(arr[2]) } })
                }
                this.paymentDetailsForm.get('paymentStatus').setValue(this.details.paymentDetails.paymentStatus);
                this.paymentDetailsForm.get('bankReferenceNumber').setValue(this.details.paymentDetails.transactionReferenceNumber);
                const com = this.details.paymentDetails.comments
                this.paymentDetailsForm.get('comments').setValue(com);
                if (this.details.paymentDetails.paymentModeId != null) {
                    this.paymentDetailsForm.get('paymentModeId').get('id').setValue(this.details.paymentDetails.paymentModeId.id)
                }
            }

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


    createItem(obj) {

        if (obj) {

            return new FormGroup({
                'sku': new FormControl(obj.sku, Validators.required),
                'productName': new FormControl(obj.productName, Validators.required),
                'price': new FormControl(obj.price, Validators.required)
            })
        }
        else {
            return new FormGroup({
                'sku': new FormControl(null, Validators.required),
                'productName': new FormControl(null, Validators.required),
                'price': new FormControl(null, Validators.required),
            })
        }


    }
    createSkuDetails() {
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
                if (this.checkedIds[i] == a) {
                    this.checkedIds.splice(i, 1)
                }
            }
        }
    }
    onDeleteItem(index) {
        const formObj = this.qutationDetailsForm.get('items') as FormArray;
        this.sparePartsTotal -= formObj.value[index].price;
        (this.qutationDetailsForm.get('items') as FormArray).removeAt(index);
        this.skuDetails.splice(index, 1);
    }
    addProduct() {

    }

    getDetailsBySku(event) {
        const sku = event.target.value
        this.productService.getDetailsByskuCode(sku).subscribe(data => {
            this.productDetails.productName = data.data.productName
            this.productDetails.qrCode = data.data.qrCode
            if (data.data.serialNumberStaus) {
                this.serialNumberStatus = false;
                this.productDetails.productSerialNumber = data.data.serialNumber
            }
            else {
                this.serialNumberStatus = true;
                this.productDetails.productSerialNumber = '';
            }
        })
        this.getChecklist(sku)

    }
    noChecklist = true;

    public paymentDetailsSubmitted = false;
    public paymentDetailsErrorMessage: any;
    public paymentDetailsSuccessMessage: any;
    showInvoiceForm = false;
    addPaymentDetails() {
        let obj;
        if (this.paymentDetailsForm.valid) {
            let arr, date, month, days;
            if (this.paymentDetailsForm.value.paymentDate.formatted) {
                arr = this.paymentDetailsForm.value.paymentDate.formatted.split("-");
                date = `${arr[2]}-${arr[1]}-${arr[0]}`
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
            if (this.paymentDetailsForm.value.bankReferenceNumber == null) {
                this.paymentDetailsForm.value.bankReferenceNumber = ""
            }

            if (this.details.paymentDetails != null) {
                obj = {
                    "id": this.quatationDetails.id,
                    "transactionReferenceNumber": this.paymentDetailsForm.value.bankReferenceNumber,
                    "paymentModeId": {
                        'id': this.paymentDetailsForm.value.paymentModeId.id
                    },
                    "paymentStatus": this.paymentDetailsForm.value.paymentStatus,
                    "paymentDate": date,
                    "comments": this.paymentDetailsForm.value.comments
                }
                this.productService.updatePaymentDetails(this.quatationDetails.id, obj).subscribe(data => {
                    if (data.error == null) {
                        this.showPaymentDetailsForm = false;
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
                obj = {
                    "transactionReferenceNumber": this.paymentDetailsForm.value.bankReferenceNumber,
                    "paymentModeId": {
                        'id': this.paymentDetailsForm.value.paymentModeId.id
                    },
                    "paymentStatus": this.paymentDetailsForm.value.paymentStatus,
                    "paymentDate": date,
                    "comments": this.paymentDetailsForm.value.comments
                }
                this.productService.addPaymentDetails(this.sparePartsId, obj).subscribe(data => {
                    if (data.error == null) {
                        this.showPaymentDetailsForm = false;
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
        this.productService.updateInvoiceDetails(this.sparePartsId, this.invoiceForm.value).subscribe(data => {
            if (data.error == null) {
                this.invoiceDetailsMessage = "Details Updated Successfully";
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
        this.sparePartsSubmitted = false;
        (this.qutationDetailsForm.get('items') as FormArray).push(this.createItem({}));
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
    errorAlertMessages: any;
    getProductName(e) {
        this.productService.getProductDetails(e.target.value).subscribe(data => {
            const date = data.data.orderDate.split('-')
            this.productDetails.orderDate = { 'date': { 'year': Number(date[0]), 'month': Number(date[1]), 'day': Number(date[2]) } }
            this.productDetails.productName = data.data.productName;
            this.productDetails.skuCode = data.data.skucode
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

        })

    }
    onDateChanged(e) {

        this.productDetails.orderDate = e.date

    }
    detailsErrorMessage: any;
    quotationform = false;
    submitForm() {
        this.getTotal()
        this.addQuotationDetails()
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
            const date = `${this.productDetails.orderDate.date.year}-${month}-${day}`
            this.productDetails.orderDate = date;
            this.productService.updateServicingProduct(this.updatingId, this.productDetails).subscribe(data => {
                if (data.error == null) {
                    this.quotationform = true;
                    this.showCustomerDetailsForm = false;
                    this.quotationId = data.data.id
                    this.addChecklist(this.quotationId)
                }
                else {
                    this.errorAlertMessages = data.error.message
                    setTimeout(() => {
                        this.errorAlertMessages = ''
                    })
                }
            })

        }
        else {
            this.submitted = true;
            this.productDetails.orderDate = orderdate
        }
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

    }
    totalValue() {
        return this.quotationTotal + this.sparePartsTotal
    }

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
    hideQuotationForm = false;
    sparePartsId: any;
    showPaymentDetailsForm = false;
    quotationDetailsSubmitted = false;
    sparePartsSubmitted = false;
    addDetails() {
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
            if (this.details.quotationDetails != null) {
                this.productService.updateQuotationDetails(this.quatationDetails.id, this.quatationDetails).subscribe(data => {
                    if (data.error == null) {
                        this.sparePartsId = data.data.id;
                        this.productService.updateSpareParts(this.quatationDetails.id, this.qutationDetailsForm.value.items).subscribe(data1 => {
                            if (data1.error == null) {
                                this.quotationform = false;
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
                if (this.quatationDetails.id == null) {
                    this.quatationDetails.id = this.updatingId
                }
                this.productService.addQutationDetails(this.updatingId, this.quatationDetails).subscribe(data => {
                    if (data.error == null) {
                        this.sparePartsId = data.data.id;
                        this.productService.updateSpareParts(this.sparePartsId, this.qutationDetailsForm.value.items).subscribe(data1 => {
                            if (data1.error == null) {
                                this.quotationform = false;
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
        }
        else {
            this.sparePartsSubmitted = true;
            this.quotationDetailsSubmitted = true;

            // alert("Invalid")
        }

    }

}
