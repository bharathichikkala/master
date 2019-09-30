import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { Observable } from 'rxjs/Observable';
import { ILoad } from '../models/load';
import { endponitConfig } from '../../../environments/endpoints';

/**
 * This is a service class which makes Rest service calls to PALS dash board to get loads details
 */

@Injectable()
export class LoadsService {

    private actionUrl: string;
    private headers: Headers;

    constructor(private http: Http) {
        this.headers = new Headers();
        this.headers.append('Content-Type', 'application/json');
        this.headers.append("Authorization", localStorage.getItem('Authentication'));
    }

    public deleteSkid(skidId) {
        let deleteLoadsURL = endponitConfig.LOAD_API_ENDPOINT + 'deleteSkidDrop/'
        let url = `${deleteLoadsURL}${skidId}`;
        return this.http
            .delete(url, { headers: this.headers })
            .map(response => response.json()).catch(this.handleError);
    }

    getTimeDistance(a, b, c, d) {
        return this.http.get(endponitConfig.LOCATIONS_API_ENDPOINT + 'getDistnaceAndTime/' + a + '/' + b + '/' + c + '/' + d + '/', { headers: this.headers })
            .map(res => { return res.json() });
    }

    getExpensesReportsbyLoadnUmber(loadNum) {
        return this.http.post('http://192.168.3.113:8080/solar-dashboard/api/reports/expensesReport/Expenses_Report', { "RP_loadnum": loadNum }, { headers: this.headers })
            .map(res => { return res });
    }
    getConsolidatedReports(loadNum) {
        return this.http.post('http://192.168.3.113:8080/solar-dashboard/api/reports/tripConsolidatedReport/Trip_Consolidated_Report', { "RP_loadnum": loadNum }, { headers: this.headers })
            .map(res => { return res });
    }

    getTimeandDistance(currentLat, currentLng, destinationlat, destinationlng) {
        return this.http.get(endponitConfig.SOLAR_API_ENDPOINT
            // tslint:disable-next-line:max-line-length
            + 'locations/distance/' + currentLat + '/' + currentLng + '/' + destinationlat + '/' + destinationlng + '/', { headers: this.headers })
            .map(res => { return res.json() });
    }

    getAllDcLocations() {
        return this.http.get(endponitConfig.LOCATIONS_API_ENDPOINT + 'getAllDcLocations', { headers: this.headers })
            .map((response: Response) =>
                response.json()
            ).catch(this.handleError);
    }
    getLoadNumber() {
        return this.http.get(endponitConfig.LOAD_API_ENDPOINT + 'loadsequence', { headers: this.headers })
            .map((response: Response) =>
                response.json().data
            ).catch(this.handleError);
    }


    /**
     * This method get all locations data
     */
    public getAlllocationsInfo() {
        return this.http.get(endponitConfig.LOCATIONS_API_ENDPOINT + 'getAllLocations', { headers: this.headers })
            .map((response: Response) =>
                response.json().data
            ).catch(this.handleError);
    }


    getDocumnentsByLoadandType(loadNumber, type) {
        return this.http.get(endponitConfig.INSEPCTION_ENDPOINT + 'getDamageImage/' + loadNumber + '/' + type, { headers: this.headers })
            .map((response: Response) =>
                response.json()
            ).catch(this.handleError);
    }

    /**
     * This method gets all trucks info
     */
    public getTrucksInfo() {
        return this.http.get(endponitConfig.TRUCK_API_ENDPOINT + 'getTruck', { headers: this.headers })
            .map((response: Response) =>
                response.json().data
            ).catch(this.handleError);
    }

    /**
     * This method gets all vendors info
     */
    public getVendorsInfo() {
        return this.http.get(endponitConfig.VENDORS_API_ENDPOINT + 'getVendors', { headers: this.headers })
            .map((response: Response) =>
                response.json().data
            ).catch(this.handleError);
    }

    /**
     * This method gets Appointments Type
     */

    public getAppointmentTypesInfo() {
        return this.http.get(endponitConfig.LOAD_APPOINTMENT_API_ENDPOINT + 'getLoadAppointmentTypes', { headers: this.headers })
            .map((response: Response) =>
                response.json().data
            ).catch(this.handleError);
    }


    /**
     * This method gets all Loads data
     */
    public getAllLoads(): Observable<ILoad[]> {
        return this.http.get(endponitConfig.LOAD_API_ENDPOINT + 'getAllLoadAppointments', { headers: this.headers }).map((response: Response) => <any>response.json().data).catch(this.handleError);
    }

    /**
     * This method gets Load data based on LoadNUmber
     */
    public getLoadDetailsByLoadNum(loadNum: string) {
        let getLoadDetailsURL = endponitConfig.LOAD_API_ENDPOINT + 'getLoadAppointments/';
        let url = `${getLoadDetailsURL}${loadNum}`;
        return this.http.get(url, { headers: this.headers }).map((response: Response) => response.json().data).catch(this.handleError);
    }

    /**
    * This method adds  a new  Load details
    */
    public addLoad(load: any) {
        let loadAddURL = endponitConfig.LOAD_API_ENDPOINT + 'addLoadAppointment'
        let url = `${loadAddURL}`;
        return this.http
            .post(url, load, { headers: this.headers })
            .toPromise()
            .then(response => response.json())
            .catch(error => {
                this.handleError;
                console.log("error occured in adding new Load details" + error)
            });
    }


    /**
    * This method updates  existing Load details
    */
    public updateLoad(load: any) {
        let loadUpdateURL = endponitConfig.LOAD_API_ENDPOINT + 'updateLoadAppointment'
        let url = `${loadUpdateURL}`;
        return this.http
            .put(url, load, { headers: this.headers })
            .toPromise()
            .then(response => response.json())
            .catch(error => {
                this.handleError;
                console.log("error occured updating load details" + error)
            });
    }
    /**
    * This method  deletes  dealer details based  on dealerCd
    */
    public deleteLoad(load) {
        let deleteLoadsURL = endponitConfig.LOAD_API_ENDPOINT + 'deleteLoadAppointment/'
        let url = `${deleteLoadsURL}${load.loadNumber}`;
        return this.http
            .delete(url, { headers: this.headers })
            .map(response => response.json()).catch(this.handleError);
    }
    /**
     * This methos get all driver names list
     */
    public getDriverNameList() {
        var driverURL = endponitConfig.DRIVER_API_ENDPOINT + 'getDrivers';
        return this.http.get(driverURL, { headers: this.headers }).map((response: Response) => response.json().data).catch(this.handleError);
    }



    /**
    * This method updates  existing Load details
    */
    public updateLoadStatus(loadNumber, loadStatus) {
        let loadUpdateURL = endponitConfig.LOAD_API_ENDPOINT + 'updateLoad/' + loadNumber + '/' + loadStatus;
        let url = `${loadUpdateURL}`;
        return this.http
            .put(url, loadNumber, { headers: this.headers })
            .toPromise()
            .then(response => response.json().data)
            .catch(error => {
                this.handleError;
                console.log("error occured updating load details" + error)
            });
    }

    private handleError(error: any) {
        if (error.status == 404) {
            return Observable.throw(error.json());
        } else {
            localStorage.setItem('status', '401')
            // 401 unauthorized response so log user out of client
            window.location.href = '/#/error';
            return Observable.throw(error.json());
        }
    }

    /**
    * This methos get all driver names list
    */
    public getDriverNameListBasedOnVendors(vendorNbr: string) {
        var driverURL = endponitConfig.DRIVER_API_ENDPOINT + 'getDriversByVendorNum/' + vendorNbr
        let url = `${driverURL}`;
        return this.http
            .get(url, { headers: this.headers })
            .map(response => response.json()).catch(this.handleError);
    }

    public getLocationByType(pickUpType, destinationType) {
        var driverURL = endponitConfig.LOCATIONS_API_ENDPOINT + 'getlocationbytype/' + pickUpType + '/' + destinationType;
        let url = `${driverURL}`;
        return this.http
            .get(url, { headers: this.headers })
            .map((response: Response) => <any>response.json()).catch(this.handleError);
    }
}
