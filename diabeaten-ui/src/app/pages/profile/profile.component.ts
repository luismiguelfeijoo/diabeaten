import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
  FormArray,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Patient } from 'src/app/_models/patient';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/_models';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Monitor } from 'src/app/_models/monitor';
import { error } from 'protractor';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  loading = false;
  disabled = false;

  modalRefence;

  monitorForm: FormGroup = new FormGroup({
    username: new FormControl(),
    name: new FormControl(),
    password: new FormControl(),
  });
  monitors: User[] = [];
  profileForm: FormGroup = new FormGroup({
    username: new FormControl(),
    name: new FormControl(),
    totalBasal: new FormControl(),
    dia: new FormControl(),
    ratios: new FormArray([]),
    sensibilities: new FormArray([]),
  });

  constructor(
    private toastr: ToastrService,
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private http: HttpClient,
    private modalService: NgbModal
  ) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Basic ${this.authenticationService.userValue.authdata}`,
    }),
  };

  ratios(): FormArray {
    return this.profileForm.get('ratios') as FormArray;
  }

  newRatio(startHour, endHour, ratioInGrams): FormGroup {
    return this.formBuilder.group({
      startHour,
      endHour,
      ratioInGrams,
    });
  }

  addRatio({ startHour, endHour, ratioInGrams }) {
    this.ratios().push(this.newRatio(startHour, endHour, ratioInGrams));
  }

  removeRatio(i: number) {
    this.ratios().removeAt(i);
  }

  sensibilities(): FormArray {
    return this.profileForm.get('sensibilities') as FormArray;
  }

  newSensibility(startHour, endHour, sensibility): FormGroup {
    return this.formBuilder.group({
      startHour,
      endHour,
      sensibility,
    });
  }

  addSensibility({ startHour, endHour, sensibility }) {
    this.sensibilities().push(
      this.newSensibility(startHour, endHour, sensibility)
    );
  }
  removeSensibility(i: number) {
    this.sensibilities().removeAt(i);
  }

  ngOnInit(): void {
    if (
      this.authenticationService.isAdmin(this.authenticationService.userValue)
    ) {
      this.router.navigate(['/patients']);
    }
    this.http
      .get<Patient>(
        `${environment.apiUrl}/patients/${this.authenticationService.userValue.id}`,
        this.httpOptions
      )
      .subscribe(
        (response) => {
          console.log(response.ratios[0]);
          this.profileForm = this.formBuilder.group({
            username: [response.username],
            name: [response.name, Validators.required],
            totalBasal: [response.totalBasal, Validators.required],
            dia: [response.dia, Validators.required],
            ratios: this.formBuilder.array([]),
            sensibilities: this.formBuilder.array([]),
          });
          this.monitors = response.monitors;
          response.ratios.forEach((ratio) => {
            this.addRatio(ratio);
          });
          response.sensibilities.forEach((sensibility) => {
            this.addSensibility(sensibility);
          });
        },
        (error) => {
          console.log(error);
        }
      );
    this.monitorForm = this.formBuilder.group({
      username: [
        '',
        [
          Validators.required,
          Validators.pattern(
            /^[\w!#$%&’*+\/=?`{|}~^-]+(?:\.[\w!#$%&’*+\/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$/
          ),
        ],
      ],
      name: ['', Validators.required],
      password: [
        '',
        [
          Validators.required,
          Validators.pattern(
            /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$/
          ),
        ],
      ],
    });

    this.profileForm.valueChanges.subscribe((values) => {
      console.log(values);
    });
  }

  get f() {
    return this.profileForm.controls;
  }

  onSubmit(): void {
    console.log(this.profileForm.value);
    this.loading = true;
    this.disabled = true;
    this.ratios().value.forEach((element) => {
      if (element.startHour.length < 8) {
        element.startHour += ':00';
      }
      if (element.endHour.length < 8) {
        element.end += ':00';
      }
    });

    this.sensibilities().value.forEach((element) => {
      if (element.startHour.length < 8) {
        element.startHour += ':00';
      }
      if (element.endHour.length < 8) {
        element.end += ':00';
      }
    });
    this.http
      .put<Patient>(
        `${environment.apiUrl}/patients/${this.authenticationService.userValue.id}`,
        this.profileForm.value,
        this.httpOptions
      )
      .subscribe(
        (response) => {
          this.toastr.success(`Profile updated successfully`, '', {
            timeOut: 2000,
            enableHtml: true,
            toastClass: 'alert alert-success alert-with-icon',
            positionClass: 'toast-top-center',
          });

          this.profileForm = this.formBuilder.group({
            username: [response.username],
            name: [response.name, Validators.required],
            totalBasal: [response.totalBasal, Validators.required],
            dia: [response.dia, Validators.required],
            ratios: this.formBuilder.array([]),
            sensibilities: this.formBuilder.array([]),
          });
          response.ratios.forEach((ratio) => {
            this.addRatio(ratio);
          });
          response.sensibilities.forEach((sensibility) => {
            this.addSensibility(sensibility);
          });
        },
        (error) => {
          console.log(error);
        }
      );
  }

  open(content) {
    this.modalRefence = this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title',
    });
    this.modalRefence.result.then((result) => {
      // console.log(`Closed with: ${result}`);
    });
  }

  addMonitor(): void {
    if (this.monitorForm.invalid) {
      return;
    }
    const monitor: Monitor = {
      username: this.monitorForm.controls.username.value,
      name: this.monitorForm.controls.name.value,
      password: this.monitorForm.controls.password.value,
      patientId: this.authenticationService.userValue.id,
    };
    this.http
      .post<User>(`${environment.apiUrl}/monitors`, monitor, this.httpOptions)
      .subscribe(
        (data) => {
          this.toastr.success(`Monitor created!`, '', {
            timeOut: 2000,
            enableHtml: true,
            toastClass: 'alert alert-success alert-with-icon',
            positionClass: 'toast-top-center',
          });
          this.monitors.push(data);
          this.modalRefence.close();
          this.monitorForm = this.formBuilder.group({
            username: [
              '',
              [
                Validators.required,
                Validators.pattern(
                  /^[\w!#$%&’*+\/=?`{|}~^-]+(?:\.[\w!#$%&’*+\/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$/
                ),
              ],
            ],
            name: ['', Validators.required],
            password: [
              '',
              [
                Validators.required,
                Validators.pattern(
                  /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$/
                ),
              ],
            ],
          });
        },
        (error) => {
          this.toastr.success(`Cannot create monitor, try again later`, '', {
            timeOut: 2000,
            enableHtml: true,
            toastClass: 'alert alert-danger alert-with-icon',
            positionClass: 'toast-top-center',
          });
        }
      );
  }
}
