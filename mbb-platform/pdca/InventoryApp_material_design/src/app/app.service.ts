import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { Response, Headers } from '@angular/http'
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { endponitConfig } from '../environments/endpoints';
import 'rxjs/add/operator/map'
import { BehaviorSubject } from 'rxjs/BehaviorSubject'

@Injectable()
export class AppService {

    private readonly _headers = new HttpHeaders().set('Authorization', JSON.parse(localStorage.getItem("Autherization")));

    private readonly headers: HttpHeaders;
    constructor(public http: HttpClient) {

    }

    array = []
    private readonly messageSource: BehaviorSubject<any> = new BehaviorSubject(this.array);
    currentMessage = this.messageSource.asObservable();

    setMessage(value) {
        this.messageSource.next(value);

    }
    getMessage() {
        return this.messageSource.value
    }
    add(value) {
        this.array.push(value);
    }

    getAllDispatchDetails(startDate, endDate) {
        const url = `${endponitConfig.DISPATCH_ENDPONT}`;
        return this.http.get(url + `getDispatchDetails${startDate}/${endDate}`, { headers: this._headers }).map(response =>
            response
        );
    }

    getInventoryDetails() {
        return this.http.get(`${endponitConfig.INVENTORY_ENDPOINT + 'getAllInventories'}`, { headers: this._headers }).map(response =>
            response);
    }

    getAllQrcodes() {
        return this.http.get(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getAllBarcodes/'}`, { headers: this._headers })
            .map(response =>
                response
            );
    }
    getProductByQrId(id) {
        return this.http.get(`${endponitConfig.INVENTORY_ENDPOINT + 'getDetailsByBarcodeId/'}${id}`, { headers: this._headers })
            .map(response => response);
    }


    getAllSkuCodes() {
        return this.http.get(`${endponitConfig.MBB_INVENTORY + 'inventory/getAllSkusBasedOnSorting'}`, { headers: this._headers })
            .map(response => response);
    }



    GenerateQRcodes(sku, count) {
        const url = `${endponitConfig.BARCODES_ENDPOINT}`;

        return this.http.get(url + `zixing/${sku}/${count}`, { headers: this._headers })
            .map(response => response);
    }


    retrieveQrCodes(skuCode) {
        return this.http.get(`${endponitConfig.BARCODES_ENDPOINT + 'reprint/'}${skuCode}`, { headers: this._headers })
            .map(response => response);
    }

    printQrcode(qrCodesdata) {
        return this.http.post(`${endponitConfig.BARCODES_ENDPOINT + 'qrcodeList'}`, qrCodesdata, { headers: this._headers })
            .map(response => response);
    }


    getProductConditionList() {
        return this.http.get(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getAllConditions'}`, { headers: this._headers })
            .map((response) => response);
    }

    getAllProductStatusTypes() {
        return this.http.get(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getAllInventoryStatuses'}`, { headers: this._headers })
            .map((response) => response);
    }

    getProductDetailsfromDispatch(qrCode) {
        return this.http.get(`${endponitConfig.DISPATCH_ENDPONT + 'getDetailsByBarcode/'}${qrCode}`, { headers: this._headers })
            .map((response) => response);
    }

    getInventoryItemDetails(qrCode) {
        return this.http.get(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getInventoryItemDetails/'}${qrCode}`, { headers: this._headers })
            .map((response) => response);
    }
    getProductDetailsfromInventory(qrCode) {
        return this.http.get(`${endponitConfig.INVENTORY_ENDPOINT + 'getDetailsByBarcodeId/'}${qrCode}`, { headers: this._headers })
            .map((response) => response);
    }

    getReturnProdDetails(qrCode) {
        return this.http.get(`${endponitConfig.RETURN_ENDPOINT + 'getItemDetails/'}${qrCode}`, { headers: this._headers })
            .map((response) => response);
    }
    getCheckList(id) {
        return this.http.get(`${endponitConfig.CHECKLIST_ENDPONT + 'getChecklist/'}${id}`, { headers: this._headers })
            .map((response) => response);
    }
    updateChecklist(object) {
        return this.http.put(`${endponitConfig.CHECKLIST_ENDPONT + 'updateChecklist'}`, object, { headers: this._headers })
            .map((response) => response);
    }

    addReturnItemToInventory(prodObj, reason) {
        return this.http.put(`${endponitConfig.RETURN_ENDPOINT + 'addReturnItem/'}${reason}`, prodObj, { headers: this._headers })
            .map((response) => response);
    }


    getStatusByInventoryCondition(id) {
        return this.http.get(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getStatusBasedONCondition/'}${id}`, { headers: this._headers })
            .map((response) => response);
    }
    getFacilities() {
        return this.http.get(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getAllFacilities'}`, { headers: this._headers })
            .map((response) => response);
    }

    addProduct(object) {
        return this.http.post(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'addNewInventoryItem'}`, object, { headers: this._headers })
            .map((response) => response);
    }

    addingSku(obj) {
        return this.http.post(`${endponitConfig.INVENTORY_ENDPOINT + 'addInventory'}`, obj, { headers: this._headers })
            .map((response) => response);
    }

    getDetailsBySkuId(skuValue) {
        return this.http.get(`${endponitConfig.INVENTORY_ENDPOINT + 'getInventoryBySkuCode/'}${skuValue}`, { headers: this._headers })
            .map((response) => response);
    }

    updateSku(obj) {
        return this.http.put(`${endponitConfig.INVENTORY_ENDPOINT + 'updateSku/'}${obj.id}`, obj, { headers: this._headers })
            .map((response) => response);
    }

    checkingBarcode(id, facId) {
        return this.http.get(`${endponitConfig.DISPATCH_ENDPONT + 'getDetailsByBarcode/'}${id}/${facId}`, { headers: this._headers })
            .map((response) => response);
    }
    // getCheckListByBarcode(barcode) {
    //     return this.http.get(`${endponitConfig.DISPATCH_ENDPONT + 'getChecklistData/'}${barcode}`, { headers: this._headers })
    //         .map((response) => response);
    // }

    getCheckListBySKu(sku) {
        return this.http.get(`${endponitConfig.DISPATCH_ENDPONT + 'getAccessoriesBySkuCode/'}${sku}`, { headers: this._headers })
            .map((response) => response);
    }

    addDispatch(obj) {
        return this.http.post(`${endponitConfig.DISPATCH_ENDPONT + 'addDispatch'}`, obj, { headers: this._headers })
            .map((response) => response);
    }

    addCheckList(obj) {
        return this.http.post(`${endponitConfig.CHECKLIST_ENDPONT + 'addNewChecklist'}`, obj, { headers: this._headers })
            .map((response) => response);
    }
    updateProduct(object) {
        return this.http.put(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'updateInventoryItem/'}${object.id}`, object, { headers: this._headers })
            .map((response) => response);
    }


    getAllQrcodes_prod() {
        return this.http.get(`${endponitConfig.INVENTORY_ENDPOINT + 'getAllBarcodes/'}`, { headers: this._headers })
            .map(response =>
                response
            );
    }

    getProductDetailsfromInventory_prod(qrCode) {
        return this.http.get(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getItemDetailsByBarcode/'}${qrCode}`, { headers: this._headers })
            .map((response) => response);
    }
    addProduct_prod(object) {
        return this.http.post(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'addNewInventoryItem'}`, object, { headers: this._headers })
            .map((response) => response);
    }
    getProductByQrId_prod(id) {

        return this.http.get(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getDetailsByBarcode/'}${id}`, { headers: this._headers })
            .map(response => response);
    }
    updateProduct_prod(object) {
        return this.http.put(`${endponitConfig.INVENTORY_ITEM_ENDPOINT + 'updateInventoryItem/'}${object.id}`, object, { headers: this._headers })
            .map((response) => response);
    }

    getAllInvoices() {
        return this.http.get(`${endponitConfig.ORDER_DETAILS + 'getAllInvoiceNumbers/'}`, { headers: this._headers })
            .map((response) => response);
    }

    dataFromUnicommerce(qrCode) {
        return this.http.get(`${endponitConfig.DISPATCH_ENDPONT + 'getUnicommercePendingData/'}${qrCode}`, { headers: this._headers })
            .map((response) => response);
    }
    addToDispatch(dispatchObj) {
        return this.http.put(`${endponitConfig.DISPATCH_ENDPONT + 'updateStatusToDispatch/'}${dispatchObj.id}`, dispatchObj, { headers: this._headers })
            .map((response) => response);
    }
    getDetailsByInvoice(object) {
        return this.http.get(`${endponitConfig.RETURN_ENDPOINT + 'getDetailsByInvoice/'}${object}`, { headers: this._headers })
            .map((response) => response);
    }
    checkInvoice(invoice) {
        return this.http.get(`${endponitConfig.RENTAL_INVOICES + 'getInvoicesForRentalsReturnsDropDown/'}${invoice}`, { headers: this._headers })
            .map((response) => response);
    }
    getDetailsByDispatchInvoice(object) {
        return this.http.get(`${endponitConfig.DISPATCH_ENDPONT + 'getDetailsByInvoice/'}${object}`, { headers: this._headers })
            .map((response) => response);
    }

    getAllPovendors() {
        return this.http.get(`${endponitConfig.PO_VENDOR + 'getAllPovendors'}`, { headers: this._headers })
            .map((response) => response);
    }

    getAllPovendorbyStatus(status) {
        return this.http.get(`${endponitConfig.PO_VENDOR + 'getBasedOnStatus/'}${status}`, { headers: this._headers })
            .map((response) => response);
    }

    //    localhost:2020/mbb/poVendor/getAllPoVendors


    getSKUSBasedonPO(po) {
        return this.http.get(`${endponitConfig.PO_VENDOR_ITEM + 'getSkuCodesByPONumber/'}${po}`, { headers: this._headers })
            .map((response) => response);
    }

    getQRBasedOnPo(po) {
        return this.http.get(`${endponitConfig.BARCODES_ENDPOINT}${po}`, { headers: this._headers })
            .map((response) => response);
    }

    getQrcbasedOnSKUPO(po) {
        const url = `${endponitConfig.BARCODES_ENDPOINT}`;

        return this.http.get(url + `reprint/${po}`, { headers: this._headers })
            .map((response) => response);
    }

    getQrCode(qrCode) {
        return this.http.get(`${endponitConfig.BARCODES_ENDPOINT + 'qrcode/'}${qrCode}`, { headers: this._headers })
            .map((response) => response);
    }

    getAllPackageDetails() {
        return this.http.get(endponitConfig.PORT_ADDRESS + 'transferInventory/getAllTransferInventory', { headers: this._headers })
            .map((response: Response) => response);
    }
    getAllCompletePackes() {
        return this.http.get(endponitConfig.PORT_ADDRESS + 'transferInventory/getPakCompleted', { headers: this._headers })
            .map((response: Response) => response);
    }

    CheckingQRWithPackage(qrCode, packageId, facId) {
        return this.http.get(endponitConfig.PACKAGE_DETAILS + qrCode + '/' + packageId + '/' + facId, { headers: this._headers })
            .map((response: Response) => response);
    }

    removeItemFromPackge(qrcode, packageId, facId) {
        return this.http.get(endponitConfig.INVENTOTY_TRANSFER + 'getScanForRemove/' + packageId + '/' + qrcode + '/' + localStorage.getItem('userName') + '/' + facId, { headers: this._headers })
            .map((response: Response) => response);
    }

    getPackageSKUDetails(packageId) {
        return this.http.get(endponitConfig.INVENTOTY_TRANSFER + 'getAllPackageDetailsByTransferInventoryId/' + packageId, { headers: this._headers })
            .map((response: Response) => response);
    }

    qrCodeMoving(qrCode, packageId) {
        return this.http.put(endponitConfig.PACKAGE_DETAILS + 'updateQrcode/' + qrCode + '/' + packageId + '/' + localStorage.getItem('userName'), '', { headers: this._headers })
            .map((response: Response) => response);
    }
    getAllDispatchedInvoices() {
        return this.http.get(endponitConfig.DISPATCH_ENDPONT + 'getAllDispatchedInvoices', { headers: this._headers })
            .map((response: Response) => response);
    }
    getAllPaymentModes() {
        return this.http.get(endponitConfig.DISPATCH_ENDPONT + 'getAllPaymentModes', { headers: this._headers })
            .map((response: Response) => response);
    }
    getAllChannels() {
        return this.http.get(endponitConfig.MBB_INVENTORY + 'returnDetails/getAllChannels', { headers: this._headers })
            .map((response: Response) => response);
    }
    getImageId(obj) {
        return this.http.post(`${endponitConfig.DISPATCH_ENDPONT + 'addDispatchPaymentDocument'}`, obj, { headers: this._headers })
            .map((response) => response);
    }
    getAllRentalDispatchedInvoices() {
        return this.http.get(endponitConfig.RENTAL_ENDPONT + 'getRentalsForDispatch', { headers: this._headers })
            .map((response: Response) => response);
    }
    getAllDispatchedInvoicesInRental() {
        return this.http.get(endponitConfig.RENTAL_INVOICES + 'getInvoicesForRentals', { headers: this._headers })
            .map((response: Response) => response);
    }
    barcodeCheckForRental(invoice, barcodes) {
        return this.http.put(endponitConfig.DISPATCH_ENDPONT + 'barcodeCheckForRentals/' + invoice, barcodes, { headers: this._headers })
            .map((response: Response) => response);
    }
    addDemoDispatch(obj) {
        return this.http.post(`${endponitConfig.DISPATCH_ENDPONT + 'addDispatch'}`, obj, { headers: this._headers })
            .map((response: any) => response);
    }

    getDemoProductById(id) {
        return this.http.get(endponitConfig.DEMO_DISPATCH + 'getDemoProductByDemoId/' + `${id}`, { headers: this._headers })
            .map((response: any) => response);
    }

    getAllDemobarCodes(id, obj) {
        return this.http.post(endponitConfig.DEMO_DISPATCH + 'getAllDemoBarCodes/' + `${id}`, obj, { headers: this._headers })
            .map((response: any) => response)
    }
    getAllDemoDispatchedProducts() {
        return this.http.get(endponitConfig.DEMO_DISPATCH + 'getAllDispatchedProducts', { headers: this._headers })
            .map((response: any) => response)
    }
    invoiceCheck(invoiceId) {
        return this.http.get(endponitConfig.RENTAL_ENDPONT + 'invoiceCheckForDropDown/' + invoiceId, { headers: this._headers })
            .map((response: any) => response)
    }
    getAllDemoPaymentModes() {
        return this.http.get(endponitConfig.DEMO_DISPATCH + 'getAllPaymentModes', { headers: this._headers })
            .map((response: Response) => response);
    }
    getAllStatusesRentalReasons() {
        return this.http.get(endponitConfig.RENTAL_ENDPONT + 'getStatusForRentalReturns', { headers: this._headers })
            .map((response: Response) => response);
    }
    addCommentsForReturn(obj, id) {
        return this.http.post(endponitConfig.DEMO_DISPATCH + 'addCommentsForDemoRejection/' + `${id}`, obj, { headers: this._headers })
            .map((response: any) => response);
    }

    getAllDemoProductsToBeDispatched() {
        return this.http.get(endponitConfig.DEMO_DISPATCH + 'getAllProdutcsToBeDispatched', { headers: this._headers })
            .map((response: any) => response);
    }

    checkingProductBarcode(id, facId, rentId) {
        return this.http.get(`${endponitConfig.DISPATCH_ENDPONT + 'getDetailsByBarcode/'}${id}/${facId}/${rentId}`, { headers: this._headers })
            .map((response) => response);
    }
    getDemoReturnStatuses() {
        return this.http.get(endponitConfig.DEMO_DISPATCH + 'getStatusForDemoReturns', { headers: this._headers })
            .map((response: any) => response);
    }
    getReturnProductInformation(id) {
        return this.http.get(endponitConfig.DEMO_DISPATCH + 'getProductReturnDropdown/' + `${id}`, { headers: this._headers })
            .map((response: any) => response)
    }
}

