(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["facilities-facilities-module"],{

/***/ "./src/app/facilities/facilities.component.html":
/*!******************************************************!*\
  !*** ./src/app/facilities/facilities.component.html ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"main-content\">\n    <div class=\"container-fluid\">\n        <div class=\"row\">\n            <div class=\"col-md-8\">\n                <div class=\"card\">\n                    <div class=\"card-header card-header-info\">\n                        <h4 class=\"card-title\" style=\"text-align:center\">Change Facility</h4>\n                    </div>\n                    <div class=\"card-body\">\n                        <form>\n                            <div class=\"row\">\n                                <div class=\"col-sm-12 col-12\">\n                                    <mat-form-field>\n                                        <label>Select Facility</label>\n                                        <mat-select matInput [(ngModel)]=\"value\" [ngModelOptions]=\"{standalone: true}\"\n                                            required>\n                                            <mat-option *ngFor=\"let a of facilities\" [value]=\"a.id\">{{a.facility}}\n                                            </mat-option>\n                                        </mat-select>\n                                    </mat-form-field>\n                                </div>\n                            </div>\n                            <span style=\"color:green;text-align:center\">{{facilitySuccess}}</span><br>\n                            <button type=\"submit\" (click)=\"home()\" class=\"btn btn-primary\">\n                                Change\n                            </button>\n                        </form>\n                    </div>\n                </div>\n            </div>\n        </div>\n    </div>\n</div>"

/***/ }),

/***/ "./src/app/facilities/facilities.component.ts":
/*!****************************************************!*\
  !*** ./src/app/facilities/facilities.component.ts ***!
  \****************************************************/
/*! exports provided: FacilityComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FacilityComponent", function() { return FacilityComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var FacilityComponent = /** @class */ (function () {
    function FacilityComponent(router) {
        this.router = router;
        this.facilities = [];
        this.submitted = false;
    }
    FacilityComponent.prototype.ngOnInit = function () {
        this.value = JSON.parse(localStorage.getItem('facility')).id;
        this.userType = localStorage.getItem('userRole');
        var a = JSON.parse(localStorage.getItem('facilities'));
        this.facilities = a;
        this.msgText = localStorage.getItem('msg');
        this.form = new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormGroup"]({
            facility: new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormControl"](null)
        });
    };
    FacilityComponent.prototype.home = function () {
        var _this = this;
        this.facilities = JSON.parse(localStorage.getItem('facilities'));
        var ob;
        for (var i = 0; i < this.facilities.length; i++) {
            if (this.facilities[i].id == this.value) {
                ob = this.facilities[i];
            }
        }
        localStorage.setItem('facility', JSON.stringify(ob));
        if (this.userType == 'DISPATCHER') {
            this.facilitySuccess = 'Facility Changed Successfully';
            setTimeout(function () {
                _this.facilitySuccess = '';
                _this.router.navigate(['/dispatch/home']);
            }, 2000);
        }
        else {
            this.facilitySuccess = 'Facility Changed Successfully';
            setTimeout(function () {
                _this.facilitySuccess = '';
                _this.router.navigate(['/inventory/view']);
            }, 2000);
        }
    };
    FacilityComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'dialog-content-example-dialog',
            template: __webpack_require__(/*! ./facilities.component.html */ "./src/app/facilities/facilities.component.html")
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"]])
    ], FacilityComponent);
    return FacilityComponent;
}());



/***/ }),

/***/ "./src/app/facilities/facilities.module.ts":
/*!*************************************************!*\
  !*** ./src/app/facilities/facilities.module.ts ***!
  \*************************************************/
/*! exports provided: FacilityModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FacilityModule", function() { return FacilityModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _facilities_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./facilities.component */ "./src/app/facilities/facilities.component.ts");
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/icon */ "./node_modules/@angular/material/esm5/icon.es5.js");
/* harmony import */ var _angular_material_card__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/card */ "./node_modules/@angular/material/esm5/card.es5.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




// import { LoginComponent, DialogComponent } from './login.component';




var routes = [
    {
        path: '',
        component: _facilities_component__WEBPACK_IMPORTED_MODULE_4__["FacilityComponent"]
    }
];
var FacilityModule = /** @class */ (function () {
    function FacilityModule() {
    }
    FacilityModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_material__WEBPACK_IMPORTED_MODULE_7__["MatButtonModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_7__["MatRippleModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_7__["MatFormFieldModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_7__["MatInputModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_7__["MatTooltipModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_7__["MatDialogModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_7__["MatSelectModule"],
                _angular_material_card__WEBPACK_IMPORTED_MODULE_6__["MatCardModule"],
                _angular_material_icon__WEBPACK_IMPORTED_MODULE_5__["MatIconModule"],
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forChild(routes)],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["ReactiveFormsModule"].withConfig({ warnOnNgModelWithFormControl: 'never' }),
            ],
            declarations: [_facilities_component__WEBPACK_IMPORTED_MODULE_4__["FacilityComponent"]],
            providers: [],
            entryComponents: [_facilities_component__WEBPACK_IMPORTED_MODULE_4__["FacilityComponent"]]
        })
    ], FacilityModule);
    return FacilityModule;
}());



/***/ })

}]);
//# sourceMappingURL=facilities-facilities-module.js.map