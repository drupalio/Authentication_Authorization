import { ToastrService } from 'ngx-toastr';
import { TokenService } from './../../services/token.service';
import { AccountService } from './../../services/account.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css'],
})
export class NavBarComponent implements OnInit {
  constructor(
    private accountService: AccountService,
    private tokenService: TokenService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  currentUser: any;

  ngOnInit(): void {
    this.accountService.authStatus.subscribe((res) => {
      this.currentUser = this.tokenService.getInfos();
    });
  }

  logout() {
    this.tokenService.remove();
    this.accountService.changeStatus(false);
    this.toastr.error('See you soon', 'MyApp');
    this.router.navigateByUrl('/login');
  }
}
