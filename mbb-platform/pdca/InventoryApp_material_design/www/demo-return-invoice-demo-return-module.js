(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["demo-return-invoice-demo-return-module"],{

/***/ "./src/app/+product-management/demo-return-invoice/demo-return.component.html":
/*!************************************************************************************!*\
  !*** ./src/app/+product-management/demo-return-invoice/demo-return.component.html ***!
  \************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"main-content\">\n    <div class=\"container-fluid\">\n        <div class=\"row\">\n            <div class=\"col-md-8\">\n                <div class=\"card\">\n                    <div class=\"card-header card-header-info\">\n                        <h4 class=\"card-title\" style=\"text-align:center\">Return Invoice</h4>\n                    </div>\n                    <div class=\"card-body\">\n                        <form [formGroup]=\"invoiceForm\">\n\n                            <div class=\"row\">\n                                <div class=\"col-sm-10 col-10\">\n                                    <!-- <mat-form-field class=\"example-full-width\">\n                                        <input matInput name=\"invoiceId\" formControlName=\"invoice\"\n                                            [(ngModel)]=\"invoiceId\">\n                                        <mat-placeholder class=\"placeholder\">Invoice Id *</mat-placeholder>\n                                    </mat-form-field> -->\n                                    <mat-form-field class=\"example-full-width\">\n                                        <input matInput name=\"invoiceId\" formControlName=\"invoice\" [(ngModel)]=\"invoiceId\"\n                                            (keyup)=\"getInvoiceSbasedonSearch()\" autocomplete=\"off\">\n                                        <mat-placeholder class=\"placeholder\">Invoice Id *</mat-placeholder>\n                                    </mat-form-field>\n\n                                    <div class=\"height-skus\">\n                                        <div *ngFor=\"let key of filteredInvoicesList\" (click)=\"selectInvoice(key)\"\n                                            style=\"border-bottom:1px solid #AAAAAA;padding: 3px\">\n                                            {{key}} </div>\n                                    </div>\n                                    <span *ngIf=\"invoiceForm.controls['invoice'].hasError('required') &&formValidate \"\n                                        class=\"text-danger\">Please\n                                        enter Invoice Id</span>\n                                </div>\n                                <div class=\"col-sm-2 col-2\" (click)=\"getInvoiceBybarcode()\" style=\"margin: auto\"><img\n                                        src=\"assets/img/qr-code.png\">\n                                </div>\n                            </div>\n                            <button mat-raised-button type=\"button\" class=\"btn btn-danger pull-center\" (click)=\"navigatetoHomePage()\">Cancel</button>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-success pull-center\" (click)=\"navigatetoInvoice()\">Submit</button>\n                        </form>\n                    </div>\n                </div>\n                <br><br>\n                <div *ngIf=\"array.length>0\">\n                    <div class=\"card\">\n                        <div class=\"card-header card-header-info\">\n                            <h4 class=\"card-title\" style=\"text-align:center\">Return QR Code</h4>\n                        </div>\n                        <div class=\"card-body\">\n                            <div class=\"table-responsive\">\n                                <table class=\"table table-bordered table-striped\">\n                                    <thead>\n                                        <tr>\n                                            <th>QRCode</th>\n                                            <th>Action</th>\n                                        </tr>\n                                    </thead>\n                                    <tbody>\n                                        <tr *ngFor=\"let item of productData\">\n                                            <td>{{item.inventoryItem.barcode}}</td>\n                                            <td>\n                                                <mat-checkbox [(ngModel)]=\"item.condition\" name=\"checkbox\">\n                                                </mat-checkbox>\n                                            </td>\n                                        </tr>\n                                    </tbody>\n                                </table>\n                                <br>\n                            </div>\n                            <button mat-raised-button type=\"submit\" class=\"btn btn-success pull-right\" (click)=\"submit()\">Submit\n                            </button>\n                        </div>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>"

/***/ }),

/***/ "./src/app/+product-management/demo-return-invoice/demo-return.component.ts":
/*!**********************************************************************************!*\
  !*** ./src/app/+product-management/demo-return-invoice/demo-return.component.ts ***!
  \**********************************************************************************/
/*! exports provided: DemoReturnInvoiceComponent, DialogComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DemoReturnInvoiceComponent", function() { return DemoReturnInvoiceComponent; });
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






var DemoReturnInvoiceComponent = /** @class */ (function () {
    function DemoReturnInvoiceComponent(router, _qrScanner, fb, appService, dialog) {
        this.router = router;
        this._qrScanner = _qrScanner;
        this.fb = fb;
        this.appService = appService;
        this.dialog = dialog;
        this.status = false;
        this.array = [];
        this.filteredInvoicesList = [];
        this.invoiceSelection = false;
        this.productArray = [];
        this.invoiceForm = this.fb.group({
            invoice: [null, [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]],
        });
    }
    DemoReturnInvoiceComponent.prototype.getInvoiceBybarcode = function () {
        var _this = this;
        this._qrScanner.promiseScan().then(function (result) {
            _this.resultQrcode = result;
            if (!_this.resultQrcode.cancelled) {
                _this.invoiceId = _this.resultQrcode.text;
                localStorage.setItem('invoiceId', _this.resultQrcode.text);
            }
        });
    };
    DemoReturnInvoiceComponent.prototype.ngOnInit = function () {
        /**
         * Back Button event trigger
         */
        history.pushState(null, null, location.href);
        window.onpopstate = function (event) {
            history.go(1);
        };
        this.getIdChange();
    };
    DemoReturnInvoiceComponent.prototype.getIdChange = function () {
        var _this = this;
        this.appService.getAllDemoDispatchedProducts().subscribe(function (data) {
            if (data.data != null) {
                _this.dispatchedInvoices = data.data;
            }
        });
    };
    DemoReturnInvoiceComponent.prototype.getInvoiceSbasedonSearch = function () {
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
    DemoReturnInvoiceComponent.prototype.selectInvoice = function (obj) {
        this.invoiceSelection = true;
        this.invoiceId = obj;
        this.filteredInvoicesList = [];
    };
    DemoReturnInvoiceComponent.prototype.navigatetoInvoice = function () {
        var _this = this;
        if (this.invoiceForm.valid) {
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
    DemoReturnInvoiceComponent.prototype.navigatetoHomePage = function () {
        this.router.navigate(['./inventory']);
    };
    DemoReturnInvoiceComponent.prototype.openDialog = function (msgText) {
        var dialogRef = this.dialog.open(DialogComponent, { disableClose: true });
        localStorage.setItem('msg', msgText);
        dialogRef.afterClosed().subscribe(function (result) {
        });
    };
    DemoReturnInvoiceComponent.prototype.submit = function () {
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
    DemoReturnInvoiceComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'demo-return-invoice',
            template: __webpack_require__(/*! ./demo-return.component.html */ "./src/app/+product-management/demo-return-invoice/demo-return.component.html"),
            styles: ["\n    .footer-text{\n        font-weight:bold\n    }\n    .mat-focused .placeholder {    \n        color: #AAAAAA;\n        }\n      .placeholder {\n         color:#AAAAAA;\n        }\n    "]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"],
            _qrcode_scanner_qrcode_scanner_service__WEBPACK_IMPORTED_MODULE_5__["QRCodeScanner"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"],
            _app_service__WEBPACK_IMPORTED_MODULE_2__["AppService"],
            _angular_material__WEBPACK_IMPORTED_MODULE_4__["MatDialog"]])
    ], DemoReturnInvoiceComponent);
    return DemoReturnInvoiceComponent;
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

/***/ "./src/app/+product-management/demo-return-invoice/demo-return.module.ts":
/*!*******************************************************************************!*\
  !*** ./src/app/+product-management/demo-return-invoice/demo-return.module.ts ***!
  \*******************************************************************************/
/*! exports provided: DemoReturnInvoiceModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DemoReturnInvoiceModule", function() { return DemoReturnInvoiceModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _demo_return_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./demo-return.component */ "./src/app/+product-management/demo-return-invoice/demo-return.component.ts");
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
        component: _demo_return_component__WEBPACK_IMPORTED_MODULE_4__["DemoReturnInvoiceComponent"]
    }
];
var DemoReturnInvoiceModule = /** @class */ (function () {
    function DemoReturnInvoiceModule() {
    }
    DemoReturnInvoiceModule = __decorate([
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
            declarations: [_demo_return_component__WEBPACK_IMPORTED_MODULE_4__["DemoReturnInvoiceComponent"], _demo_return_component__WEBPACK_IMPORTED_MODULE_4__["DialogComponent"]],
            providers: [],
            entryComponents: [_demo_return_component__WEBPACK_IMPORTED_MODULE_4__["DialogComponent"]]
        })
    ], DemoReturnInvoiceModule);
    return DemoReturnInvoiceModule;
}());



/***/ })

}]);
//# sourceMappingURL=demo-return-invoice-demo-return-module.js.map