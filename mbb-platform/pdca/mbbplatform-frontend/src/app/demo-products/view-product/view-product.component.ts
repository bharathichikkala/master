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
    selector: 'view-product',
    templateUrl: './view-product.component.html',
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
export class ViewDemoProductComponent {
    loading = false;
    adminType: any;
    demoId: any;
    constructor(
        public demoProductsService: DemoProductsService,
        private readonly http: HttpClient,
        public router: Router) {
        this.adminType = sessionStorage.getItem('userRole') == 'SUPERADMIN' ? true : false;
    }
    ob: any;
    ngOnInit() {
        let id = localStorage.getItem("productId");
        this.demoProductsService.getDetailsById(id).subscribe(data => {
            this.demoId = data.data.demoId
            this.ob = data.data
        })
    }
}
