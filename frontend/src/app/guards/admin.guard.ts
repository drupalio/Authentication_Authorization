import { TokenService } from './../services/token.service';
import { AccountService } from './../services/account.service';
import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(
    private router: Router,
    private accountService: AccountService,
    private tokenService: TokenService
  ) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (!this.isAdmin()) {
      this.router.navigateByUrl('/user');
      return false;
    }
    return true;
  }

  isAdmin(): boolean {
    const roles = this.tokenService.getInfos().roles;
    for (const role of roles) {
      if (role.authority == 'ADMIN') return true;
    }
    return false;
  }
}
