import { environment } from './../../environments/environment';
import { Role } from './../models/role';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RoleService {
  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get(`${environment.apiURL}/roles`);
  }
}
