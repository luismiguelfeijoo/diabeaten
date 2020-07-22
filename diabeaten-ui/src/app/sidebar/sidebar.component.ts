import { Component, OnInit } from '@angular/core';

export interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}

export const ROUTES: RouteInfo[] = [
  { path: '/profile', title: 'User Profile', icon: 'nc-single-02', class: '' },
  { path: '/bolus', title: 'Bolus', icon: 'nc-tile-56', class: '' },
  { path: '/registry', title: 'registry', icon: 'nc-pin-3', class: '' },
  { path: '/reports', title: 'Reports', icon: 'nc-diamond', class: '' },
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  public menuItems: any[];
  ngOnInit() {
    this.menuItems = ROUTES.filter((menuItem) => menuItem);
  }
}
