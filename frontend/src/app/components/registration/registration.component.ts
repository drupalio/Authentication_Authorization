import { UserService } from './../../services/user.service';
import { User } from './../../models/user';
import { PasswordValidator } from './../../validation/password.validator';
import { RoleService } from './../../services/role.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Role } from 'src/app/models/role';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css'],
})
export class RegistrationComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private roleService: RoleService,
    private userService: UserService,
    private toastr: ToastrService
  ) {}

  form = this.fb.group(
    {
      firstName: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(20),
        ],
      ],
      lastName: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(20),
        ],
      ],
      email: ['', [Validators.required, Validators.email]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(20),
        ],
      ],
      confirmPassword: ['', [Validators.required]],
      role: ['', [Validators.required]],
    },
    { validators: PasswordValidator }
  );

  roles: Role[] = null;

  user: User = new User();

  chosenRole: Role = new Role();

  get firstName() {
    return this.form.get('firstName');
  }

  get lastName() {
    return this.form.get('lastName');
  }

  get email() {
    return this.form.get('email');
  }

  get password() {
    return this.form.get('password');
  }

  get confirmPassword() {
    return this.form.get('confirmPassword');
  }

  get role() {
    return this.form.get('role');
  }

  ngOnInit(): void {
    this.roleService.getAll().subscribe((res) => {
      this.roles = res as Role[];
    });
  }

  onSubmit() {
    this.user.firstName = this.firstName.value;
    this.user.lastName = this.lastName.value;
    this.user.email = this.email.value;
    this.user.password = this.password.value;
    this.chosenRole.role = this.role.value;
    this.user.roles.push(this.chosenRole);

    this.userService.create(this.user).subscribe((res) => {
      this.toastr.success('User created successfully', 'MyApp');
    });
  }
}
