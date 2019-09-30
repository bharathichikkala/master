import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { endponitConfig } from '../../environments/endpoint';

@Injectable()
export class RentalService {

    constructor(private readonly http: HttpClient) {

    }

    headers = new HttpHeaders().set('Authorization', JSON.parse(sessionStorage.getItem("Autherization")))
    addRentalDetails(rentalObj) {
        return this.http.post(endponitConfig.RENTALS_API + 'addRentals', rentalObj, { headers: this.headers })
            .map((response: any) => response);
    }
    getRentalDetails(id) {
        return this.http.get(endponitConfig.RENTALS_API + id, { headers: this.headers })
            .map((response: any) => response);
    }
    updateRentalDetails(rentalObj, rentalId) {
        return this.http.put(endponitConfig.RENTALS_API + `updateRental/${rentalId}`, rentalObj, { headers: this.headers })
            .map((response: any) => response);
    }
    getAllServiceTypes() {
        return this.http.get(endponitConfig.RENTALS_API + 'getAllRentalServiceTypes', { headers: this.headers })
            .map((response: any) => response);
    }
    getAllStatuses() {
        return this.http.get(endponitConfig.RENTALS_API + 'getAllStatus', { headers: this.headers })
            .map((response: any) => response);
    }
    getAllStatus() {
        return this.http.get(endponitConfig.RENTALS_API + 'getStatusForRentals', { headers: this.headers })
            .map((response: any) => response);
    }
    addRentalExtension(rentalObj, id) {
        return this.http.post(endponitConfig.RENTALS_API + `addRentalExtension/${id}`, rentalObj, { headers: this.headers })
            .map((response: any) => response);
    }
    viewExtensions(id) {
        return this.http.get(endponitConfig.RENTALS_API + `viewExtensions/${id}`, { headers: this.headers })
            .map((response: any) => response);
    }
    getDetailsByskuCode(sku) {
        return this.http.get(endponitConfig.SERVICE_PRODUCTS_API + `getDetailsBySkuCode/${sku}`, { headers: this.headers })
            .map((response: any) => response)
    }
    getDispatchImage(skuCode) {
        return this.http.get(endponitConfig.DISPATCH_ENDPONT + `getDispatchPaymentDocument/${skuCode}`, { headers: this.headers })
            .map((response: any) => response);
    }
    checkInvoice(id) {
        return this.http.get(endponitConfig.RENTALS_API + `invoiceCheck/${id}`, { headers: this.headers })
            .map((response: any) => response);
    }
    makeOrder(invoiceNumber, rentalId) {
        return this.http.get(endponitConfig.RENTALS_API + `convertToOrder/${invoiceNumber}/${rentalId}`, { headers: this.headers })
            .map((response: any) => response);
    }
    getSKUImage(skuCode) {
        return this.http.get(endponitConfig.INVENTORY_ENDPOINT + `getInventoryImage/${skuCode}`, { headers: this.headers })
            .map((response: any) => response);
    }
    addHistory(history, barcode) {
        return this.http.post(endponitConfig.RENTALS_API + 'addHistory', { history, barcode }, { headers: this.headers })
            .map((response: any) => response);
    }
    getHistory(barcode) {
        return this.http.get(endponitConfig.RENTALS_API + `getAllHistory/${barcode}`, { headers: this.headers })
            .map((response: any) => response);
    }
    getAllProductsNames() {
        return this.http.get(endponitConfig.DEMO_PRODUCTS_API + `getAllProductNames`, { headers: this.headers })
            .map((response: any) => response);
    }
    getSkuCodeByProductName(productName) {
        return this.http.post(endponitConfig.DEMO_PRODUCTS_API + `getSkuCodeByProductName`, { "name": productName }, { headers: this.headers })
            .map((response: any) => response);
    }  
}

