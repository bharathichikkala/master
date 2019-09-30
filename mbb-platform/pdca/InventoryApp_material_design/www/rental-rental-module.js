(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["rental-rental-module"],{

/***/ "./src/app/+dispatch/+rental/rental.component.html":
/*!*********************************************************!*\
  !*** ./src/app/+dispatch/+rental/rental.component.html ***!
  \*********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"main-content\">\n    <div class=\"container-fluid\">\n        <div class=\"row\">\n            <div class=\"col-md-8\">\n                <div class=\"card\">\n                    <div class=\"card-header card-header-info\">\n                        <h4 class=\"card-title\" style=\"text-align:center\">Dispatch Product</h4>\n                    </div>\n                    <div class=\"card-body\">\n                        <form [formGroup]=\"invoiceForm\">\n                            <div class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <mat-form-field class=\"example-full-width\">\n                                        <input matInput name=\"invoiceId\" formControlName=\"invoice\"\n                                            (keyup)=\"getInvoicesbasedonSearch()\" [(ngModel)]=\"invoiceId\" type=\"text\">\n                                        <mat-placeholder class=\"placeholder\">Invoice Id</mat-placeholder>\n                                    </mat-form-field>\n                                    <div class=\"height-skus\">\n                                        <div *ngFor=\"let key of filteredInvoicesList\" (click)=\"selectInvoice(key)\"\n                                            style=\"border-bottom:1px solid #AAAAAA;padding: 3px\">\n                                            {{key}} </div>\n                                    </div>\n                                    <span *ngIf=\"invoiceForm.controls['invoice'].hasError('required') &&formValidate \"\n                                        class=\"text-danger\">Please\n                                        enter InvoiceId</span>\n                                </div>\n                            </div>\n                            <div class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <mat-form-field class=\"example-full-width\">\n                                        <input matInput formControlName=\"noofProducts\" type=\"number\"\n                                            [(ngModel)]=\"noofProducts\">\n                                        <mat-placeholder class=\"placeholder\">No.of Products</mat-placeholder>\n                                    </mat-form-field>\n                                    <span\n                                        *ngIf=\"invoiceForm.controls['noofProducts'].hasError('required') &&formValidate \"\n                                        class=\" text-danger \">\n                                        Please enter no.of products</span>\n                                </div>\n                            </div>\n                            <div class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <mat-form-field class=\"example-full-width\">\n                                        <input matInput formControlName=\"channelId\" type=\"text\" [(ngModel)]=\"channelId\"\n                                            readonly>\n                                        <mat-placeholder class=\"placeholder\">Channel</mat-placeholder>\n                                    </mat-form-field>\n                                </div>\n                            </div>\n                            <div class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <mat-form-field>\n                                        <label>Payment Mode</label>\n                                        <mat-select matInput formControlName=\"payId\" [(ngModel)]=\"payId\" required\n                                            (selectionChange)=\"change()\">\n                                            <mat-option value=\"\" disabled>Select</mat-option>\n                                            <mat-option *ngFor=\"let pay of paymentModeList\" [value]=\"pay.id\">\n                                                {{pay.types}}</mat-option>\n                                        </mat-select>\n                                    </mat-form-field>\n                                </div>\n                            </div>\n                            <div *ngIf='payId==1' class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <mat-form-field class=\"example-full-width\">\n                                        <input matInput formControlName=\"receivedBy\" type=\"text\"\n                                            [(ngModel)]=\"receivedBy\">\n                                        <mat-placeholder class=\"placeholder\">Received by</mat-placeholder>\n                                    </mat-form-field>\n                                    <span\n                                        *ngIf=\"invoiceForm.controls['receivedBy'].hasError('required') &&formValidate \"\n                                        class=\" text-danger \">\n                                        Please enter person name</span>\n\n                                </div>\n                            </div>\n                            <div *ngIf='(channelId==\"Custom\" && payId!=8)' class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <label><b>Upload Payment Image</b>\n                                    </label><br>\n                                    <div class=\" col-sm-10 col-10\" class='row'>\n                                        <div *ngIf=\"receiptImg\" class=\" col-sm-6 col-6\">\n                                            <div style=\"text-align:right\" (click)=\"deleteReceiptImg(1)\"> <i\n                                                    class=\"fa fa-times\"\n                                                    style=\"margin-right:-15px;border: 1px solid black;padding: 1px 7px; \"></i>\n                                            </div>\n                                        </div>\n                                        <div *ngIf=\"!receiptImg\" class=\" col-sm-4 col-4\">\n                                            <img src=\"assets/img/upload-img.png\" width=\"100%\" height=\"100px\"\n                                                (click)=\"takePicture(1)\"><br>Upload Photo</div>&nbsp;&nbsp;\n                                        <div *ngIf=\"!receiptImg\" class=\" col-sm-4 col-4\">\n                                            <img src=\"assets/img/camera.png\" width=\"100%\" height=\"100px\"\n                                                (click)=\"takePicture(2)\"><br>Camera</div>\n                                        <div *ngIf=\"receiptImg\">\n                                            <img [src]=\"'data:image/jpg;base64,'+receiptImg\" width=\"50%\" height=\"125px\">\n                                        </div>\n                                    </div>\n                                    <span *ngIf=\"receiptImg=='' &&formValidate\" class=\" text-danger \">Please select\n                                        Payment image</span>\n                                </div>\n                            </div>\n\n                            <br>\n                            <div class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <label><b>Upload Address Proof</b>\n                                    </label>\n                                    <div class='row'>\n                                        <div class=\" col-sm-10 col-10\">\n                                            <mat-form-field>\n                                                <mat-select matInput [(ngModel)]=\"aadharId\"\n                                                    [ngModelOptions]=\"{standalone:true}\">\n                                                    <mat-option value=\"\" disabled>Select</mat-option>\n                                                    <mat-option *ngFor=\"let aadhar of aadharSelection\"\n                                                        [value]=\"aadhar.id\" (click)=\"takePicture(aadhar.id)\">\n                                                        {{aadhar.name}}</mat-option>\n                                                </mat-select>\n                                            </mat-form-field>\n                                        </div>\n                                        <!-- <div *ngIf=\"!aadhar\" class=\" col-sm-4 col-4\">\n                                            <img src=\"assets/img/upload-img.png\" width=\"100%\" height=\"100px\"\n                                                (click)=\"takePicture(3)\"><br>Address Proof</div>&nbsp;&nbsp;\n                                        <div *ngIf=\"!aadhar\" class=\" col-sm-4 col-4\">\n                                            <img src=\"assets/img/camera.png\" width=\"100%\" height=\"100px\"\n                                                (click)=\"takePicture(5)\"><br>Camera</div> -->\n                                    </div>\n\n                                    <div *ngIf=\"aadhar\" class=\" col-sm-8 col-8\">\n                                        <div class='row'>\n                                            <div class='col-sm-8 col-8' style=\"margin-left: -26px;\"></div>\n                                            <div style=\"text-align:right\" (click)=\"deleteReceiptImg(2)\"> <i\n                                                    class=\"fa fa-times\"\n                                                    style=\"margin-right:-15px;border: 1px solid black;padding: 1px 7px; \"></i>\n                                            </div>\n                                            <img [src]=\"'data:image/jpg;base64,'+aadhar\" width=\"67%\" height=\"125px\">\n                                        </div>\n                                    </div>\n                                </div>\n                            </div>\n                            <br>\n                            <div class=\"row\"><br>\n                                <div class=\"col-sm-10 col-10\">\n                                    <label><b>Upload Doctor Prescription</b>\n                                    </label><br>\n                                    <div class=\" col-sm-10 col-10\" class='row'>\n                                        <div class=\" col-sm-10 col-10\">\n                                            <mat-form-field>\n                                                <mat-select matInput [(ngModel)]=\"doctorPrescript\"\n                                                    [ngModelOptions]=\"{standalone:true}\">\n                                                    <mat-option value=\"\" disabled>Select</mat-option>\n                                                    <mat-option *ngFor=\"let prescription of prescriptionSelection\"\n                                                        [value]=\"prescription.id\"\n                                                        (click)=\"takePicture(prescription.id)\">\n                                                        {{prescription.name}}</mat-option>\n                                                </mat-select>\n                                            </mat-form-field>\n                                        </div>\n                                    </div>\n                                    <!-- <div *ngIf=\"!prescription\" class=\" col-sm-4 col-4\">\n                                            <img src=\"assets/img/upload-img.png\" width=\"100%\" height=\"100px\"\n                                                (click)=\"takePicture(4)\"><br>Prescription</div>&nbsp;&nbsp;\n                                        <div *ngIf=\"!prescription\" class=\" col-sm-4 col-4\">\n                                            <img src=\"assets/img/camera.png\" width=\"100%\" height=\"100px\"\n                                                (click)=\"takePicture(6)\"><br>Camera</div> -->\n                                    <div *ngIf=\"prescription\" class=\" col-sm-8 col-8\">\n                                        <div class='row'>\n                                            <div class='col-sm-8 col-8' style=\"margin-left: -26px;\"></div>\n                                            <div style=\"text-align:right\" (click)=\"deleteReceiptImg(3)\"> <i\n                                                    class=\"fa fa-times\"\n                                                    style=\"margin-right:-15px;border: 1px solid black;padding: 1px 7px; \"></i>\n                                            </div>\n                                            <img [src]=\"'data:image/jpg;base64,'+prescription\" width=\"67%\"\n                                                height=\"125px\">\n                                        </div>\n                                    </div>\n                                </div>\n                            </div>\n                            <div *ngIf='payId!=8' class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <mat-form-field class=\"example-full-width\">\n                                        <textarea matInput [(ngModel)]=\"comments\" formControlName=\"comments\"\n                                            required></textarea>\n                                        <mat-placeholder class=\"placeholder\">Comments</mat-placeholder>\n                                    </mat-form-field>\n                                    <span *ngIf=\"formValidate && invoiceForm.controls['comments'].hasError('required')\"\n                                        class=\" text-danger\">please\n                                        enter comments</span>\n                                </div>\n                            </div>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-success pull-right\"\n                                (click)=\"submit()\">Submit</button>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-danger pull-right\"\n                                (click)=\"navigatetoHomePage()\">Cancel</button>\n                        </form>\n                    </div>\n                </div>\n            </div>\n        </div>\n        <div>\n            <div *ngIf=\"loaderBtn\">\n                <div class=\"ui-overlay-c\">\n                    <div class=\"loading\" style=\" margin: 0;\n                        position: absolute;\n                        top: 50%;\n                        left: 50%;\n                        transform: translate(-50%, -50%);\">\n                        <div class=\"loading-bar\"></div>\n                        <div class=\"loading-bar\"></div>\n                        <div class=\"loading-bar\"></div>\n                        <div class=\"loading-bar\"></div>\n                        <h4 style=\"color: #e0dcd4\">Loading....</h4>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n\n</div>"

/***/ }),

/***/ "./src/app/+dispatch/+rental/rental.component.ts":
/*!*******************************************************!*\
  !*** ./src/app/+dispatch/+rental/rental.component.ts ***!
  \*******************************************************/
/*! exports provided: RentalComponent, DialogComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RentalComponent", function() { return RentalComponent; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DialogComponent", function() { return DialogComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _app_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../app.service */ "./src/app/app.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _qrcode_scanner_qrcode_scanner_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../../+qrcode-scanner/qrcode-scanner.service */ "./src/app/+qrcode-scanner/qrcode-scanner.service.ts");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var RentalComponent = /** @class */ (function () {
    function RentalComponent(router, _qrScanner, fb, appservice, dialog, _sanitizer, cdr) {
        this.router = router;
        this._qrScanner = _qrScanner;
        this.fb = fb;
        this.appservice = appservice;
        this.dialog = dialog;
        this._sanitizer = _sanitizer;
        this.cdr = cdr;
        this.receivedBy = '';
        this.paymentModeList = [];
        this.postObj = {};
        this.doctorPrescript = '';
        this.aadharId = '';
        this.invoiceSelection = false;
        this.filteredInvoicesList = [];
        this.details = [{ 'binaryData': [], 'names': [] }];
        this.loaderBtn = false;
        this.receiptImg = '';
        this.aadhar = '';
        this.prescription = '';
        this.prescriptionSelection = [{ "id": 4, "name": "Upload Image" }, { "id": 6, "name": "Take Picture" }];
        this.aadharSelection = [{ "id": 3, "name": "Upload Image" }, { "id": 5, "name": "Take Picture" }];
        this.imageId = '';
        this.invoiceForm = this.fb.group({
            invoice: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]],
            channelId: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]],
            payId: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]],
            noofProducts: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]],
            // aadhar: [null],
            // pancard: [null],
            // payment: [null, [Validators.required]],
            receivedBy: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]],
            comments: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]],
        });
        // this.invoiceId = this.invoiceForm.controls['INVOICE']
    }
    RentalComponent.prototype.ngOnInit = function () {
        var _this = this;
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
        this.appservice.getAllPaymentModes().subscribe(function (data) {
            if (data.error == null) {
                _this.paymentModeList = data.data;
            }
            else {
                _this.openDialog(data.error.message);
            }
        });
        this.appservice.getAllChannels().subscribe(function (data) {
            if (data.error == null) {
                _this.channelList = data.data;
            }
            else {
                _this.openDialog(data.error.message);
            }
        });
        var receivedBy = this.invoiceForm.get('receivedBy');
        this.invoiceForm.get('payId').valueChanges.subscribe(function (value) {
            if (value == 1) {
                receivedBy.setValidators(_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].compose([_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]));
            }
            else {
                receivedBy.clearValidators();
                receivedBy.updateValueAndValidity();
            }
        });
        this.getIdChange();
    };
    RentalComponent.prototype.change = function () {
        var comments = this.invoiceForm.get('comments');
        if (this.invoiceForm.get('payId').value != 8) {
            comments.setValidators(_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].compose([_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]));
        }
        else {
            comments.clearValidators();
            comments.updateValueAndValidity();
        }
    };
    RentalComponent.prototype.changePayModeOptions = function () {
        if (this.channelId != 'Custom') {
            this.paymentModeList = [];
            this.paymentModeList.push({ "id": 8, "types": "COD" }, { "id": 10, "types": "ONLINE" });
        }
        else {
            this.paymentModeList = [];
            this.paymentModeList.push({ "id": 1, "types": "CASH" }, { "id": 2, "types": "CREDIT_CARD" }, { "id": 3, "types": "NET_BANKING" }, { "id": 4, "types": "DEBIT_CARD" }, { "id": 5, "types": "WALLET" }, { "id": 6, "types": "CHEQUE" }, { "id": 7, "types": "INDIA_MART" }, { "id": 8, "types": "COD" }, { "id": 9, "types": "UPI" }, { "id": 10, "types": "ONLINE" });
        }
    };
    RentalComponent.prototype.selectInvoice = function (obj) {
        this.invoiceSelection = true;
        this.invoiceId = obj;
        localStorage.setItem('invoiceId', this.invoiceId);
        this.filteredInvoicesList = [];
    };
    RentalComponent.prototype.getIdChange = function () {
        var _this = this;
        this.appservice.getAllRentalDispatchedInvoices().subscribe(function (data) {
            if (data.error == null) {
                _this.dispatchedInvoices = data.data;
            }
        });
    };
    RentalComponent.prototype.getInvoicesbasedonSearch = function () {
        var _this = this;
        this.invoiceSelection = false;
        this.filteredInvoicesList = [];
        if (this.invoiceId) {
            this.dispatchedInvoices.find(function (data) {
                if (data != null) {
                    if ((data.toLowerCase()).includes(_this.invoiceId.toLowerCase())) {
                        _this.filteredInvoicesList.push(data);
                    }
                }
            });
        }
    };
    RentalComponent.prototype.takePicture = function (field) {
        var _this = this;
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
            }
            else if (this.selectedImage == 1 || this.selectedImage == 3 || this.selectedImage == 4) {
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
            navigator.camera.getPicture(function (base64string) {
                _this.loaderBtn = false;
                if (_this.selectedImage == 1 || _this.selectedImage == 2) {
                    _this.receiptImg = base64string;
                }
                if (_this.selectedImage == 3 || _this.selectedImage == 5) {
                    _this.aadhar = base64string;
                }
                if (_this.selectedImage == 4 || _this.selectedImage == 6) {
                    _this.prescription = base64string;
                }
                _this.cdr.detectChanges();
            }, function (error) {
                _this.cdr.detectChanges();
                _this.loaderBtn = false;
                if (_this.selectedImage == 3 || _this.selectedImage == 5) {
                    _this.aadharId = '';
                    _this.cdr.detectChanges();
                }
                if (_this.selectedImage == 4 || _this.selectedImage == 6) {
                    _this.doctorPrescript = '';
                    _this.cdr.detectChanges();
                }
                if (_this.aadhar) {
                    _this.aadhar = '';
                }
                if (_this.prescription) {
                    _this.prescription = '';
                }
                alert("Unable to obtain picture: " + error);
                _this.cdr.detectChanges();
            }, options);
        }
    };
    RentalComponent.prototype.deleteReceiptImg = function (id) {
        if (id == 1) {
            this.receiptImg = '';
            this.cdr.detectChanges();
        }
        if (id == 2) {
            this.aadhar = '';
            this.cdr.detectChanges();
            this.aadharId = '';
            this.cdr.reattach();
            this.cdr.detectChanges();
        }
        if (id == 3) {
            this.prescription = '';
            this.cdr.detectChanges();
            this.doctorPrescript = '';
            this.cdr.reattach();
            this.cdr.detectChanges();
        }
    };
    RentalComponent.prototype.submit = function () {
        if (this.channelId == 'Custom' && this.payId != 8) {
            if (this.receiptImg != '' && this.invoiceForm.valid) {
                this.navigatetoInvoice();
            }
            else {
                this.formValidate = true;
            }
        }
        else {
            this.navigatetoInvoice();
        }
    };
    RentalComponent.prototype.navigatetoInvoice = function () {
        var _this = this;
        if (this.invoiceForm.valid) {
            localStorage.setItem('invoiceId', this.invoiceId);
            localStorage.setItem('noofProducts', this.noofProducts);
            localStorage.setItem('paymentMode', this.payId);
            localStorage.setItem('channel', '7');
            localStorage.setItem('receivedBy', this.receivedBy);
            var postObj = {
                comments: this.comments,
                invoiceNumber: this.invoiceId,
                proofs: []
            };
            if (this.receiptImg) {
                postObj.proofs.push(this.receiptImg);
            }
            if (this.aadhar) {
                postObj.proofs.push(this.aadhar);
            }
            if (this.prescription) {
                postObj.proofs.push(this.prescription);
            }
            if ((this.channelId == 'Custom' && this.payId != 8) || this.prescription || this.aadhar) {
                this.appservice.getImageId(postObj).subscribe(function (data1) {
                    if (data1.error == null) {
                        _this.imageId = data1.data.id;
                        localStorage.setItem('imageId', _this.imageId);
                        _this.postObj.comments = '';
                        _this.postObj.invoiceNumber = '';
                        _this.postObj.proofs = [];
                        if (_this.imageId != "") {
                            _this.dispatch();
                        }
                        else {
                            _this.openDialog("Fail to get Image Id");
                        }
                    }
                    else {
                        _this.postObj.comments = '';
                        _this.postObj.invoiceNumber = '';
                        _this.postObj.proofs = [];
                        _this.openDialog(data1.error.message);
                    }
                });
            }
            else {
                localStorage.setItem('imageId', '');
                this.dispatch();
            }
        }
        else {
            this.formValidate = true;
        }
    };
    RentalComponent.prototype.dispatch = function () {
        var _this = this;
        this.appservice.invoiceCheck(this.invoiceId).subscribe(function (data) {
            if (data.error == null) {
                _this.appservice.getDetailsByDispatchInvoice(_this.invoiceId).subscribe(function (data) {
                    if (data.error == null) {
                        localStorage.setItem('dispatchType', '3');
                        // localStorage.setItem('QRCode', '0175-0000085')
                        // this.router.navigate(['/dispatch/invoice-view'])
                        _this._qrScanner.promiseScan().then(function (result) {
                            _this.resultQrcode = result;
                            if (!_this.resultQrcode.cancelled) {
                                localStorage.setItem('QRCode', _this.resultQrcode.text);
                                _this.router.navigate(['/dispatch/invoice-view']);
                            }
                        });
                    }
                    else {
                        _this.openDialog(data.error.message);
                    }
                });
            }
            else {
                _this.openDialog(data.error.message);
            }
        });
    };
    RentalComponent.prototype.navigatetoHomePage = function () {
        this.router.navigate(['./dispatch']);
    };
    RentalComponent.prototype.openDialog = function (msgText) {
        var dialogRef = this.dialog.open(DialogComponent, { disableClose: true });
        localStorage.setItem('msg', msgText);
        dialogRef.afterClosed().subscribe(function (result) {
            if (result) {
            }
        });
    };
    RentalComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'rental-invoice',
            template: __webpack_require__(/*! ./rental.component.html */ "./src/app/+dispatch/+rental/rental.component.html"),
            styles: ["\n    .footer-text{\n        font-weight:bold\n    }\n    .mat-focused .placeholder {    \n        color: #AAAAAA;\n        }\n      .placeholder {\n         color:#AAAAAA;\n        }\n        .ui-overlay-c {\n            background-color: rgba(0, 0, 0, 0.5);\n            position: fixed;\n            width: 100%;\n            height: 100%;\n            top: 0;\n            left: 0;\n            text-align: center;\n        }\n        .loading {\n          position: absolute;\n          top: 50%;\n          left: 50%;\n        }\n        .loading-bar {\n         \n          display: inline-block;\n          width: 4px;\n          height: 18px;\n          border-radius: 4px;\n          animation: loading 1s ease-in-out infinite;\n        }\n        .loading-bar:nth-child(1) {\n          background-color: #3498db;\n          animation-delay: 0;\n        }\n        .loading-bar:nth-child(2) {\n          background-color: #c0392b;\n          animation-delay: 0.09s;\n        }\n        .loading-bar:nth-child(3) {\n          background-color: #f1c40f;\n          animation-delay: .18s;\n        }\n        .loading-bar:nth-child(4) {\n          background-color: #27ae60;\n          animation-delay: .27s;\n        }\n        \n        @keyframes loading {\n          0% {\n            transform: scale(1);\n          }\n          20% {\n            transform: scale(1, 2.2);\n          }\n          40% {\n            transform: scale(1);\n          }\n        }\n        md-checkbox .md-icon {\n    background: red;\n}\nmd-checkbox.md-default-theme.md-checked .md-icon {\n    background: orange;\n}\n\n \n    "]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"],
            _qrcode_scanner_qrcode_scanner_service__WEBPACK_IMPORTED_MODULE_5__["QRCodeScanner"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"],
            _app_service__WEBPACK_IMPORTED_MODULE_2__["AppService"],
            _angular_material__WEBPACK_IMPORTED_MODULE_4__["MatDialog"],
            _angular_platform_browser__WEBPACK_IMPORTED_MODULE_6__["DomSanitizer"],
            _angular_core__WEBPACK_IMPORTED_MODULE_0__["ChangeDetectorRef"]])
    ], RentalComponent);
    return RentalComponent;
}());

var DialogComponent = /** @class */ (function () {
    function DialogComponent() {
    }
    DialogComponent.prototype.ngOnInit = function () {
        this.msgText = localStorage.getItem('msg');
    };
    DialogComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'dialog-content-example-dialog',
            template: "<img class=\"mat-typography img1\" src=\"assets/img/logom.png\" alt=\"MedicalBulkBuy\" width=\"90%\"   >\n    <mat-dialog-content class=\"mat-typography\" style=\"border-bottom:1px solid #ddd;border-top:1px solid #ddd; text-align:center\">\n     \n    <h5 style=\"padding: 18px;margin: 0px;font-size: 14px;font-family: sans-serif;\">{{msgText}}</h5>\n    </mat-dialog-content>\n   \n    <mat-dialog-actions align=\"center\" >\n    <button mat-stroked-button [mat-dialog-close]=\"true\" cdkFocusInitial > \n    <mat-icon>done</mat-icon>\n    Ok\n</button>\n    </mat-dialog-actions>\n  ",
        })
    ], DialogComponent);
    return DialogComponent;
}());



/***/ }),

/***/ "./src/app/+dispatch/+rental/rental.module.ts":
/*!****************************************************!*\
  !*** ./src/app/+dispatch/+rental/rental.module.ts ***!
  \****************************************************/
/*! exports provided: RentalModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RentalModule", function() { return RentalModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _rental_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./rental.component */ "./src/app/+dispatch/+rental/rental.component.ts");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};






var routes = [
    {
        path: '',
        component: _rental_component__WEBPACK_IMPORTED_MODULE_4__["RentalComponent"]
    }
];
var RentalModule = /** @class */ (function () {
    function RentalModule() {
    }
    RentalModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatIconModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatButtonModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatRippleModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatFormFieldModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatInputModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatTooltipModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatSelectModule"],
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatDialogModule"],
                [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forChild(routes)],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["ReactiveFormsModule"].withConfig({ warnOnNgModelWithFormControl: 'never' })
            ],
            declarations: [_rental_component__WEBPACK_IMPORTED_MODULE_4__["RentalComponent"], _rental_component__WEBPACK_IMPORTED_MODULE_4__["DialogComponent"]],
            providers: [],
            entryComponents: [_rental_component__WEBPACK_IMPORTED_MODULE_4__["DialogComponent"]]
        })
    ], RentalModule);
    return RentalModule;
}());



/***/ })

}]);
//# sourceMappingURL=rental-rental-module.js.map