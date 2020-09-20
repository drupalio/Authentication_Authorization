import { AccountService } from './../../services/account.service';
import { TokenService } from './../../services/token.service';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private toaster: ToastrService,
    private tokenService: TokenService,
    private accountService: AccountService,
    private router: Router
  ) {}

  form = this.fb.group({
    email: ['', [Validators.email, Validators.required]],
    password: [
      '',
      [Validators.required, Validators.minLength(8), Validators.maxLength(20)],
    ],
  });

  get email() {
    return this.form.get('email');
  }

  get password() {
    return this.form.get('password');
  }

  ngOnInit(): void {}

  login(form) {
    this.auth.login(form).subscribe(
      (res) => {
        this.tokenService.handle(res);
        this.accountService.changeStatus(true);
        this.router.navigateByUrl('/user');
        this.toaster.success(
          `Hello ${this.tokenService.getInfos().name}`,
          'MyApp'
        );
      },
      (err) => {
        this.toaster.error(
          'You have entered an invalid email or password',
          'MyApp'
        );
      }
    );
  }
}
