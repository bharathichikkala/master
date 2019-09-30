(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["return-invoice-return-invoice-module"],{

/***/ "./src/app/+product-management/return-invoice/return-invoice.component.html":
/*!**********************************************************************************!*\
  !*** ./src/app/+product-management/return-invoice/return-invoice.component.html ***!
  \**********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"main-content\">\n    <div class=\"container-fluid\">\n        <div class=\"row\">\n            <div class=\"col-md-8\">\n                <div class=\"card\">\n                    <div class=\"card-header card-header-info\">\n                        <h4 class=\"card-title\" style=\"text-align:center\">Return Management</h4>\n                    </div>\n                    <div class=\"card-body\">\n                        <form [formGroup]=\"returnManagementForm\">\n\n                            <div class=\"row\">\n                                <div class=\"col-sm-12 col-12\">\n                                    <!-- <mat-form-field class=\"example-full-width\">\n                                                <input matInput name=\"invoiceId\" formControlName=\"invoice\"\n                                                    [(ngModel)]=\"invoiceId\">\n                                                <mat-placeholder class=\"placeholder\">Invoice Id *</mat-placeholder>\n                                            </mat-form-field> -->\n                                    <mat-form-field class=\"example-full-width\">\n                                        <!-- <input matInput name=\"invoiceId\" formControlName=\"invoice\" [(ngModel)]=\"invoiceId\"\n                                            (keyup)=\"getInvoiceSbasedonSearch()\" autocomplete=\"off\">\n                                        <mat-placeholder class=\"placeholder\">Invoice Id *</mat-placeholder> -->\n                                        <label><b>Return Type</b></label>\n                                        <mat-select formControlName=\"returnType\" [(ngModel)]=\"typeofReturn\"\n                                            (selectionChange)=\"changeReturn()\" matInput>\n                                            <mat-option value=\"\" disabled>Select</mat-option>\n                                            <mat-option *ngFor=\"let type of Returns\" [value]=\"type.id\">\n                                                {{type.type}}</mat-option>\n                                        </mat-select>\n                                    </mat-form-field>\n                                </div>\n                            </div>\n                        </form>\n                    </div>\n                </div>\n                <div *ngIf='showNormalReturn' class=\"card\">\n                    <div class=\"card-header card-header-info\">\n                        <h4 class=\"card-title\" style=\"text-align:center\">Return Invoice</h4>\n                    </div>\n                    <div class=\"card-body\">\n                        <form [formGroup]=\"invoiceForm\">\n\n                            <div class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <!-- <mat-form-field class=\"example-full-width\">\n                                        <input matInput name=\"invoiceId\" formControlName=\"invoice\"\n                                            [(ngModel)]=\"invoiceId\">\n                                        <mat-placeholder class=\"placeholder\">Invoice Id *</mat-placeholder>\n                                    </mat-form-field> -->\n                                    <mat-form-field class=\"example-full-width\">\n                                        <input matInput name=\"invoiceId\" formControlName=\"invoice\" [(ngModel)]=\"invoiceId\"\n                                            (keyup)=\"getInvoiceSbasedonSearch()\" autocomplete=\"off\">\n                                        <mat-placeholder class=\"placeholder\">Invoice Id *</mat-placeholder>\n                                    </mat-form-field>\n\n                                    <div class=\"height-skus\">\n                                        <div *ngFor=\"let key of filteredInvoicesList\" (click)=\"selectInvoice(key)\"\n                                            style=\"border-bottom:1px solid #AAAAAA;padding: 3px\">\n                                            {{key}} </div>\n                                    </div>\n                                    <span *ngIf=\"invoiceForm.controls['invoice'].hasError('required') &&formValidate \"\n                                        class=\"text-danger\">Please\n                                        enter Invoice Id</span>\n                                </div>\n                                <div class=\"col-sm-2 col-2\" (click)=\"getInvoiceBybarcode()\" style=\"margin: auto\"><img\n                                        src=\"assets/img/qr-code.png\">\n                                </div>\n                            </div>\n                            <button mat-raised-button type=\"button\" class=\"btn btn-danger pull-center\" (click)=\"navigatetoHomePage()\">Cancel</button>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-success pull-center\" (click)=\"navigatetoInvoice()\">Submit</button>\n                        </form>\n                    </div>\n                </div>\n                <div *ngIf='showRentalreturn' class=\"card\">\n                    <div class=\"card-header card-header-info\">\n                        <h4 class=\"card-title\" style=\"text-align:center\">Rental Invoice</h4>\n                    </div>\n                    <div class=\"card-body\">\n                        <form [formGroup]=\"rentalInvoiceForm\">\n\n                            <div class=\"row\">\n                                <div class=\"col-sm-12 col-12\">\n                                    <!-- <mat-form-field class=\"example-full-width\">\n                                        <input matInput name=\"invoiceId\" formControlName=\"invoice\"\n                                            [(ngModel)]=\"invoiceId\">\n                                        <mat-placeholder class=\"placeholder\">Invoice Id *</mat-placeholder>\n                                    </mat-form-field> -->\n                                    <mat-form-field class=\"example-full-width\">\n                                        <input matInput name=\"rentalInvoiceId\" formControlName=\"invoiceId\" [(ngModel)]=\"rentalInvoiceId\"\n                                            (keyup)=\"getInvoiceSbasedonSearchInRental()\" autocomplete=\"off\">\n                                        <mat-placeholder class=\"placeholder\">Invoice Id *</mat-placeholder>\n                                    </mat-form-field>\n\n                                    <div class=\"height-skus\">\n                                        <div *ngFor=\"let key of filteredInvoicesListInRental\" (click)=\"selectInvoiceInRental(key)\"\n                                            style=\"border-bottom:1px solid #AAAAAA;padding: 3px\">\n                                            {{key}} </div>\n                                    </div>\n                                    <span *ngIf=\"rentalInvoiceForm.controls['invoiceId'].hasError('required') &&submitted \"\n                                        class=\"text-danger\">Please\n                                        enter Invoice Id</span>\n                                </div>\n                            </div>\n                            <button mat-raised-button type=\"button\" class=\"btn btn-danger pull-center\" (click)=\"navigatetoHomePage()\">Cancel</button>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-success pull-center\" (click)=\"navigatetoRentalInvoice()\">Submit</button>\n                        </form>\n                    </div>\n                </div>\n                <div *ngIf='showDemoreturn' class=\"card\">\n                    <div class=\"card-header card-header-info\">\n                        <h4 class=\"card-title\" style=\"text-align:center\">Demo Invoice</h4>\n                    </div>\n                    <div class=\"card-body\">\n                        <form [formGroup]=\"demoInvoiceForm\">\n                            <div class=\"row\">\n                                <div class=\"col-sm-12 col-12\">\n\n                                    <mat-form-field class=\"example-full-width\">\n                                        <input matInput name=\"rentalInvoiceId\" formControlName=\"invoice\" [(ngModel)]=\"demoInvoiceId\"\n                                            (keyup)=\"getDemosBasedonSearch()\" autocomplete=\"off\">\n                                        <mat-placeholder class=\"placeholder\">Demo Id *</mat-placeholder>\n                                    </mat-form-field>\n\n                                    <div class=\"height-skus\">\n                                        <div *ngFor=\"let key of filteredDemoInvoicesList\" (click)=\"selectDemoInvoice(key)\"\n                                            style=\"border-bottom:1px solid #AAAAAA;padding: 3px\">\n                                            {{key}} </div>\n                                    </div>\n                                    <span *ngIf=\"demoInvoiceForm.controls['invoice'].hasError('required') &&demoFormValidate \"\n                                        class=\"text-danger\">Please\n                                        enter demo Id</span>\n                                </div>\n\n                            </div>\n                            <button mat-raised-button type=\"button\" class=\"btn btn-danger pull-center\" (click)=\"navigatetoHomePage()\">Cancel</button>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-success pull-center\" (click)=\"navigatetoDemoInvoice()\">Submit</button>\n                        </form>\n                    </div>\n                </div>\n                <br><br>\n                <div *ngIf=\"array.length>0 && showNormalReturn\">\n                    <div class=\"card\">\n                        <div class=\"card-header card-header-info\">\n                            <h4 class=\"card-title\" style=\"text-align:center\">Return QR Code</h4>\n                        </div>\n                        <div class=\"card-body\">\n                            <div class=\"table-responsive\">\n                                <table class=\"table table-bordered table-striped\">\n                                    <thead>\n                                        <tr>\n                                            <th>QRCode</th>\n                                            <th>Action</th>\n                                        </tr>\n                                    </thead>\n                                    <tbody>\n                                        <tr *ngFor=\"let item of productData\">\n                                            <td>{{item.inventoryItem.barcode}}</td>\n                                            <td>\n                                                <mat-checkbox [(ngModel)]=\"item.condition\" name=\"checkbox\">\n                                                </mat-checkbox>\n                                            </td>\n                                        </tr>\n                                    </tbody>\n                                </table>\n                                <br>\n                            </div>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-success pull-right\" (click)=\"submit()\">Submit\n                            </button>\n                        </div>\n                    </div>\n                </div>\n                <div *ngIf=\"rentalArray.length>0 && showRentalreturn\">\n                    <div class=\"card\">\n                        <div class=\"card-header card-header-info\">\n                            <h4 class=\"card-title\" style=\"text-align:center\">Rental QR Code</h4>\n                        </div>\n                        <div class=\"card-body\">\n                            <div class=\"table-responsive\">\n                                <table class=\"table table-bordered table-striped\">\n                                    <thead>\n                                        <tr>\n                                            <th>QRCode</th>\n                                            <th>Action</th>\n                                        </tr>\n                                    </thead>\n                                    <tbody>\n                                        <tr *ngFor=\"let item of rentalProductData\">\n                                            <td>{{item.inventoryItem.barcode}}</td>\n                                            <td>\n                                                <mat-checkbox (click)=\"$event.preventDefault()\" [(ngModel)]=\"item.condition\"\n                                                    name=\"checkbox\">\n                                                </mat-checkbox>\n                                            </td>\n                                        </tr>\n                                    </tbody>\n                                </table>\n                                <br>\n                            </div>\n                            <div class=\"row\">\n                                <div class=\"col-md-6\">\n                                    <form [formGroup]=\"rentalReasonForm\">\n                                        <mat-form-field>\n                                            <label>Add Reasons</label>\n                                            <mat-select matInput [(ngModel)]=\"rentalReason\" formControlName='rentalReason'\n                                                required>\n                                                <mat-option value='' disabled>Select</mat-option>\n                                                <mat-option *ngFor=\"let reason of rentalReasons\" [value]=\"reason.id\">\n                                                    {{reason.status}}</mat-option>\n                                            </mat-select>\n                                        </mat-form-field>\n                                        <span *ngIf=\"rentalReasonForm.controls['rentalReason'].hasError('required') && rentalSub\"\n                                            class=\" text-danger\">Please\n                                            select Add Reasons</span>\n                                    </form>\n                                </div>\n                            </div>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-success pull-right\" (click)=\"submitRental()\">Submit\n                            </button>\n                        </div>\n                    </div>\n                </div>\n                <div *ngIf=\"demoArray.length>0 && showDemoreturn\">\n                    <div class=\"card\">\n                        <div class=\"card-header card-header-info\">\n                            <h4 class=\"card-title\" style=\"text-align:center\">Demo QR Code</h4>\n                        </div>\n                        <div class=\"card-body\">\n                            <div class=\"table-responsive\">\n                                <table class=\"table table-bordered table-striped\">\n                                    <thead>\n                                        <tr>\n                                            <th>QRCode</th>\n                                            <th>Action</th>\n                                        </tr>\n                                    </thead>\n                                    <tbody>\n                                        <tr *ngFor=\"let item of demoProductData\">\n                                            <td>{{item.inventoryItem.barcode}}</td>\n                                            <td>\n                                                <mat-checkbox [(ngModel)]=\"item.condition\" name=\"checkbox\" checked\n                                                    (click)=\"noCarToastr($event, hasCar)\">\n                                                </mat-checkbox>\n                                            </td>\n                                        </tr>\n                                    </tbody>\n                                    <br />\n\n                                </table>\n\n\n                            </div>\n                            <form [formGroup]=\"returnForm\">\n                                <div class=\"row\">\n                                    <div class=\"col-sm-12 col-12\">\n                                        <mat-form-field>\n                                            <label><b>Return Type</b></label>\n                                            <mat-select [(ngModel)]=\"typeOfReturn\" formControlName=\"type\" matInput>\n                                                <mat-option value=\"\" disabled>Select</mat-option>\n                                                <mat-option *ngFor=\"let type of ReturnTypes\" [value]=\"type.id\">\n                                                    {{type.status}}</mat-option>\n\n                                            </mat-select>\n\n                                        </mat-form-field>\n                                        <span *ngIf=\"returnForm.controls['type'].hasError('required') &&returnFormSubmitted \"\n                                            class=\"text-danger\">Please\n                                            select return type</span>\n                                    </div>\n                                </div>\n                                <div *ngIf=\"typeOfReturn==10\" class=\"row\">\n                                    <div class=\"col-sm-12 col-12\">\n                                        <mat-form-field>\n                                            <label><b>Comments</b></label>\n                                            <textarea formControlName=\"comments\" matInput [(ngModel)]=\"comments\">\n                            </textarea>\n                                        </mat-form-field>\n                                    </div>\n                                </div>\n                            </form>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-success pull-right\" (click)=\"submitDemo()\">Submit\n                            </button>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>"

/***/ }),

/***/ "./src/app/+product-management/return-invoice/return-invoice.component.ts":
/*!********************************************************************************!*\
  !*** ./src/app/+product-management/return-invoice/return-invoice.component.ts ***!
  \********************************************************************************/
/*! exports provided: ReturnInvoiceComponent, DialogComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ReturnInvoiceComponent", function() { return ReturnInvoiceComponent; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DialogComponent", function() { return DialogComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _app_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../app.service */ "./src/app/app.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _qrcode_scanner_qrcode_scanner_service__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../../+qrcode-scanner/qrcode-scanner.service */ "./src/app/+qrcode-scanner/qrcode-scanner.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


// import {Router} from '@angular/router';




var ReturnInvoiceComponent = /** @class */ (function () {
    function ReturnInvoiceComponent(router, _qrScanner, fb, appService, dialog) {
        this.router = router;
        this._qrScanner = _qrScanner;
        this.fb = fb;
        this.appService = appService;
        this.dialog = dialog;
        this.status = false;
        this.typeofReturn = "";
        this.showNormalReturn = false;
        this.showRentalreturn = false;
        this.showDemoreturn = false;
        this.Returns = [{ "id": 1, "type": "Normal" }, { "id": 2, "type": "Rental" }, { "id": 3, "type": "Demo" }];
        this.array = [];
        this.rentalArray = [];
        this.demoArray = [];
        this.ReturnTypes = [];
        this.filteredInvoicesList = [];
        this.filteredDemoInvoicesList = [];
        this.invoiceSelection = false;
        this.demoInvoiceSelection = false;
        this.hasCar = false;
        this.filteredInvoicesListInRental = [];
        this.invoiceSelectionInRental = false;
        this.rental = false;
        this.normalInvoice = false;
        this.demo = false;
        this.productArray = [];
        this.returnFormSubmitted = false;
        this.invoiceForm = this.fb.group({
            invoice: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]],
        });
        this.rentalInvoiceForm = this.fb.group({
            invoiceId: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]],
        });
        this.demoInvoiceForm = this.fb.group({
            invoice: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]]
        });
        this.rentalReasonForm = this.fb.group({
            rentalReason: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]]
        });
        this.returnForm = new _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormGroup"]({
            'type': new _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormControl"](null, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required),
            'comments': new _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormControl"](null)
        });
        this.returnManagementForm = new _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormGroup"]({
            'returnType': new _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormControl"](null, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required)
        });
    }
    ReturnInvoiceComponent.prototype.getInvoiceBybarcode = function () {
        var _this = this;
        this._qrScanner.promiseScan().then(function (result) {
            _this.resultQrcode = result;
            if (!_this.resultQrcode.cancelled) {
                _this.invoiceId = _this.resultQrcode.text;
                localStorage.setItem('invoiceId', _this.resultQrcode.text);
            }
        });
    };
    ReturnInvoiceComponent.prototype.ngOnInit = function () {
        var _this = this;
        /**
         * Back Button event trigger
         */
        history.pushState(null, null, location.href);
        window.onpopstate = function (event) {
            history.go(1);
        };
        this.appService.getDemoReturnStatuses().subscribe(function (data) {
            if (data.error == null) {
                _this.ReturnTypes = data.data;
            }
        });
        this.getIdChange();
        this.getIdChangeInRental();
        this.getDemoInvoices();
    };
    ReturnInvoiceComponent.prototype.changeReturn = function () {
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
    };
    ReturnInvoiceComponent.prototype.getAllStatusesRentalReasons = function () {
        var _this = this;
        this.appService.getAllStatusesRentalReasons().subscribe(function (data) {
            if (data.error == null) {
                _this.rentalReasons = data.data;
            }
        });
    };
    ReturnInvoiceComponent.prototype.getIdChange = function () {
        var _this = this;
        this.appService.getAllDispatchedInvoices().subscribe(function (data) {
            if (data.data != null) {
                _this.dispatchedInvoices = data.data;
            }
        });
    };
    ReturnInvoiceComponent.prototype.getDemoInvoices = function () {
        var _this = this;
        // this.appService.getAllDemoDispatchedProducts
        this.appService.getAllDemoDispatchedProducts().subscribe(function (data) {
            if (data.data != null) {
                _this.dispatchedDemoInvoices = data.data;
            }
        });
    };
    ReturnInvoiceComponent.prototype.getInvoiceSbasedonSearch = function () {
        var _this = this;
        this.invoiceSelection = false;
        this.filteredInvoicesList = [];
        if (this.invoiceId) {
            this.dispatchedInvoices.find(function (data) {
                if ((data.toLowerCase()).includes(_this.invoiceId.toLowerCase())) {
                    _this.filteredInvoicesList.push(data);
                }
            });
        }
    };
    ReturnInvoiceComponent.prototype.getDemosBasedonSearch = function () {
        var _this = this;
        this.demoInvoiceSelection = false;
        this.filteredDemoInvoicesList = [];
        if (this.demoInvoiceId) {
            this.dispatchedDemoInvoices.find(function (data) {
                if ((data.toLowerCase()).includes(_this.demoInvoiceId.toLowerCase())) {
                    _this.filteredDemoInvoicesList.push(data);
                }
            });
        }
    };
    ReturnInvoiceComponent.prototype.selectInvoice = function (obj) {
        this.invoiceSelection = true;
        this.invoiceId = obj;
        this.filteredInvoicesList = [];
    };
    ReturnInvoiceComponent.prototype.selectDemoInvoice = function (obj) {
        this.invoiceSelection = true;
        this.demoInvoiceId = obj;
        this.filteredDemoInvoicesList = [];
    };
    ReturnInvoiceComponent.prototype.noCarToastr = function (event) {
        var me = this;
        if (!this.hasCar) {
            event.preventDefault();
        }
    };
    ReturnInvoiceComponent.prototype.getIdChangeInRental = function () {
        var _this = this;
        this.appService.getAllDispatchedInvoicesInRental().subscribe(function (data) {
            if (data.data != null) {
                _this.dispatchedInvoicesInRental = data.data;
            }
        });
    };
    ReturnInvoiceComponent.prototype.getInvoiceSbasedonSearchInRental = function () {
        var _this = this;
        this.invoiceSelectionInRental = false;
        this.filteredInvoicesListInRental = [];
        if (this.rentalInvoiceId) {
            this.dispatchedInvoicesInRental.find(function (data) {
                if ((data.toLowerCase()).includes(_this.rentalInvoiceId.toLowerCase())) {
                    _this.filteredInvoicesListInRental.push(data);
                }
            });
        }
    };
    ReturnInvoiceComponent.prototype.selectInvoiceInRental = function (obj) {
        this.invoiceSelectionInRental = true;
        this.rentalInvoiceId = obj;
        this.filteredInvoicesListInRental = [];
    };
    ReturnInvoiceComponent.prototype.navigatetoInvoice = function () {
        var _this = this;
        if (this.invoiceForm.valid) {
            this.rental = true;
            this.demo = true;
            localStorage.setItem('invoiceId', this.invoiceId);
            this.appService.getDetailsByInvoice(this.invoiceForm.value.invoice).subscribe(function (data) {
                if (data.data != null) {
                    _this.productData = data.data;
                    _this.array = data.data;
                    _this.status = true;
                    for (var _i = 0, _a = _this.productData; _i < _a.length; _i++) {
                        var i = _a[_i];
                        i.condition = false;
                    }
                }
                if (data.data == "") {
                    _this.openDialog(data.error.message);
                    _this.invoiceForm.reset();
                }
            });
        }
        else {
            this.formValidate = true;
        }
    };
    ReturnInvoiceComponent.prototype.navigatetoRentalInvoice = function () {
        var _this = this;
        if (this.rentalInvoiceForm.valid) {
            this.normalInvoice = true;
            this.demo = true;
            localStorage.setItem('invoiceId', this.rentalInvoiceId);
            this.appService.checkInvoice(this.rentalInvoiceForm.value.invoiceId).subscribe(function (data1) {
                if (data1.error == null) {
                    _this.appService.getDetailsByInvoice(_this.rentalInvoiceForm.value.invoiceId).subscribe(function (data) {
                        if (data.data != null) {
                            _this.rentalProductData = data.data;
                            _this.rentalArray = data.data;
                            _this.status = true;
                            for (var _i = 0, _a = _this.rentalProductData; _i < _a.length; _i++) {
                                var i = _a[_i];
                                i.condition = true;
                            }
                            _this.getAllStatusesRentalReasons();
                        }
                        if (data.data == "") {
                            _this.openDialog(data.error.message);
                            _this.rentalInvoiceForm.reset();
                        }
                    });
                }
                else {
                    _this.openDialog(data1.error.message);
                }
            });
        }
        else {
            this.submitted = true;
        }
    };
    ReturnInvoiceComponent.prototype.navigatetoDemoInvoice = function () {
        var _this = this;
        if (this.demoInvoiceForm.valid) {
            this.appService.getReturnProductInformation(this.demoInvoiceForm.get('invoice').value).subscribe(function (data1) {
                if (data1.error == null) {
                    localStorage.setItem('invoiceId', _this.demoInvoiceId);
                    _this.appService.getDetailsByInvoice(_this.demoInvoiceForm.value.invoice).subscribe(function (data) {
                        if (data.data != null) {
                            _this.demoProductData = data.data;
                            _this.demoArray = data.data;
                            _this.status = true;
                            for (var _i = 0, _a = _this.demoProductData; _i < _a.length; _i++) {
                                var i = _a[_i];
                                i.condition = true;
                            }
                        }
                        if (data.data == "") {
                            _this.openDialog(data.error.message);
                            _this.demoInvoiceForm.reset();
                        }
                    });
                }
                else {
                    _this.openDialog(data1.error.message);
                }
            });
        }
        else {
            this.demoFormValidate = true;
        }
    };
    ReturnInvoiceComponent.prototype.navigatetoHomePage = function () {
        this.router.navigate(['./inventory']);
    };
    ReturnInvoiceComponent.prototype.openDialog = function (msgText) {
        var dialogRef = this.dialog.open(DialogComponent, { disableClose: true });
        localStorage.setItem('msg', msgText);
        dialogRef.afterClosed().subscribe(function (result) {
        });
    };
    ReturnInvoiceComponent.prototype.submit = function () {
        var productArray = [];
        for (var i = 0; i < this.productData.length; i++) {
            if (this.productData[i].condition) {
                productArray.push(this.productData[i]);
            }
        }
        if (productArray.length > 0) {
            this.appService.add(productArray);
            this.router.navigate(['/product/return']);
        }
    };
    ReturnInvoiceComponent.prototype.submitRental = function () {
        this.rentalSub = false;
        if (this.rentalReasonForm.valid) {
            localStorage.setItem("type", "rental");
            localStorage.setItem("reason", this.rentalReason);
            var productArray = [];
            for (var i = 0; i < this.rentalProductData.length; i++) {
                if (this.rentalProductData[i].condition) {
                    productArray.push(this.rentalProductData[i]);
                }
            }
            if (productArray.length > 0) {
                this.appService.add(productArray);
                this.router.navigate(['/product/return']);
            }
        }
        else {
            this.rentalSub = true;
        }
    };
    ReturnInvoiceComponent.prototype.submitDemo = function () {
        var productArray = [];
        if (this.returnForm.valid) {
            for (var i = 0; i < this.demoProductData.length; i++) {
                if (this.demoProductData[i].condition) {
                    productArray.push(this.demoProductData[i]);
                }
            }
            if (productArray.length > 0) {
                this.appService.add(productArray);
                localStorage.setItem("reason", this.returnForm.get('type').value);
                localStorage.setItem("comments", this.returnForm.get('comments').value);
                localStorage.setItem("demoId", this.demoInvoiceForm.get('invoice').value);
                this.router.navigate(['/product/return']);
            }
        }
        else {
            this.returnFormSubmitted = true;
        }
    };
    ReturnInvoiceComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-return-invoice',
            template: __webpack_require__(/*! ./return-invoice.component.html */ "./src/app/+product-management/return-invoice/return-invoice.component.html"),
            styles: ["\n    .footer-text{\n        font-weight:bold\n    }\n    .mat-focused .placeholder {\n        color: #AAAAAA;\n        }\n      .placeholder {\n         color:#AAAAAA;\n        }\n    "]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"],
            _qrcode_scanner_qrcode_scanner_service__WEBPACK_IMPORTED_MODULE_5__["QRCodeScanner"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"],
            _app_service__WEBPACK_IMPORTED_MODULE_2__["AppService"],
            _angular_material__WEBPACK_IMPORTED_MODULE_4__["MatDialog"]])
    ], ReturnInvoiceComponent);
    return ReturnInvoiceComponent;
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

/***/ "./src/app/+product-management/return-invoice/return-invoice.module.ts":
/*!*****************************************************************************!*\
  !*** ./src/app/+product-management/return-invoice/return-invoice.module.ts ***!
  \*****************************************************************************/
/*! exports provided: ReturnInvoiceModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ReturnInvoiceModule", function() { return ReturnInvoiceModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _return_invoice_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./return-invoice.component */ "./src/app/+product-management/return-invoice/return-invoice.component.ts");
/* harmony import */ var _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/checkbox */ "./node_modules/@angular/material/esm5/checkbox.es5.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};







var routes = [
    {
        path: '',
        component: _return_invoice_component__WEBPACK_IMPORTED_MODULE_4__["ReturnInvoiceComponent"]
    }
];
var ReturnInvoiceModule = /** @class */ (function () {
    function ReturnInvoiceModule() {
    }
    ReturnInvoiceModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatIconModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatButtonModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatRippleModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatFormFieldModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatInputModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatTooltipModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatSelectModule"],
                _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_5__["MatCheckboxModule"],
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_6__["MatDialogModule"],
                [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forChild(routes)],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["ReactiveFormsModule"].withConfig({ warnOnNgModelWithFormControl: 'never' })
            ],
            declarations: [_return_invoice_component__WEBPACK_IMPORTED_MODULE_4__["ReturnInvoiceComponent"], _return_invoice_component__WEBPACK_IMPORTED_MODULE_4__["DialogComponent"]],
            providers: [],
            entryComponents: [_return_invoice_component__WEBPACK_IMPORTED_MODULE_4__["DialogComponent"]]
        })
    ], ReturnInvoiceModule);
    return ReturnInvoiceModule;
}());



/***/ })

}]);
//# sourceMappingURL=return-invoice-return-invoice-module.js.map