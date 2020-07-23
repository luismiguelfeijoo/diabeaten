import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Glucose } from 'src/app/_models/glocuse';
import { environment } from 'src/environments/environment';
import { Bolus } from 'src/app/_models/bolus';

@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.scss'],
})
export class RegistryComponent implements OnInit {
  glucoseList: Glucose[];
  bolusList: Bolus[];

  constructor(
    private toastr: ToastrService,
    private router: Router,
    private authenticationService: AuthenticationService,
    private http: HttpClient
  ) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Basic ${this.authenticationService.userValue.authdata}`,
    }),
  };
  ngOnInit(): void {
    this.http
      .get<Glucose[]>(
        `${environment.apiUrl}/patients/${this.authenticationService.userValue.id}/glucose`,
        this.httpOptions
      )
      .subscribe(
        (data) => {
          console.log(data);
          this.glucoseList = data.sort(
            (a, b) => new Date(a.date).getTime() + new Date(b.date).getTime()
          );
        },
        (error) => {
          console.log(error);
        }
      );

    this.http
      .get<Bolus[]>(
        `${environment.apiUrl}/patients/${this.authenticationService.userValue.id}/bolus`,
        this.httpOptions
      )
      .subscribe(
        (data) => {
          console.log(data);
          this.bolusList = data.sort(
            (a, b) => new Date(a.date).getTime() + new Date(b.date).getTime()
          );
        },
        (error) => {
          console.log(error);
        }
      );
  }
}
