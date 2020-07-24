import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Patient } from 'src/app/_models/patient';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.scss'],
})
export class PatientListComponent implements OnInit {
  patientList: Patient[] = [];

  constructor(
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
    if (
      !this.authenticationService.userValue.roles.find(
        (role) => role.role === 'ROLE_ADMIN'
      )
    ) {
      this.router.navigate(['/profile']);
    }

    this.http
      .get<Patient[]>(`${environment.apiUrl}/patients`, this.httpOptions)
      .subscribe(
        (data) => {
          this.patientList = data;
        },
        (error) => console.log(error)
      );
  }
}
