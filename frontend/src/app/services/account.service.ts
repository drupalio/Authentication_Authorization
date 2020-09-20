import { TokenService } from './token.service';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private tokenService: TokenService) {}

  private loggedIn = new BehaviorSubject<boolean>(this.tokenService.loggedIn());

  authStatus = this.loggedIn.asObservable();

  changeStatus(status: boolean) {
    this.loggedIn.next(status);
  }
}
