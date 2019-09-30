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

declare var $;
@Component({
    selector: 'view-product',
    templateUrl: './view-product.component.html',
    styles: [``]
})
export class ViewProductComponent {
    public ob: any;
    adminType: any;
    constructor(
        public productService: ProductsService,
        private readonly http: HttpClient,
        public router: Router) {
        this.adminType = sessionStorage.getItem('userRole') === 'SUPERADMIN' ? true : false;
    }
    userType: any;
    warranty: any;
    warrantyInDays: any;
    tenDaysReturnPolicy: any;
    sparePartsTotal: any = 0;
    quotationTotal: any = 0;
    quotationTotalCharges: any = 0;
    paymentStatus: any;
    paymentsDetailsExists = false;
    quotationDetailsExist = false;
    invoiceDetailsExists = false;
    transportType: any;
    showDispatchStatus = false;
    paymentModes: any;
    orderDate: any;
    selfShipmentDetails: any;
    shippingAggregatorDetails: any;
    paymentMode: any;
    showSelfDispatch = false;
    showAggregator = false;
    ngOnInit() {
        this.userType = sessionStorage.getItem('userRole')
        const id = localStorage.getItem("viewId");
        this.productService.getDetailsById(id).subscribe(data => {
            this.ob = data.data
            if (this.ob.servicingproduct.orderDate != null) {
                const date = this.ob.servicingproduct.orderDate.split("-");
                this.orderDate = `${date[2]}-${date[1]}-${date[0]}`
            }
            if (this.ob.quotationDetails != null) {
                this.quotationDetailsExist = true;
            }
            if (this.ob.paymentDetails != null) {
                this.paymentsDetailsExists = true;
            }
            if (this.ob.invoiceDetails != null && this.ob.invoiceDetails.unicommerceReferenceNumber != null) {
                this.invoiceDetailsExists = true;
            }
            if (this.ob.shipmentDetails.shippingAggregator == null && this.ob.shipmentDetails.selfShipment == null) {
                this.showDispatchStatus = false;
            }
            else if (this.ob.shipmentDetails.shippingAggregator == null && this.ob.shipmentDetails.selfShipment != null) {
                this.showDispatchStatus = true;
                this.showSelfDispatch = true;
                this.showAggregator = false;
                this.selfShipmentDetails = this.ob.shipmentDetails.selfShipment;
                this.transportType = "Self Shipment"
            }
            else if (this.ob.shipmentDetails.shippingAggregator != null) {
                this.showDispatchStatus = true;
                this.showSelfDispatch = false;
                this.showAggregator = true;
                this.shippingAggregatorDetails = this.ob.shipmentDetails.shippingAggregator;
                this.transportType = "Shipping Aggregator"
            }
            if (this.ob.servicingproduct.warranty) {
                this.warranty = "Yes"
            }
            else {
                this.warranty = "No"
            }
            if (this.ob.servicingproduct.warrantyInDays == null) {
                this.warrantyInDays = 0;
            }
            else {
                this.warrantyInDays = this.ob.servicingproduct.warrantyInDays
            }
            if (this.ob.servicingproduct.tenDaysReturnPolicy) {
                this.tenDaysReturnPolicy = "Yes"
            }
            else {
                this.tenDaysReturnPolicy = "No"
            }
            if (this.ob.quotationDetails != null) {
                this.quotationTotal = this.ob.quotationDetails.serviceCharges + this.ob.quotationDetails.courierCharges + this.ob.quotationDetails.otherCharges +
                    this.ob.quotationDetails.igst + this.ob.quotationDetails.cgst + this.ob.quotationDetails.sgst + this.ob.quotationDetails.utgst
            }
            if (this.ob.spareParts) {
                for (let i = 0; i < this.ob.spareParts.length; i++) {
                    this.sparePartsTotal += this.ob.spareParts[i].price
                }
            }
            this.quotationTotalCharges = this.quotationTotal + this.sparePartsTotal
            if (this.ob.paymentDetails != null) {
                if (this.ob.paymentDetails.paymentStatus) {
                    this.paymentStatus = "Completed"
                }
                else if (this.ob.paymentDetails.paymentStatus == null) {
                    this.paymentStatus = "-"
                }
                else {
                    this.paymentStatus = "Pending"
                }
            }
        })

    }
}

