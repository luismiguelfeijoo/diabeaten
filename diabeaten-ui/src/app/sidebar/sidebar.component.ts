import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../_services';
import { HttpClient } from '@angular/common/http';

export interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}

export const ROUTES: RouteInfo[] = [
  { path: '/profile', title: 'User Profile', icon: 'nc-single-02', class: '' },
  { path: '/patients', title: 'Patients', icon: 'nc-single-02', class: '' },
  { path: '/bolus', title: 'Bolus', icon: 'nc-zoom-split', class: '' },
  { path: '/registry', title: 'registry', icon: 'nc-tile-56', class: '' },
  { path: '/reports', title: 'Reports', icon: 'nc-diamond', class: '' },
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  public menuItems: any[];

  constructor(
    private authenticationService: AuthenticationService,
    private http: HttpClient
  ) {}

  ngOnInit() {
    let role = this.authenticationService.userValue.roles[0].role;
    console.log(role);
    if (role === 'ROLE_ADMIN') {
      this.menuItems = ROUTES.filter(
        (menuItem) => menuItem.path === '/patients'
      );
    } else if (role === 'ROLE_PATIENT') {
      this.menuItems = ROUTES.filter(
        (menuItem) => menuItem.path !== '/patients'
      );
    } else {
      this.menuItems = ROUTES.filter((menuItem) => menuItem);
    }
  }
}
