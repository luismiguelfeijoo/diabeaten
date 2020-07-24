import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProfileComponent } from './pages/profile/profile.component';
import { AuthGuard } from './_helpers';
import { LoginComponent } from './pages/login/login.component';
import { RegistryComponent } from './pages/registry/registry.component';
import { BolusCalculateComponent } from './pages/bolus-calculate/bolus-calculate.component';
import { PatientListComponent } from './pages/patient-list/patient-list.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login',
  },
  {
    path: 'login',
    pathMatch: 'full',
    component: LoginComponent,
  },
  {
    path: 'profile',
    component: ProfileComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
  },
  {
    path: 'bolus',
    component: BolusCalculateComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
  },
  {
    path: 'registry',
    component: RegistryComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
  },
  {
    path: 'registry/:id',
    component: RegistryComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
  },
  {
    path: 'patients',
    component: PatientListComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
  },
  // { path: '404', component:  },
  { path: '**', redirectTo: '/404' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
