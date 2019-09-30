import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { endponitConfig } from '../../environments/endpoint';

@Injectable()
export class DemoProductsService {

    constructor(private http: HttpClient) {

    }

    headers = new HttpHeaders().set('Authorization', JSON.parse(sessionStorage.getItem("Autherization")))
    getDetailsByskuCode(sku) {
        return this.http.get(endponitConfig.SERVICE_PRODUCTS_API + 'getDetailsBySkuCode/' + `${sku}`, { headers: this.headers })
            .map((response: any) => response)
    }
    addDemoProduct(obj) {
        return this.http.post(endponitConfig.DEMO_PRODUCTS_API + 'addDemoProducts', obj, { headers: this.headers })
            .map((response: any) => response)
    }
    getDetailsById(id) {
        return this.http.get(endponitConfig.DEMO_PRODUCTS_API + 'getDemoProductById/' + `${id}`, { headers: this.headers })
            .map((response: any) => response)
    }
    updateDemoProduct(obj, id) {
        return this.http.put(endponitConfig.DEMO_PRODUCTS_API + 'updateDemoProducts/' + `${id}`, obj, { headers: this.headers })
            .map((response: any) => response)
    }
    convertDemoToOrder(obj, id) {
        return this.http.post(endponitConfig.DEMO_PRODUCTS_API + 'convertDemoProductToOrder/' + `${id}`, obj, { headers: this.headers })
            .map((response: any) => response)
    }
    getUnicommerceNumber(num) {
        return this.http.get(endponitConfig.DEMO_PRODUCTS_API + 'getByUnicommerceReferenceNumber/' + `${num}`, { headers: this.headers })
            .map((response: any) => response);
    }
    getAllProducts() {
        return this.http.get(endponitConfig.DEMO_PRODUCTS_API + 'getAllProductNames', { headers: this.headers })
            .map((response: any) => response);
    }
    getSkuCode(obj) {
        return this.http.post(endponitConfig.DEMO_PRODUCTS_API + 'getSkuCodeByProductName', obj, { headers: this.headers })
            .map((response: any) => response);

    }
}