import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationService } from 'src/app/_services';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Glucose } from 'src/app/_models/glocuse';
import { environment } from 'src/environments/environment';
import { Bolus } from 'src/app/_models/bolus';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  FormControl,
  FormGroup,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { createOfflineCompileUrlResolver } from '@angular/compiler';
import { NotificationService } from 'src/app/_services/notification.service';

@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.scss'],
})
export class RegistryComponent implements OnInit {
  id;
  glucoseList: Glucose[];
  bolusList: Bolus[];
  modalRefence;

  addGlucoseForm: FormGroup = new FormGroup({
    glucose: new FormControl(),
  });

  addBolusForm: FormGroup = new FormGroup({
    glucose: new FormControl(),
    chBolus: new FormControl(),
    correctionBolus: new FormControl(),
  });

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Basic ${this.authenticationService.userValue.authdata}`,
    }),
  };

  open(content) {
    this.modalRefence = this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title',
    });
    this.modalRefence.result.then((result) => {
      // console.log(`Closed with: ${result}`);
    });
  }

  constructor(
    private modalService: NgbModal,
    private toastr: ToastrService,
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private http: HttpClient,
    private activatedRoute: ActivatedRoute,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    if (
      this.authenticationService.isAdmin(this.authenticationService.userValue)
    ) {
      this.id = this.activatedRoute.snapshot.paramMap.get('id');
    } else {
      this.id = this.authenticationService.userValue.id;
    }
    console.log(this.id);

    this.http
      .get<Glucose[]>(
        `${environment.apiUrl}/patients/${this.id}/glucose`,
        this.httpOptions
      )
      .subscribe(
        (data) => {
          // console.log(data);
          this.glucoseList = data.sort(
            (a, b) => new Date(a.date).getTime() + new Date(b.date).getTime()
          );
        },
        (error) => {
          // console.log(error);
        }
      );

    this.http
      .get<Bolus[]>(
        `${environment.apiUrl}/patients/${this.id}/bolus`,
        this.httpOptions
      )
      .subscribe(
        (data) => {
          // console.log(data);
          this.bolusList = data.sort(
            (a, b) => new Date(a.date).getTime() + new Date(b.date).getTime()
          );
        },
        (error) => {
          // console.log(error);
        }
      );

    this.addGlucoseForm = this.formBuilder.group({
      glucose: ['', [Validators.required, Validators.min(0)]],
    });

    this.addBolusForm = this.formBuilder.group({
      glucose: ['', [Validators.required, Validators.min(0)]],
      chBolus: ['', [Validators.required, Validators.min(0)]],
      correctionBolus: ['', Validators.required],
    });
  }

  addGlucose(): void {
    if (this.addGlucoseForm.invalid) {
      return;
    }
    const newGlucose: Glucose = {
      glucose: this.addGlucoseForm.controls.glucose.value,
      date: new Date(),
    };
    this.http
      .post<Glucose>(
        `${environment.apiUrl}/patients/${this.id}/glucose`,
        newGlucose,
        this.httpOptions
      )
      .subscribe(
        (data) => {
          this.glucoseList.push(data);
          this.glucoseList.sort(
            (a, b) => new Date(a.date).getTime() + new Date(b.date).getTime()
          );
          this.modalRefence.close();
          this.addGlucoseForm = this.formBuilder.group({
            glucose: ['', [Validators.required, Validators.min(0)]],
          });
          this.sendGlucose(data.glucose);
        },
        (error) => {
          // console.log(error);
        }
      );
  }

  addBolus(): void {
    if (this.addBolusForm.invalid) {
      return;
    }
    const newBolus: Bolus = {
      glucose: this.addBolusForm.controls.glucose.value,
      chBolus: this.addBolusForm.controls.chBolus.value,
      correctionBolus: this.addBolusForm.controls.correctionBolus.value,
      date: new Date(),
    };
    this.http
      .post<Bolus>(
        `${environment.apiUrl}/patients/${this.id}/bolus`,
        newBolus,
        this.httpOptions
      )
      .subscribe(
        (data) => {
          this.bolusList.push(data);
          this.bolusList.sort(
            (a, b) => new Date(a.date).getTime() + new Date(b.date).getTime()
          );
          this.modalRefence.close();
          this.addBolusForm = this.formBuilder.group({
            glucose: ['', [Validators.required, Validators.min(0)]],
            chBolus: ['', [Validators.required, Validators.min(0)]],
            correctionBolus: ['', Validators.required],
          });
          this.sendBolus(data.chBolus + data.correctionBolus);
        },
        (error) => {
          // console.log(error);
        }
      );
  }

  sendGlucose(message: any) {
    this.notificationService._send(message, '/app/notification/glucose');
  }

  sendBolus(message: any) {
    this.notificationService._send(message, '/app/notification/bolus');
  }
}
