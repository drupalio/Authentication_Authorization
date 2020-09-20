import { AccountService } from './../services/account.service';
import { TokenService } from './../services/token.service';
import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BeforeAuthGuard implements CanActivate {
  constructor(
    private tokenService: TokenService,
    private router: Router,
    private accountService: AccountService
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (!this.tokenService.loggedIn()) {
      this.tokenService.remove();
      this.accountService.changeStatus(false);
      this.router.navigateByUrl('login');
      return false;
    }
    return true;
  }
}
