import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '../../app.service';
import { FormGroup, FormControl, AbstractControl, FormBuilder, Validators } from '@angular/forms';
import * as moment from 'moment';
import { MatDialog } from '@angular/material';
declare var cordova: any;
import { PlatformLocation } from '@angular/common';

import { QRCodeScanner } from '../../+qrcode-scanner/qrcode-scanner.service';
import { DomSanitizer } from '@angular/platform-browser';
declare var cordova: any;

declare let navigator: any;
declare let Camera: any;
@Component({
    selector: 'rental-invoice',
    templateUrl: './rental.component.html',
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
        .ui-overlay-c {
            background-color: rgba(0, 0, 0, 0.5);
            position: fixed;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            text-align: center;
        }
        .loading {
          position: absolute;
          top: 50%;
          left: 50%;
        }
        .loading-bar {
         
          display: inline-block;
          width: 4px;
          height: 18px;
          border-radius: 4px;
          animation: loading 1s ease-in-out infinite;
        }
        .loading-bar:nth-child(1) {
          background-color: #3498db;
          animation-delay: 0;
        }
        .loading-bar:nth-child(2) {
          background-color: #c0392b;
          animation-delay: 0.09s;
        }
        .loading-bar:nth-child(3) {
          background-color: #f1c40f;
          animation-delay: .18s;
        }
        .loading-bar:nth-child(4) {
          background-color: #27ae60;
          animation-delay: .27s;
        }
        
        @keyframes loading {
          0% {
            transform: scale(1);
          }
          20% {
            transform: scale(1, 2.2);
          }
          40% {
            transform: scale(1);
          }
        }
        md-checkbox .md-icon {
    background: red;
}
md-checkbox.md-default-theme.md-checked .md-icon {
    background: orange;
}

 
    `]
})

export class RentalComponent implements OnInit {
    public invoiceForm: FormGroup;
    formValidate: boolean
    resultQrcode: any;
    invoiceId;
    productData: any;
    channelId: any;
    payId: any;
    receivedBy: any = '';
    comments: any;
    paymentModeList: any = [];
    channelList: any;
    noofProducts;
    postObj: any = {

    };




    constructor(
        private readonly router: Router,
        private readonly _qrScanner: QRCodeScanner,
        private readonly fb: FormBuilder,
        private readonly appservice: AppService,
        public dialog: MatDialog,
        private _sanitizer: DomSanitizer,
        private cdr: ChangeDetectorRef

    ) {
        this.invoiceForm = this.fb.group({
            invoice: [null, [Validators.required]],
            channelId: [null, [Validators.required]],
            payId: [null, [Validators.required]],
            noofProducts: [null, [Validators.required]],
            // aadhar: [null],
            // pancard: [null],
            // payment: [null, [Validators.required]],
            receivedBy: [null, [Validators.required]],
            comments: [null, [Validators.required]],
        })
        // this.invoiceId = this.invoiceForm.controls['INVOICE']
    }

    doctorPrescript = ''; aadharId = '';
    ngOnInit() {
        /**
         * Back Button event trigger
         */
        history.pushState(null, null, location.href);
        window.onpopstate = function (event) {
            history.go(1);
        };
        this.payId = '';
        this.doctorPrescript = '';
        this.aadharId = '';
        this.channelId = 'Custom';
        this.appservice.getAllPaymentModes().subscribe((data: any) => {
            if (data.error == null) {
                this.paymentModeList = data.data;
            }
            else {
                this.openDialog(data.error.message)
            }
        });
        this.appservice.getAllChannels().subscribe((data: any) => {
            if (data.error == null) {
                this.channelList = data.data;
            }
            else {
                this.openDialog(data.error.message)
            }
        });
        const receivedBy = this.invoiceForm.get('receivedBy');
        this.invoiceForm.get('payId').valueChanges.subscribe(
            (value) => {
                if (value == 1) {
                    receivedBy.setValidators(Validators.compose([Validators.required]));
                }
                else {
                    receivedBy.clearValidators()
                    receivedBy.updateValueAndValidity()
                }
            });
        this.getIdChange();

    }

    change() {
        const comments = this.invoiceForm.get('comments');
        if (this.invoiceForm.get('payId').value != 8) {
            comments.setValidators(Validators.compose([Validators.required]));
        }
        else {
            comments.clearValidators()
            comments.updateValueAndValidity()
        }
    }
    changePayModeOptions() {
        if (this.channelId != 'Custom') {
            this.paymentModeList = []
            this.paymentModeList.push(
                { "id": 8, "types": "COD" },
                { "id": 10, "types": "ONLINE" }
            )
        } else {
            this.paymentModeList = []
            this.paymentModeList.push(
                { "id": 1, "types": "CASH" },
                { "id": 2, "types": "CREDIT_CARD" },
                { "id": 3, "types": "NET_BANKING" },
                { "id": 4, "types": "DEBIT_CARD" },
                { "id": 5, "types": "WALLET" },
                { "id": 6, "types": "CHEQUE" },
                { "id": 7, "types": "INDIA_MART" },
                { "id": 8, "types": "COD" },
                { "id": 9, "types": "UPI" },
                { "id": 10, "types": "ONLINE" }
            )
        }
    }
    invoiceSelection = false;
    object;
    selectInvoice(obj) {
        this.invoiceSelection = true;
        this.invoiceId = obj;
        localStorage.setItem('invoiceId', this.invoiceId);
        this.filteredInvoicesList = [];
    }
    dispatchedInvoices;
    getIdChange() {
        this.appservice.getAllRentalDispatchedInvoices().subscribe((data: any) => {
            if (data.error == null) {
                this.dispatchedInvoices = data.data
            }
        })
    }
    filteredInvoicesList: any = [];
    getInvoicesbasedonSearch() {
        this.invoiceSelection = false;
        this.filteredInvoicesList = [];
        if (this.invoiceId) {
            this.dispatchedInvoices.find((data) => {
                if (data != null) {
                    if ((data.toLowerCase()).includes(this.invoiceId.toLowerCase())) {
                        this.filteredInvoicesList.push(data)
                    }
                }
            })
        }
    }
    details: any = [{ 'binaryData': [], 'names': [] }];
    loaderBtn: boolean = false;
    receiptImg: any = ''; img: any; selectedImage: any; aadhar: any = ''; prescription: any = '';
    prescriptionSelection: any = [{ "id": 4, "name": "Upload Image" }, { "id": 6, "name": "Take Picture" }];
    aadharSelection: any = [{ "id": 3, "name": "Upload Image" }, { "id": 5, "name": "Take Picture" }];
    takePicture(field) {
        // this.selectedImage = field;
        // if (this.selectedImage == 1 || this.selectedImage == 2) {
        //     this.receiptImg = "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"
        // }
        // if (this.selectedImage == 5) {           
        //     this.aadhar = "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"
        // }
        // if (this.selectedImage == 3) {           
        //     this.aadhar = "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"
        // }
        // if (this.selectedImage == 6 || this.selectedImage == 4) {
        //     this.prescription = "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"
        // }
        this.loaderBtn = true;
        this.selectedImage = field;
        if (navigator != undefined) {
            if (this.selectedImage == 2 || this.selectedImage == 5 || this.selectedImage == 6) {
                var options = {
                    quality: 10,
                    allowEdit: true,
                    destinationType: Camera.DestinationType.DATA_URL,
                    sourceType: Camera.PictureSourceType.CAMERA,
                    encodingType: Camera.EncodingType.PNG,
                    targetWidth: 500,
                    targeHeight: 500,
                    correctOrientation: true,
                };
            } else if (this.selectedImage == 1 || this.selectedImage == 3 || this.selectedImage == 4) {
                var options = {
                    quality: 10,
                    allowEdit: true,
                    destinationType: Camera.DestinationType.DATA_URL,
                    sourceType: Camera.PictureSourceType.PHOTOLIBRARY,
                    encodingType: Camera.EncodingType.PNG,
                    targetWidth: 500,
                    targeHeight: 500,
                    correctOrientation: true
                };
            }
            this.cdr.detectChanges();
            navigator.camera.getPicture(
                (base64string) => {
                    this.loaderBtn = false;
                    if (this.selectedImage == 1 || this.selectedImage == 2) {
                        this.receiptImg = base64string;
                    }
                    if (this.selectedImage == 3 || this.selectedImage == 5) {
                        this.aadhar = base64string;
                    }
                    if (this.selectedImage == 4 || this.selectedImage == 6) {
                        this.prescription = base64string;
                    }
                    this.cdr.detectChanges();
                },
                (error) => {
                    this.cdr.detectChanges();
                    this.loaderBtn = false;
                    if (this.selectedImage == 3 || this.selectedImage == 5) {
                        this.aadharId = '';
                        this.cdr.detectChanges();
                    }
                    if (this.selectedImage == 4 || this.selectedImage == 6) {
                        this.doctorPrescript = '';
                        this.cdr.detectChanges();
                    }
                    if (this.aadhar) {
                        this.aadhar = '';
                    }
                    if (this.prescription) {
                        this.prescription = '';
                    }
                    alert("Unable to obtain picture: " + error);
                    this.cdr.detectChanges();
                }, options);
        }
    }

    deleteReceiptImg(id) {
        if (id == 1) {
            this.receiptImg = '';
            this.cdr.detectChanges();
        }
        if (id == 2) {
            this.aadhar = '';
            this.cdr.detectChanges();
            this.aadharId = ''
            this.cdr.reattach();
            this.cdr.detectChanges();
        }
        if (id == 3) {
            this.prescription = '';
            this.cdr.detectChanges();
            this.doctorPrescript = ''
            this.cdr.reattach();
            this.cdr.detectChanges();
        }
    }
    submit() {
        if (this.channelId == 'Custom' && this.payId != 8) {
            if (this.receiptImg != '' && this.invoiceForm.valid) {
                this.navigatetoInvoice()
            } else {
                this.formValidate = true
            }
        } else {
            this.navigatetoInvoice()
        }
    }
    imageId: any = '';
    navigatetoInvoice() {
        if (this.invoiceForm.valid) {
            localStorage.setItem('invoiceId', this.invoiceId);
            localStorage.setItem('noofProducts', this.noofProducts);
            localStorage.setItem('paymentMode', this.payId);
            localStorage.setItem('channel', '7');
            localStorage.setItem('receivedBy', this.receivedBy);
            const postObj: any = {
                comments: this.comments,
                invoiceNumber: this.invoiceId,
                proofs: []
            };
            if (this.receiptImg) {
                postObj.proofs.push(this.receiptImg)
            }
            if (this.aadhar) {
                postObj.proofs.push(this.aadhar)
            }
            if (this.prescription) {
                postObj.proofs.push(this.prescription)
            }
            if ((this.channelId == 'Custom' && this.payId != 8) || this.prescription || this.aadhar) {
                this.appservice.getImageId(postObj).subscribe((data1: any) => {
                    if (data1.error == null) {
                        this.imageId = data1.data.id;
                        localStorage.setItem('imageId', this.imageId);
                        this.postObj.comments = '';
                        this.postObj.invoiceNumber = '';
                        this.postObj.proofs = [];
                        if (this.imageId != "") {
                            this.dispatch();
                        } else {
                            this.openDialog("Fail to get Image Id")
                        }
                    }
                    else {
                        this.postObj.comments = '';
                        this.postObj.invoiceNumber = '';
                        this.postObj.proofs = [];
                        this.openDialog(data1.error.message)
                    }
                })
            } else {
                localStorage.setItem('imageId', '');
                this.dispatch();
            }
        }
        else {
            this.formValidate = true
        }
    }
    dispatch() {
        this.appservice.invoiceCheck(this.invoiceId).subscribe((data: any) => {
            if (data.error == null) {
                this.appservice.getDetailsByDispatchInvoice(this.invoiceId).subscribe((data: any) => {
                    if (data.error == null) {
                        localStorage.setItem('dispatchType', '3');
                        // localStorage.setItem('QRCode', '0175-0000085')
                        // this.router.navigate(['/dispatch/invoice-view'])
                        this._qrScanner.promiseScan().then(result => {
                            this.resultQrcode = result;
                            if (!this.resultQrcode.cancelled) {
                                localStorage.setItem('QRCode', this.resultQrcode.text)
                                this.router.navigate(['/dispatch/invoice-view'])
                            }
                        })
                    }
                    else {
                        this.openDialog(data.error.message)
                    }
                })
            }
            else {
                this.openDialog(data.error.message)
            }
        })
    }

    navigatetoHomePage() {
        this.router.navigate(['./dispatch']);
    }
    openDialog(msgText) {
        const dialogRef = this.dialog.open(DialogComponent, { disableClose: true });
        localStorage.setItem('msg', msgText)
        dialogRef.afterClosed().subscribe(result => {
            if (result) {

            }
        });
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
