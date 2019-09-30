import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { endponitConfig } from '../environments/endpoint';

export type InternalStateType = {
  [key: string]: any
};

@Injectable()
export class AppState {
  _state: InternalStateType = {};

  constructor(private readonly http: HttpClient) {

  }

  // already return a clone of the current state
  get state() {
    return this._state = this._clone(this._state);
  }
  // never allow mutation
  set state(value) {
    throw new Error('do not mutate the `.state` directly');
  }


  get(prop?: any) {
    // use our state getter for the clone
    const state = this.state;
    return state.hasOwnProperty(prop) ? state[prop] : state;
  }

  set(prop: string, value: any) {
    // internally mutate our state
    return this._state[prop] = value;
  }


  private _clone(object: InternalStateType) {
    // simple object clone
    return JSON.parse(JSON.stringify(object));
  }
  headers = new HttpHeaders().set('Authorization', JSON.parse(sessionStorage.getItem("Autherization")))

  getAllFacilities() {
    return this.http.get(endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getAllFacilitesExceptRentals/0', { headers: this.headers })
      .map((response: any) => response)
  }
  getAllRentalFacilities() {
    return this.http.get(endponitConfig.INVENTORY_ITEM_ENDPOINT + 'getAllFacilitesExceptRentals/1', { headers: this.headers })
      .map((response: any) => response)
  }
}
