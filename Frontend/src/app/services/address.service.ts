import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Address } from '../models/address';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private url: string = 'http://localhost:8080/';

  constructor(private httpClient: HttpClient) { }

  getUserAddresses(userId: string): Observable<Address[]> {
    return this.httpClient.get<Address[]>(`${this.url}addresses/getaddresses?id=${userId}`);
  }

  createAddress(address: Address): Observable<Address> {
    return this.httpClient.post<Address>(`${this.url}addresses/createaddress`, address);
  }

  updateAddress(address: Address): Observable<Address> {
    return this.httpClient.put<Address>(`${this.url}addresses/updateaddress`, address);
  }

  deleteAddress(addressId: string): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.url}addresses/removeAddress/?id=${addressId}`);
  }
}

export function getMainAddress(addresses: Address[]): string {

  for(let address of addresses) {
    if(address.main_address){
      return address.street_name + ", " + address.street_number;
    }
  }

  return "-";
}
