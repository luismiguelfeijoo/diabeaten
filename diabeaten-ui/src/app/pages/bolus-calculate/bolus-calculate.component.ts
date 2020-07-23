import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Bolus } from 'src/app/_models/bolus';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-bolus-calculate',
  templateUrl: './bolus-calculate.component.html',
  styleUrls: ['./bolus-calculate.component.scss'],
})
export class BolusCalculateComponent implements OnInit {
  calculateForm: FormGroup = new FormGroup({
    glucose: new FormControl(),
    carbs: new FormControl(),
    date: new FormControl(),
  });

  addBolusForm: FormGroup = new FormGroup({
    glucose: new FormControl(),
    chBolus: new FormControl(),
    correctionBolus: new FormControl(),
    date: new FormControl(),
  });

  constructor(
    private toastr: ToastrService,
    private formBuilder: FormBuilder,
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
      this.authenticationService.isAdmin(this.authenticationService.userValue)
    ) {
      this.router.navigate(['/patients']);
    }
    this.calculateForm = this.formBuilder.group({
      glucose: ['', [Validators.required, Validators.min(0)]],
      carbs: ['', [Validators.required, Validators.min(0)]],
      date: [new Date()],
    });

    this.addBolusForm = this.formBuilder.group({
      glucose: ['', [Validators.required, Validators.min(0)]],
      chBolus: ['', [Validators.required, Validators.min(0)]],
      correctionBolus: ['', Validators.required],
      date: [null],
    });
  }

  onCalculate(): void {
    if (this.calculateForm.invalid) {
      return;
    }

    this.http
      .post<Bolus>(
        `${environment.apiUrl}/patients/${this.authenticationService.userValue.id}/bolus/calculate`,
        {
          glucose: this.calculateForm.controls.glucose.value,
          date: this.calculateForm.controls.date.value || new Date(),
          carbs: this.calculateForm.controls.carbs.value,
        },
        this.httpOptions
      )
      .subscribe(
        (data) => {
          // console.log(data);
          this.addBolusForm = this.formBuilder.group({
            glucose: [data.glucose, [Validators.required, Validators.min(0)]],
            chBolus: [data.chBolus, [Validators.required, Validators.min(0)]],
            correctionBolus: [data.correctionBolus, Validators.required],
            date: [data.date],
          });
          this.toastr.success(
            `Bolus calculated, press add to register it on your history`,
            '',
            {
              timeOut: 2000,
              enableHtml: true,
              toastClass: 'alert alert-success alert-with-icon',
              positionClass: 'toast-top-center',
            }
          );
        },
        (error) => {}
      );
  }

  addBolus(): void {
    if (this.addBolusForm.invalid) {
      return;
    }
    if (
      Math.round(
        (((new Date(this.addBolusForm.controls.date.value).getTime() -
          new Date().getTime()) %
          86400000) %
          3600000) /
          60000
      ) > 15
    ) {
      this.toastr.error(
        `You've to calculate bolus again, it has been more than 15 minutes since your last calculation`,
        '',
        {
          timeOut: 2000,
          enableHtml: true,
          toastClass: 'alert alert-danger alert-with-icon',
          positionClass: 'toast-top-center',
        }
      );
      return;
    }

    this.http
      .post<Bolus>(
        `${environment.apiUrl}/patients/${this.authenticationService.userValue.id}/bolus`,
        this.addBolusForm.value,
        this.httpOptions
      )
      .subscribe(
        (data) => {
          this.addBolusForm = this.formBuilder.group({
            glucose: ['', [Validators.required, Validators.min(0)]],
            chBolus: ['', [Validators.required, Validators.min(0)]],
            correctionBolus: ['', Validators.required],
          });
        },
        (error) => {
          this.toastr.error(`${error}`, '', {
            timeOut: 2000,
            enableHtml: true,
            toastClass: 'alert alert-danger alert-with-icon',
            positionClass: 'toast-top-center',
          });
        }
      );
  }
}
