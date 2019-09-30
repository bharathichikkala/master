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
import { FormGroup, FormControl, Validators } from '@angular/forms';

declare var $;
@Component({
    selector: 'dispatch-product',
    templateUrl: './dispatch-product.component.html',
    styles: [`
    input[type=number]::-webkit-inner-spin-button, 
    input[type=number]::-webkit-outer-spin-button { 
    -webkit-appearance: none; 
    margin: 0; 
}
    `]
})
export class DispatchProductComponent {
    public ob: any;
    transportationTypes: any;
    selfTransportForm: FormGroup;
    showSelfTransport = true;
    showShipingAggregator = false;
    shippingAggregatorForm: FormGroup;
    transportaionForm: FormGroup;
    successMessage: any;
    errorMessage: any;
    selfTransportFormSubmitted = false;
    adminType: any;
    transportationTypeSubmitted = false;
    constructor(
        public productService: ProductsService,
        private readonly http: HttpClient,
        public router: Router) {
        this.adminType = sessionStorage.getItem('userRole') === 'SUPERADMIN' ? true : false;
    }
    ngOnInit() {
        this.transportaionForm = new FormGroup({
            'transportationType': new FormControl(null, Validators.required)
        });
        this.selfTransportForm = new FormGroup({
            'driverName': new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(64)])),
            'vehicleNumber': new FormControl(null),
            'transferInventoryId': new FormControl(null),
            'driverNumber': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('[0-9]{10,10}')])),
            'driverAlternateNumber': new FormControl(null, Validators.compose([Validators.required, Validators.pattern('[0-9]{10,10}')])),
            'email': new FormControl(null, Validators.compose([
                Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')])),
            'comments': new FormControl(null)
        }, DispatchProductComponent.facilityCompare);
        this.shippingAggregatorForm = new FormGroup({
            'shippingAggregator': new FormControl(null, Validators.required),
            'courierName': new FormControl(null, Validators.required),
            'trackingNumber': new FormControl(null, Validators.required),
            'comments': new FormControl(null)
        });
        this.productService.getAllTransportations().subscribe(data => {
            this.transportationTypes = data.data
        })
    }
    static facilityCompare(formGroup) {
        const returnObj: any = {};
        if (formGroup.controls.driverNumber.value === formGroup.controls.driverAlternateNumber.value) {
            returnObj.equal = true
            return returnObj
        }
        return '';
    }
    homeScreen: any = '/servicing-products';
    gotoHomePage() {
        this.router.navigate([this.homeScreen])
    }
    transportType(e) {
        this.selfTransportFormSubmitted = false;
        this.shippingAggregatorFormSubmitted = false;
        if (e.target.value == "Shipping Aggregator") {
            this.showSelfTransport = false;
            this.showShipingAggregator = true;
            this.shippingAggregatorForm.reset()
        }
        else {
            this.showShipingAggregator = false;
            this.showSelfTransport = true;
            this.selfTransportForm.reset()
        }
    }
    addSelfShipment() {
        if (this.transportaionForm.valid) {
            const id = localStorage.getItem("dispatchId");
            if (this.selfTransportForm.valid) {
                const object = {
                    "driverName": this.selfTransportForm.get('driverName').value,
                    "vehicleNumber": null,
                    "transferInventoryId": null,
                    "comments": this.selfTransportForm.get('comments').value,
                    "driverAlternateNumber": this.selfTransportForm.get('driverAlternateNumber').value,
                    "driverNumber": this.selfTransportForm.get('driverNumber').value,
                    "email": this.selfTransportForm.get('email').value,
                    "servicingProductId": {
                        "id": id
                    }
                }
                this.productService.addSelfShipment(object, id).subscribe(data => {
                    if (data.error == null) {
                        this.successMessage = "Servicing done for the product & dispatched successfully"
                        setTimeout(() => {
                            this.successMessage = '';
                            this.router.navigate([this.homeScreen])
                        }, 3000)
                    }
                    else {
                        this.errorMessage = data.error.message;
                        setTimeout(() => {
                            this.errorMessage = '';
                        }, 3000)
                    }
                })
            }
            else {
                this.selfTransportFormSubmitted = true;
            }
        }
        else {
            this.transportationTypeSubmitted = true;
        }
    }
    shippingAggregatorFormSubmitted = false;
    addShippingAggregator() {
        if (this.transportaionForm.valid) {
            const id = localStorage.getItem("dispatchId");
            if (this.shippingAggregatorForm.valid) {
                const object = {
                    "shippingAggregator": this.shippingAggregatorForm.get('shippingAggregator').value,
                    "courierName": this.shippingAggregatorForm.get('courierName').value,
                    "trackingNumber": this.shippingAggregatorForm.get('trackingNumber').value,
                    "comments": this.shippingAggregatorForm.get('comments').value,
                    "servicingProductId": {
                        "id": id
                    },
                    "transferInventoryId": null
                };
                this.productService.addShippingAggregator(object, id).subscribe(data => {
                    if (data.error == null) {
                        this.successMessage = "Servicing done for the product & dispatched successfully"
                        setTimeout(() => {
                            this.successMessage = '';
                            this.router.navigate([this.homeScreen])
                        }, 3000)
                    }
                    else {
                        this.errorMessage = data.error.message;
                        setTimeout(() => {
                            this.errorMessage = '';
                        }, 3000)
                    }
                })
            }
            else {
                this.shippingAggregatorFormSubmitted = true;
            }
        } else {
            this.transportationTypeSubmitted = true
        }
    }
}

