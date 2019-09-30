import { Component, ViewChild } from '@angular/core';
import { Response, Headers } from '@angular/http'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Router } from '@angular/router';
import { endponitConfig } from '../../../environments/endpoint';
import { DemoProductsService } from '../demo-products.service';
import { ModalDirective } from 'ngx-bootstrap';
import { IMyOptions, IMyDateModel, IMyDate } from 'mydatepicker';
import * as moment from 'moment';
import { retry } from 'rxjs/operators';
import { FormGroup, FormControl, Validators, FormArray } from '@angular/forms';

declare var $;
@Component({
    selector: 'demo-to-order',
    templateUrl: './demo-to-order.component.html',
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
export class DemoToOrderComponent {
    loading = false;
    adminType: any;
    submitted: boolean = false;
    uniCommerceForm: FormGroup;
    demoId: any;
    constructor(
        public demoProductsService: DemoProductsService,
        private readonly http: HttpClient,
        public router: Router) {
        this.adminType = sessionStorage.getItem('userRole') == 'SUPERADMIN' ? true : false;
    }
    ob: any;
    ngOnInit() {
        // let id = localStorage.getItem("productId");
        // this.demoProductsService.getDetailsById(id).subscribe(data => {
        //     this.demoId = data.data.demoId
        //     this.ob = data.data
        // })
        this.uniCommerceForm = new FormGroup({
            'unicommerceReferenceNumber': new FormControl(null, Validators.required)
        })
    }
    gotoHomePage() {
        this.router.navigate(['/demo-products'])
    }
    successMessage: any;
    errorMessage: any;
    convertToOrder() {
        if (this.uniCommerceForm.valid) {
            this.demoProductsService.getUnicommerceNumber(this.uniCommerceForm.get('unicommerceReferenceNumber').value).subscribe(data1 => {
                if (data1.error == null) {
                    this.demoProductsService.convertDemoToOrder(this.uniCommerceForm.value, localStorage.getItem("productDemoId")).subscribe(data => {
                        if (data.error == null) {
                            this.successMessage = "Product converted to order successfully";
                            setTimeout(() => {
                                this.successMessage = "";
                                this.router.navigate(['/demo-products']);
                            }, 500)
                        }
                        else {
                            console.log(data);
                            this.errorMessage = data.error.message;
                            setTimeout(() => {
                                this.errorMessage = "";
                                // this.router.navigate(['/demo-products']);
                            }, 2000)
                        }
                    })
                }
                else {
                    console.log(data1)
                    this.errorMessage = data1.error.message;
                    setTimeout(() => {
                        this.errorMessage = "";
                        // this.router.navigate(['/demo-products']);
                    }, 2000)
                }
            })
        }
        else {
            this.submitted = true;
        }
    }
}
