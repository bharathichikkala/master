import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Http, Headers, Response, RequestOptions } from '@angular/http';

import 'rxjs/add/operator/map'
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/delay';

import { endponitConfig } from '../../environments/endpoints';


@Injectable()
export class NotificationService {
    public headers: Headers;
    public role;
    public userId;

    constructor(private http: Http) {
        this.headers = new Headers();
        this.headers.append('Authorization', localStorage.getItem('Authentication'));
        this.headers.append('Content-Type', 'application/json');
    }

    getAllUsers(): Observable<UserData[]> {
        return this.http.get(endponitConfig.USER_API_ENDPOINT + 'getAllUsers', { headers: this.headers })
            .map((response: Response) => response.json().data).catch(this.handleError);
    }

    deleteUser(userId: any) {
        return this.http.get(endponitConfig.USER_API_ENDPOINT + 'deleteUser/' + userId, { headers: this.headers })
            .map(res => { return res.json() }).catch(this.handleError);
    }

    updateUser(userDetails) {
        return this.http.put(endponitConfig.USER_API_ENDPOINT + 'updateUser', JSON.stringify(userDetails), { headers: this.headers })
            .map(res => { return res.json() }).catch(this.handleError);
    }


    addChannel(channelData) {
        return this.http.post(endponitConfig.NOTIFICATIONS_API_ENDPOINT + 'eventsettings', channelData, { headers: this.headers })
            .map(res => { return res.json() }).catch(this.handleError);
    }

    updateChannel(channelData) {
        return this.http.put(endponitConfig.NOTIFICATIONS_API_ENDPOINT + 'eventsettings',
            channelData, { headers: this.headers }).map(res => { return res.json() }).catch(this.handleError);
    }

    deleteChannel(notificationData) {
        return this.http.delete(endponitConfig.NOTIFICATIONS_API_ENDPOINT + 'eventsettings/' + notificationData.id,
            { headers: this.headers }).map(res => { return res.json() }).catch(this.handleError);
    }


    getDefaultNotificationSettings() {
        return this.http.get(endponitConfig.NOTIFICATIONS_API_ENDPOINT + 'eventsettings/default',
            { headers: this.headers }).map(res => { return res.json() }).catch(this.handleError);
    }



    getChannels() {
        return this.http.get(endponitConfig.NOTIFICATIONS_API_ENDPOINT + 'getNotificationEventSettingsByRole/' +
            localStorage.getItem('userRole') + '/' + localStorage.getItem('userData'),
            { headers: this.headers }).map(res => { return res.json() }).catch(this.handleError);
    }

    getEmailTemplates() {
        return this.http.get(endponitConfig.TEMPLATES_API_ENDPOINT,
            { headers: this.headers }).map(res => { return res.json() }).catch(this.handleError);
    }


    getNotifications() {
        return this.http.get(endponitConfig.NOTIFICATIONS_API_ENDPOINT + 'notify/' + localStorage.getItem('userData'),
            { headers: this.headers }).map(res => { return res.json() }).catch(this.handleError);
    }


    /**
  * This method gets  user details based on user Id
  */
    public getUserDetailsByID(userId: string) {
        return this.getAllUsers().toPromise().then(users =>
            users.find(usera => Number(usera.id) === Number(userId))).catch(this.handleError);
    }

    /*
    This method gets all user roles
  */
    public getAllRoles() {
        return this.http.get(endponitConfig.USER_API_ENDPOINT + 'getAllRoles', { headers: this.headers })
            .map(res => { return res.json() }).catch(this.handleError);
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




}
export class UserData {
    public id: string;
    public name: string;
    public email: string;
    public phone: string;
    public active: boolean;
    public roles: roles[]
    constructor() { }
}
export class roles {
    public id: number;
    public name: string
}



