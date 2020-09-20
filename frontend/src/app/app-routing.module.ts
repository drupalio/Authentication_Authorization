import { AdminGuard } from './guards/admin.guard';
import { AfterAuthGuard } from './guards/after-auth.guard';
import { BeforeAuthGuard } from './guards/before-auth.guard';
import { HelloUserComponent } from './components/hello-user/hello-user.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { LoginComponent } from './components/login/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HelloAdminComponent } from './components/hello-admin/hello-admin.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/user',
    pathMatch: 'full',
    canActivate: [BeforeAuthGuard],
  },
  { path: 'login', component: LoginComponent, canActivate: [AfterAuthGuard] },
  { path: 'register', component: RegistrationComponent },
  {
    path: 'user',
    component: HelloUserComponent,
    canActivate: [BeforeAuthGuard],
  },
  {
    path: 'admin',
    component: HelloAdminComponent,
    canActivate: [BeforeAuthGuard, AdminGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
