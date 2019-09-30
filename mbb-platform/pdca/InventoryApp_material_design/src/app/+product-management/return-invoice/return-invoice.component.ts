import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
// import {Router} from '@angular/router';
import { AppService } from '../../app.service';
import { FormGroup, FormControl, AbstractControl, FormBuilder, Validators } from '@angular/forms';
import * as moment from 'moment';
import { MatDialog } from '@angular/material';
declare var cordova: any;
import { PlatformLocation } from '@angular/common';
import { QRCodeScanner } from '../../+qrcode-scanner/qrcode-scanner.service';
@Component({
    selector: 'app-return-invoice',
    templateUrl: './return-invoice.component.html',
    styles: [`
    .footer-text{
        font-weight:bold
    }
    .mat-focused .placeholder {
        color: #AAAAAA;
        }
      .placeholder {
         color:#AAAAAA;
        }
    `]
})

export class ReturnInvoiceComponent implements OnInit {
    invoiceId;
    demoInvoiceId;
    public invoiceForm: FormGroup;
    public rentalInvoiceForm: FormGroup;
    public demoInvoiceForm: FormGroup;
    public rentalReasonForm: FormGroup;
    public returnForm: FormGroup;
    formValidate: boolean;
    demoFormValidate: boolean
    submitted: boolean;
    productData: any;
    rentalProductData: any
    demoProductData: any;
    status = false;
    typeofReturn: any = "";
    returnManagementForm: FormGroup;
    rentalReason;
    showNormalReturn: boolean = false;
    showRentalreturn: boolean = false;
    showDemoreturn: boolean = false;
    Returns = [{ "id": 1, "type": "Normal" }, { "id": 2, "type": "Rental" }, { "id": 3, "type": "Demo" }]
    constructor(
        private readonly router: Router,
        private readonly _qrScanner: QRCodeScanner,
        private readonly fb: FormBuilder,
        private readonly appService: AppService,
        public dialog: MatDialog,
    ) {
        this.invoiceForm = this.fb.group({
            invoice: [null, [Validators.required]],
        })
        this.rentalInvoiceForm = this.fb.group({
            invoiceId: [null, [Validators.required]],
        })
        this.demoInvoiceForm = this.fb.group({
            invoice: [null, [Validators.required]]
        })
        this.rentalReasonForm = this.fb.group({
            rentalReason: [null, [Validators.required]]
        })
        this.returnForm = new FormGroup({
            'type': new FormControl(null, Validators.required),
            'comments': new FormControl(null)
        });
        this.returnManagementForm = new FormGroup({
            'returnType': new FormControl(null, Validators.required)
        })

    }

    resultQrcode: any;
    array = [];
    rentalArray = []
    demoArray = []

    getInvoiceBybarcode() {
        this._qrScanner.promiseScan().then(result => {
            this.resultQrcode = result;
            if (!this.resultQrcode.cancelled) {
                this.invoiceId = this.resultQrcode.text
                localStorage.setItem('invoiceId', this.resultQrcode.text)
            }
        });
    }
    dispatchedInvoices; invoice;
    dispatchedDemoInvoices; ReturnTypes = [];

    ngOnInit() {
        /**
         * Back Button event trigger
         */
        history.pushState(null, null, location.href);
        window.onpopstate = function (event) {
            history.go(1);
        };
        this.appService.getDemoReturnStatuses().subscribe(data => {
            if (data.error == null) {
                this.ReturnTypes = data.data;
            }
        })
        this.getIdChange();
        this.getIdChangeInRental()
        this.getDemoInvoices();

    }
    changeReturn() {
        // const a=this.typeofReturn;
        if (this.typeofReturn == 1) {
            this.showNormalReturn = true;
            this.showRentalreturn = false;
            this.showDemoreturn = false;
            this.array = [];
            this.rentalArray = [];
            this.demoArray = [];
            this.invoiceForm.reset();
            this.rentalInvoiceForm.reset();
            this.demoInvoiceForm.reset();
        }
        else if (this.typeofReturn == 2) {
            this.showNormalReturn = false;
            this.showRentalreturn = true;
            this.showDemoreturn = false;
            this.array = [];
            this.rentalArray = [];
            this.demoArray = [];
            this.invoiceForm.reset();
            this.rentalInvoiceForm.reset();
            this.demoInvoiceForm.reset();
        }
        else {
            this.showNormalReturn = false;
            this.showRentalreturn = false;
            this.showDemoreturn = true;
            this.array = [];
            this.rentalArray = [];
            this.demoArray = [];
            this.invoiceForm.reset();
            this.rentalInvoiceForm.reset();
            this.demoInvoiceForm.reset();
        }
    }
    rentalReasons;
    getAllStatusesRentalReasons() {
        this.appService.getAllStatusesRentalReasons().subscribe((data: any) => {
            if (data.error == null) {
                this.rentalReasons = data.data
            }
        })
    }

    getIdChange() {
        this.appService.getAllDispatchedInvoices().subscribe((data: any) => {
            if (data.data != null) {
                this.dispatchedInvoices = data.data
            }
        })
    }
    getDemoInvoices() {
        // this.appService.getAllDemoDispatchedProducts
        this.appService.getAllDemoDispatchedProducts().subscribe((data: any) => {
            if (data.data != null) {
                this.dispatchedDemoInvoices = data.data
            }
        })
    }

    filteredInvoicesList: any = [];
    filteredDemoInvoicesList: any = [];
    getInvoiceSbasedonSearch() {
        this.invoiceSelection = false;
        this.filteredInvoicesList = [];
        if (this.invoiceId) {
            this.dispatchedInvoices.find((data) => {
                if ((data.toLowerCase()).includes(this.invoiceId.toLowerCase())) {
                    this.filteredInvoicesList.push(data)
                }
            })
        }

    }
    getDemosBasedonSearch() {
        this.demoInvoiceSelection = false;
        this.filteredDemoInvoicesList = [];
        if (this.demoInvoiceId) {
            this.dispatchedDemoInvoices.find((data) => {
                if ((data.toLowerCase()).includes(this.demoInvoiceId.toLowerCase())) {
                    this.filteredDemoInvoicesList.push(data)
                }
            })
        }
    }

    invoiceSelection = false;
    object;
    selectInvoice(obj) {
        this.invoiceSelection = true;
        this.invoiceId = obj;
        this.filteredInvoicesList = [];
    }
    demoInvoiceSelection = false;
    selectDemoInvoice(obj) {
        this.invoiceSelection = true;
        this.demoInvoiceId = obj;
        this.filteredDemoInvoicesList = [];
    }
    hasCar = false;
    noCarToastr(event: Event) {
        let me = this;
        if (!this.hasCar) {
            event.preventDefault()
        }
    }

    rentalInvoiceId;

    dispatchedInvoicesInRental;
    getIdChangeInRental() {
        this.appService.getAllDispatchedInvoicesInRental().subscribe((data: any) => {
            if (data.data != null) {
                this.dispatchedInvoicesInRental = data.data
            }
        })
    }

    filteredInvoicesListInRental: any = [];
    getInvoiceSbasedonSearchInRental() {
        this.invoiceSelectionInRental = false;
        this.filteredInvoicesListInRental = [];
        if (this.rentalInvoiceId) {
            this.dispatchedInvoicesInRental.find((data) => {
                if ((data.toLowerCase()).includes(this.rentalInvoiceId.toLowerCase())) {
                    this.filteredInvoicesListInRental.push(data)
                }
            })
        }
    }

    invoiceSelectionInRental = false;
    selectInvoiceInRental(obj) {
        this.invoiceSelectionInRental = true;
        this.rentalInvoiceId = obj;
        this.filteredInvoicesListInRental = [];
    }
    rental = false;
    navigatetoInvoice() {
        if (this.invoiceForm.valid) {
            this.rental = true;
            this.demo = true;
            localStorage.setItem('invoiceId', this.invoiceId);
            this.appService.getDetailsByInvoice(this.invoiceForm.value.invoice).subscribe((data: any) => {
                if (data.data != null) {
                    this.productData = data.data
                    this.array = data.data;
                    this.status = true
                    for (const i of this.productData) {
                        i.condition = false
                    }
                }
                if (data.data == "") {
                    this.openDialog(data.error.message)
                    this.invoiceForm.reset()
                }
            })
        } else {
            this.formValidate = true
        }
    }
    normalInvoice = false;
    navigatetoRentalInvoice() {
        if (this.rentalInvoiceForm.valid) {
            this.normalInvoice = true;
            this.demo = true;
            localStorage.setItem('invoiceId', this.rentalInvoiceId);
            this.appService.checkInvoice(this.rentalInvoiceForm.value.invoiceId).subscribe((data1: any) => {
                if (data1.error == null) {
                    this.appService.getDetailsByInvoice(this.rentalInvoiceForm.value.invoiceId).subscribe((data: any) => {
                        if (data.data != null) {
                            this.rentalProductData = data.data
                            this.rentalArray = data.data;
                            this.status = true
                            for (const i of this.rentalProductData) {
                                i.condition = true
                            }
                            this.getAllStatusesRentalReasons();
                        }
                        if (data.data == "") {
                            this.openDialog(data.error.message)
                            this.rentalInvoiceForm.reset()
                        }
                    })
                } else {
                    this.openDialog(data1.error.message)
                }

            })
        } else {
            this.submitted = true
        }
    }
    demo = false;
    navigatetoDemoInvoice() {
        if (this.demoInvoiceForm.valid) {
            this.appService.getReturnProductInformation(this.demoInvoiceForm.get('invoice').value).subscribe(data1 => {
                if (data1.error == null) {
                    localStorage.setItem('invoiceId', this.demoInvoiceId);
                    this.appService.getDetailsByInvoice(this.demoInvoiceForm.value.invoice).subscribe((data: any) => {
                        if (data.data != null) {
                            this.demoProductData = data.data
                            this.demoArray = data.data;
                            this.status = true
                            for (const i of this.demoProductData) {
                                i.condition = true
                            }
                        }
                        if (data.data == "") {
                            this.openDialog(data.error.message)
                            this.demoInvoiceForm.reset()
                        }
                    })
                }
                else {
                    this.openDialog(data1.error.message)
                }
            })
        } else {
            this.demoFormValidate = true
        }
    }
    productArray: any = []


    navigatetoHomePage() {
        this.router.navigate(['./inventory']);
    }

    openDialog(msgText) {
        const dialogRef = this.dialog.open(DialogComponent, { disableClose: true });
        localStorage.setItem('msg', msgText)
        dialogRef.afterClosed().subscribe(result => {
        });
    }



    submit() {
        const productArray: any = []
        for (let i = 0; i < this.productData.length; i++) {
            if (this.productData[i].condition) {
                productArray.push(this.productData[i])
            }
        }
        if (productArray.length > 0) {
            this.appService.add(productArray)
            this.router.navigate(['/product/return'])
        }
    }
    rentalSub;
    submitRental() {
        this.rentalSub = false;
        if (this.rentalReasonForm.valid) {
            localStorage.setItem("type", "rental")
            localStorage.setItem("reason", this.rentalReason)
            const productArray: any = []
            for (let i = 0; i < this.rentalProductData.length; i++) {
                if (this.rentalProductData[i].condition) {
                    productArray.push(this.rentalProductData[i])
                }
            }
            if (productArray.length > 0) {
                this.appService.add(productArray)
                this.router.navigate(['/product/return'])
            }
        } else {
            this.rentalSub = true;
        }
    }
    returnFormSubmitted: boolean = false;
    submitDemo() {
        const productArray: any = []
        if (this.returnForm.valid) {
            for (let i = 0; i < this.demoProductData.length; i++) {
                if (this.demoProductData[i].condition) {
                    productArray.push(this.demoProductData[i])
                }
            }
            if (productArray.length > 0) {
                this.appService.add(productArray)
                localStorage.setItem("reason", this.returnForm.get('type').value);
                localStorage.setItem("comments", this.returnForm.get('comments').value);
                localStorage.setItem("demoId", this.demoInvoiceForm.get('invoice').value);
                this.router.navigate(['/product/return'])
            }
        }
        else {
            this.returnFormSubmitted = true;
        }
    }

}

@Component({
    selector: 'dialog-content-example-dialog',
    template: `<img class="mat-typography img1" src="assets/img/logom.png" alt="MedicalBulkBuy" width="90%"   >
    <mat-dialog-content class="mat-typography" style="border-bottom:1px solid #ddd;border-top:1px solid #ddd; text-align:center">
     
    <h5 style="padding: 18px;margin: 0px;font-size: 14px;font-family: sans-serif;">{{msgText}}</h5>
    </mat-dialog-content>
   
    <mat-dialog-actions align="center" >
    <button mat-stroked-button [mat-dialog-close]="true" cdkFocusInitial > 
    <mat-icon>done</mat-icon>
    Ok
</button>
    </mat-dialog-actions>
  `,
})
export class DialogComponent {
    msgText: any;
    ngOnInit() {
        this.msgText = localStorage.getItem('msg');
    }
}

