(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["otp-otp-module"],{

/***/ "./src/app/+auth/+otp/otp-routing.module.ts":
/*!**************************************************!*\
  !*** ./src/app/+auth/+otp/otp-routing.module.ts ***!
  \**************************************************/
/*! exports provided: OtpRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OtpRoutingModule", function() { return OtpRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _otp_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./otp.component */ "./src/app/+auth/+otp/otp.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var routes = [{
        path: '',
        component: _otp_component__WEBPACK_IMPORTED_MODULE_2__["OtpComponent"]
    }];
var OtpRoutingModule = /** @class */ (function () {
    function OtpRoutingModule() {
    }
    OtpRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]],
            providers: []
        })
    ], OtpRoutingModule);
    return OtpRoutingModule;
}());



/***/ }),

/***/ "./src/app/+auth/+otp/otp.component.html":
/*!***********************************************!*\
  !*** ./src/app/+auth/+otp/otp.component.html ***!
  \***********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!--<header id=\"header\" class=\"animated fadeInDown\">\n  <div id=\"logo-group\">\n    <span id=\"logo\">\n  \n         </span>\n  </div>\n</header>-->\n<!-- <br><br> -->\n<!-- <div id=\"main\" role=\"main\" class=\"animated fadeInDown\"> -->\n  <!-- MAIN CONTENT -->\n  <!-- <div id=\"content\" class=\"container\">\n    <div class=\"row\">\n      <div class=\"col-md-6 col-md-offset-3 \" style=\"margin-left:27%\">\n        <div class=\"loginui well no-padding\">\n          <form [formGroup]=\"complexForm\" method=\"post\" *ngIf=\"userRegisterModel\" class=\"smart-form client-form\">\n            <header class=\"header-tag\" style=\"background-color:white\">\n              <img src=\"../../../assets/img/logom.png\" alt=\"MedicalBulkBuy\">\n              <div class=\"app-version\">Version 9.0</div>\n            </header>\n            <fieldset>\n              <section *ngIf=\"errorMessage\" class=\"icon-color-bad text-center\"> {{errorMessage}} </section>\n              <section *ngIf=\"forgotPasswordMessage\" class=\"text-success text-center\">{{forgotPasswordMessage}}</section>\n              <section class=\"otp-section\">\n                <label class=\"label\" for=\"otp\" [ngClass]=\"{'has-error':! this.otp.valid &&  this.otp}\">OTP</label>\n\n                <div class=\"form-group form-control  input\">\n                  <label class=\"input\">\n                    <i class=\"icon-append fa fa-user\"></i>\n                    <input type=\"text\" name=\"otp\" [formControl]=\"this.otp\" [(ngModel)]=\"userRegisterModel.otp\"\n                      placeholder=\"OTP\" />\n\n                    <span *ngIf=\" this.otp.hasError('required') &&submitted  \" class=\" icon-color-bad \">Please enter\n                      otp</span>\n\n                    <span *ngIf=\" this.otp.hasError('pattern') \" class=\" icon-color-bad \">Please enter only 6 numeric\n                      characters for OTP</span>\n\n                  </label>\n                </div>\n              </section>\n\n\n              <section class=\"otp-section\">\n                <label class=\"label\" for=\"password\" [ngClass]=\"{'has-error':! this.password.valid &&  this.password}\">Password</label>\n\n                <div class=\"form-group form-control  input\">\n                  <label class=\"input\">\n                    <i class=\"icon-append fa fa-lock\"></i>\n                    <input type=\"password\" name=\"password\" [formControl]=\"this.password\" [(ngModel)]=\"userRegisterModel.password\"\n                      placeholder=\"Password\" />\n                    <span *ngIf=\" this.password.hasError('required')  && submitted\" class=\" icon-color-bad \">Please\n                      enter password</span>\n                  </label>\n                </div>\n              </section>\n\n\n              <section class=\"otp-section\">\n                <label class=\"label\" for=\"retypePassword\" [ngClass]=\"{'has-error':! this.retypePassword.valid &&  this.retypePassword}\">Confirm\n                  Password</label>\n\n                <div class=\"form-group form-control  input\">\n                  <label class=\"input\">\n                    <i class=\"icon-append fa fa-lock\"></i>\n                    <input type=\"password\" name=\"retypePassword\" [formControl]=\"this.retypePassword\" [(ngModel)]=\"userRegisterModel.reTypepassword\"\n                      placeholder=\"Confirm Password\" />\n                    <span class=\" icon-color-bad \" *ngIf=\" (userRegisterModel.reTypepassword) && (userRegisterModel.password != userRegisterModel.reTypepassword) \">Password\n                      miss match</span>\n                    <span *ngIf=\" this.retypePassword.hasError('required')  && submitted\" class=\" icon-color-bad \">Please\n                      Enter Re-Type Password</span>\n                  </label>\n                </div>\n              </section>\n\n            </fieldset>\n            <br>\n\n\n            <div class=\"row show-grid\">\n              <div class=\"col-xs-1 \"> </div>\n              <div class=\"col-xs-4 \">\n                <span class=\"hidden-mobile hiddex-xs\"><b>Already registered ? </b> </span>\n                <a routerLink=\"/loginUrl\">\n                  <u> <b> Sign In </b></u>\n                </a>\n              </div>\n            </div>\n            <br>\n\n            <footer style=\"background-color:white\">\n              <button (click)=\"register(userRegisterModel)\" [disabled]=\"!complexForm.valid\" class=\"btn btn-primary\">\n                Reset\n                <i class=\"fa fa-cog fa-spin\" *ngIf=\"loading\"></i>\n                <span *ngIf=\"loading\">loading...</span>\n              </button>\n            </footer>\n          </form>\n        </div>\n      </div>\n    </div>\n  </div>\n\n</div>\n -->\n\n<div class=\"wrapper wrapper-full-page\">\n  <div class=\"page-header login-page header-filter\" filter-color=\"black\" style=\"background-image: url('assets/img/background.jpg'); background-size: cover; background-position: top center;height: 100%;\">\n    <div class=\"container\">\n      <br><br><br><br>\n      <div class=\"row\">\n        <div class=\"col-lg-4 col-md-6 col-sm-6 ml-auto mr-auto\">\n          <!--<form [formGroup]=\"complexForm\" method=\"post\" *ngIf=\"userLogin\" class=\"smart-form client-form\">-->\n          <form [formGroup]=\"complexForm\" *ngIf=\"userRegisterModel\" class=\"form ng-untouched ng-pristine ng-valid\"\n            method=\"\" novalidate=\"\">\n            <div class=\"card card-login\">\n              <div class=\"card-header text-center\">\n                <img src=\"assets/img/logom.png\" width=\"100%\"><br/>\n                <h3 style=\"color:#2B55A3;font-weight: bold;\">Reset Password</h3>\n              </div>\n              <div class=\"card-body \">\n                <section *ngIf=\"errorMessage\" style=\"color: red\" class=\"icon-color-bad text-center\"> {{errorMessage}} </section>\n                <section *ngIf=\"forgotPasswordMessage\" class=\"text-success text-center\">{{forgotPasswordMessage}}</section>\n                <!-- <section class=\"otp-section\"> -->\n                <!-- <label class=\"label\" for=\"otp\" [ngClass]=\"{'has-error':! this.otp.valid &&  this.otp}\">OTP</label> -->\n                <span class=\"bmd-form-group\">\n                  <div class=\"input-group\">\n                    <!-- <i class=\"icon-append fa fa-user\"></i> -->\n                    <input class=\"form-control\" placeholder=\"OTP\" name=\"otp\" type=\"number\" [formControl]=\"this.otp\"\n                      [(ngModel)]=\"userRegisterModel.otp\" >\n                      </div>\n                    <span *ngIf=\" this.otp.hasError('required') &&submitted  \" style=\"color:red\" class=\" icon-color-bad \">Please enter\n                      otp</span>\n\n                    <span *ngIf=\" this.otp.hasError('pattern') \" style=\"color:red\" class=\" icon-color-bad \">Please enter 6 digits OTP</span>\n                  \n                </span>\n                <!-- </section> -->\n                <br>\n                <span class=\"bmd-form-group\">\n                  <div class=\"input-group\">\n                    <input class=\"form-control\" placeholder=\"Password\" name=\"password\" type=\"password\" [formControl]=\"this.password\"\n                      [(ngModel)]=\"userRegisterModel.password\">\n                  </div>\n                  <span *ngIf=\" this.password.hasError('required')  && submitted\" style=\"color:red\" class=\" icon-color-bad \">Please\n                    enter password</span>\n                </span>\n                <br>\n                <span class=\"bmd-form-group\">\n                  <div class=\"input-group\">\n                    <input class=\"form-control\" placeholder=\"Confirm Password\" type=\"password\" name=\"retypePassword\"\n                      [formControl]=\"this.retypePassword\" [(ngModel)]=\"userRegisterModel.reTypepassword\">\n                  </div>\n                  <span class=\" icon-color-bad \" style=\"color:red\" *ngIf=\" (userRegisterModel.reTypepassword) && (userRegisterModel.password != userRegisterModel.reTypepassword) \">Password\n                    miss match</span>\n                  <span *ngIf=\" this.retypePassword.hasError('required')  && submitted\" style=\"color:red\" class=\" icon-color-bad \">Please\n                    Enter Re-Type Password</span>\n                </span>\n\n                <br>\n                <span class=\"bmd-form-group\">\n                  <div class=\"input-group\">\n                    <span class=\"hidden-mobile hiddex-xs\"><b>Already registered?</b> </span>\n                    <a routerLink=\"/loginUrl\">\n                      <u><b> Sign In</b></u>\n                    </a>\n                  </div>\n                </span>\n              </div>\n              <!-- <br> -->\n              <!-- <footer style=\"background-color:white\"> -->\n              <div class=\"card-footer\">\n                <button style=\"margin-left: 100px;\" (click)=\"register(userRegisterModel)\" [disabled]=\"!complexForm.valid\"\n                  class=\"btn btn-primary\"><strong *ngIf=\"!loading\">\n                  Reset</strong>\n                  <i class=\"fa fa-cog fa-spin\" *ngIf=\"loading\"></i>\n                  <span *ngIf=\"loading\"><strong>loading...</strong></span>\n                </button>\n              </div>\n              <!-- </footer> -->\n              <!-- <span style=\"color:red;text-align:center\">{{loginErrorMessage}}</span> -->\n              <!-- <div class=\"card-footer justify-content-center\"> -->\n              <!-- <button mat-raised-button color=\"primary\" (click)=\"doLogin()\">Login</button> -->\n              <!-- </div> -->\n            </div>\n          </form>\n        </div>\n      </div>\n    </div>\n    <footer class=\"footer \">\n      <div class=\"container\">\n\n\n      </div>\n    </footer>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/+auth/+otp/otp.component.ts":
/*!*********************************************!*\
  !*** ./src/app/+auth/+otp/otp.component.ts ***!
  \*********************************************/
/*! exports provided: OtpComponent, UserOTPModel */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OtpComponent", function() { return OtpComponent; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserOTPModel", function() { return UserOTPModel; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _auth_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../auth.service */ "./src/app/auth.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var OtpComponent = /** @class */ (function () {
    function OtpComponent(
    //private alertNotificationService: AlertNotificationService,
    route, router, authService, fb) {
        this.route = route;
        this.router = router;
        this.authService = authService;
        this.fb = fb;
        this.userRegisterModel = new UserOTPModel('', '');
        this.loading = false;
        this.passwordResetMessage = "Password reset successfully. Please Login";
        this.complexForm = fb.group({
            otp: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].compose([_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].pattern('^[0-9]{6,6}$')])],
            password: [null, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required],
            retypePassword: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required]
        }, { validator: this.MatchPassword });
        this.otp = this.complexForm.controls['otp'];
        this.password = this.complexForm.controls['password'];
        this.retypePassword = this.complexForm.controls['retypePassword'];
    }
    OtpComponent.prototype.MatchPassword = function (control) {
        var password = control.get('password').value; // to get value in input tag
        var confirmPassword = control.get('retypePassword').value;
        // to get value in input tag
        if (confirmPassword != null) {
            if (password !== confirmPassword) {
                control.get('retypePassword').setErrors({ MatchPassword: true });
            }
            else {
                return null;
            }
        }
    };
    OtpComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.subscribe(function (params) {
            if (params.data !== undefined) {
                if (params.data === 'Success') {
                    _this.forgotPasswordMessage = 'OTP has been sent to the registered Mobile Number/Email';
                }
            }
        });
    };
    OtpComponent.prototype.register = function (event) {
        var _this = this;
        this.submitted = true;
        this.loading = true;
        this.authService.userOTP(event.otp, event.password).subscribe(function (data) {
            if (data.data != null) {
                _this.errorMessage = '';
                sessionStorage.setItem('userRole', data.data.roles[0].name);
                sessionStorage.setItem('Authentication', btoa(data.data.email + ":" + event.password));
                //  this.router.navigate(['/login'])
                _this.loading = false;
                localStorage.setItem("reset", _this.passwordResetMessage);
                _this.router.navigate(['/loginUrl']);
            }
            else {
                _this.loading = false;
                _this.errorMessage = data.error.message;
            }
        }, function (error) {
            _this.loading = false;
            _this.errorMessage = 'Enter  OTP and Password';
        });
    };
    OtpComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-otp',
            template: __webpack_require__(/*! ./otp.component.html */ "./src/app/+auth/+otp/otp.component.html"),
            providers: [_auth_service__WEBPACK_IMPORTED_MODULE_2__["AuthService"]],
            styles: []
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_1__["ActivatedRoute"], _angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"], _auth_service__WEBPACK_IMPORTED_MODULE_2__["AuthService"], _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"]])
    ], OtpComponent);
    return OtpComponent;
}());

var UserOTPModel = /** @class */ (function () {
    function UserOTPModel(password, otp) {
        this.password = password;
        this.otp = otp;
    }
    return UserOTPModel;
}());



/***/ }),

/***/ "./src/app/+auth/+otp/otp.module.ts":
/*!******************************************!*\
  !*** ./src/app/+auth/+otp/otp.module.ts ***!
  \******************************************/
/*! exports provided: OtpModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OtpModule", function() { return OtpModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _otp_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./otp-routing.module */ "./src/app/+auth/+otp/otp-routing.module.ts");
/* harmony import */ var _otp_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./otp.component */ "./src/app/+auth/+otp/otp.component.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};





var OtpModule = /** @class */ (function () {
    function OtpModule() {
    }
    OtpModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _otp_routing_module__WEBPACK_IMPORTED_MODULE_2__["OtpRoutingModule"], _angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormsModule"], _angular_forms__WEBPACK_IMPORTED_MODULE_4__["ReactiveFormsModule"].withConfig({ warnOnNgModelWithFormControl: 'never' })
            ],
            declarations: [_otp_component__WEBPACK_IMPORTED_MODULE_3__["OtpComponent"]]
        })
    ], OtpModule);
    return OtpModule;
}());



/***/ })

}]);
//# sourceMappingURL=otp-otp-module.js.map