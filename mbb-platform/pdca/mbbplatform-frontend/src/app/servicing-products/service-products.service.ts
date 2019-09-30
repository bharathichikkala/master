import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { endponitConfig } from '../../environments/endpoint';

@Injectable()
export class ProductsService {

    constructor(private readonly http: HttpClient) {

    }

    headers = new HttpHeaders().set('Authorization', JSON.parse(sessionStorage.getItem("Autherization")))

    getProductDetails(id) {
        return this.http.get(`${endponitConfig.SERVICE_PRODUCTS_API}getDetailsByorderId/${id}`, { headers: this.headers })
            .map((response: any) => response);
    }
    getAccessoriesBySku(sku) {
        return this.http.get(`${endponitConfig.RETURNS_API}getAccessoriesBySkuCode/${sku}`, { headers: this.headers })
            .map((response: any) => response);
    }
    getAllPaymentModes() {
        return this.http.get(endponitConfig.SERVICE_PRODUCTS_API + 'getAllServicingProductsPaymentModes', { headers: this.headers })
            .map((responce: any) => responce)
    }
    addServicingProduct(serviceObject) {
        return this.http.post(endponitConfig.SERVICE_PRODUCTS_API + 'addservicingproduct', serviceObject, { headers: this.headers })
            .map((response: any) => response)
    }
    addQutationDetails(id, object) {
        return this.http.post(`${endponitConfig.SERVICING_API}addquotation/${id}`, object, { headers: this.headers })
            .map((response: any) => response);
    }
    getDetailsBySku(sku) {
        return this.http.get(`${endponitConfig.SERVICE_PRODUCTS_API}getBysku/${sku}`, { headers: this.headers })
            .map((response: any) => response);
    }
    addSparePartsDetails(id, object) {
        return this.http.post(`${endponitConfig.SPAREPARTS_API}addspareparts/${id}`, object, { headers: this.headers })
            .map((response: any) => response)
    }
    getDetailsByskuCode(sku) {
        return this.http.get(`${endponitConfig.SERVICE_PRODUCTS_API}getDetailsBySkuCode/${sku}`, { headers: this.headers })
            .map((response: any) => response);
    }
    addChecklistItems(id, checklist) {
        return this.http.post(`${endponitConfig.SERVICING_CHECKLIST_API}addproductchecklist/${id}`, checklist, { headers: this.headers })
            .map((response: any) => response);
    }
    updateServicingProduct(id, object) {
        return this.http.put(`${endponitConfig.SERVICE_PRODUCTS_API}update/${id}`, object, { headers: this.headers })
            .map((response: any) => response);
    }
    updateQuotationDetails(id, object) {
        return this.http.put(`${endponitConfig.SERVICING_API}updateQuotationDetails/${id}`, object, { headers: this.headers })
            .map((response: any) => response);
    }
    updateSpareParts(id, object) {
        return this.http.put(`${endponitConfig.SPAREPARTS_API}updateSpareparts/${id}`, object, { headers: this.headers })
            .map((response: any) => response);
    }
    sendQuotation(id) {
        return this.http.get(`${endponitConfig.SERVICE_PRODUCTS_API}pdf/${id}`, { headers: this.headers })
            .map((response: any) => response)
    }
    getServicingStatuses() {
        return this.http.get(endponitConfig.SERVICE_PRODUCTS_API + 'getAllServicingStatues', { headers: this.headers })
            .map((response: any) => response)
    }
    getDetailsById(id) {
        return this.http.get(`${endponitConfig.SERVICE_PRODUCTS_API}getServicingProductById/${id}`, { headers: this.headers })
            .map((response: any) => response)
    }
    addPaymentDetails(id, object) {
        return this.http.post(`${endponitConfig.SERVICING_API}addPaymentDetails/${id}`, object, { headers: this.headers })
            .map((response: any) => response)
    }
    addInvoiceDetails(id, object) {
        return this.http.post(`${endponitConfig.SERVICING_API}addInvoiceDetails/${id}`, object, { headers: this.headers })
            .map((response: any) => response);
    }
    updatePaymentDetails(id, object) {
        return this.http.put(`${endponitConfig.SERVICING_API}updatePaymentDetails/${id}`, object, { headers: this.headers })
            .map((response: any) => response)
    }
    updateInvoiceDetails(id, object) {
        return this.http.put(`${endponitConfig.SERVICING_API}updateInvoiceDetails/${id}`, object, { headers: this.headers })
            .map((response: any) => response)
    }
    getAllTransportations() {
        return this.http.get(endponitConfig.TRANSPORT_DETAILS + 'getAllTransportation', { headers: this.headers })
            .map((response: any) => response)
    }
    addSelfShipment(object, id) {
        return this.http.post(`${endponitConfig.SELF_TRANSPORT}addSelfShipmentForServicingProducts/${id}`, object, { headers: this.headers })
            .map((response: any) => response)
    }
    addShippingAggregator(object, id) {
        return this.http.post(`${endponitConfig.AGGGTR_TRANSPORT}addShippingAggregatorForServicingproducts/${id}`, object, { headers: this.headers })
            .map((response: any) => response)
    }
}

