import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl,
} from '@angular/forms';
import { AuthenticationService } from '../../_services';
import { ToastrService } from 'ngx-toastr';
import { first } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/_models';
import { environment } from 'src/environments/environment';
import { Patient } from 'src/app/_models/patient';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loading = false;
  disabledLogin = true;
  disabledRegister = true;
  rightPanel = false;
  error = '';
  loginForm: FormGroup = new FormGroup({
    username: new FormControl(),
    password: new FormControl(),
  });

  registerForm: FormGroup = new FormGroup({
    username: new FormControl(),
    name: new FormControl(),
    password: new FormControl(),
    confirmPassword: new FormControl(),
  });

  constructor(
    private toastr: ToastrService,
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService: AuthenticationService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    if (this.authenticationService.userValue) {
      this.router.navigate(['/profile']);
    }
    this.loginForm = this.formBuilder.group({
      username: [
        '',
        [
          Validators.required,
          Validators.pattern(
            /^[\w!#$%&’*+\/=?`{|}~^-]+(?:\.[\w!#$%&’*+\/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$/
          ),
        ],
      ],
      password: ['', Validators.required],
    });

    this.registerForm = this.formBuilder.group({
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
      confirmPassword: ['', Validators.required],
    });

    this.loginForm.valueChanges.subscribe((values) => {
      this.disabledLogin = this.loginForm.invalid;
    });

    this.registerForm.valueChanges.subscribe((values) => {
      if (
        values.confirmPassword === values.password &&
        this.registerForm.valid
      ) {
        this.disabledRegister = false;
      } else {
        this.disabledRegister = true;
      }
    });
  }

  changePanel(state: boolean) {
    this.rightPanel = state;
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  get r() {
    return this.registerForm.controls;
  }

  onSubmitLogin(): void {
    this.loading = true;
    this.disabledLogin = true;
    this.authenticationService
      .login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        (data) => {
          this.router.navigate(['/profile']);
          this.toastr.success(
            `Welcome back to DIABEATEN! ${data.username}`,
            '',
            {
              timeOut: 2000,
              enableHtml: true,
              toastClass: 'alert alert-success alert-with-icon',
              positionClass: 'toast-top-center',
            }
          );
        },
        (error) => {
          this.error = error;
          this.toastr.error(`Invalid Username or Password combination`, '', {
            timeOut: 2000,
            enableHtml: true,
            toastClass: 'alert alert-danger alert-with-icon',
            positionClass: 'toast-top-center',
          });
          this.disabledLogin = false;
          this.loading = false;
        }
      );
  }

  onSubmitRegister(): void {
    this.loading = true;
    this.disabledRegister = true;
    let newPatient: Patient = {
      username: this.r.username.value,
      password: this.r.password.value,
      name: this.r.name.value,
      ratios: [{ startHour: '00:00:00', endHour: '23:59:59', ratioInGrams: 0 }],
      sensibilities: [
        { startHour: '00:00:00', endHour: '23:59:59', sensibility: 0 },
      ],
      totalBasal: 0,
      dia: 3,
    };
    this.http
      .post<User>(`${environment.apiUrl}/patients`, newPatient)
      .subscribe(
        (data) => {
          // console.log(data);
          /*
          this.toastr.success(
            `<span class="tim-icons icon-bell-55" [data-notify]="icon"></span> Welcome to DIABEATEN! ${data.username}`,
            '',
            {
              timeOut: 2000,
              enableHtml: true,
              toastClass: 'alert alert-success alert-with-icon',
              positionClass: 'toast-top-center',
            }
            */
          this.authenticationService
            .login(newPatient.username, newPatient.password)
            .pipe(first())
            .subscribe(
              (response) => {
                this.router.navigate(['/profile']);
                this.toastr.success(
                  `Welcome to DIABEATEN! ${response.username}, please take a minute to fill up your information`,
                  '',
                  {
                    timeOut: 2000,
                    enableHtml: true,
                    toastClass: 'alert alert-success alert-with-icon',
                    positionClass: 'toast-top-center',
                  }
                );
              },
              (error) => {
                this.error = error;
                this.toastr.error(
                  `Invalid Username or Password combination`,
                  '',
                  {
                    timeOut: 2000,
                    enableHtml: true,
                    toastClass: 'alert alert-danger alert-with-icon',
                    positionClass: 'toast-top-center',
                  }
                );
                this.disabledLogin = false;
                this.loading = false;
              }
            );
          this.disabledRegister = false;
        },
        (error) => {
          // console.log(error);
          this.toastr.error(
            `${error ? 'Error creating user with the provided username' : ''}`,
            '',
            {
              timeOut: 2000,
              enableHtml: true,
              toastClass: 'alert alert-danger alert-with-icon',
              positionClass: 'toast-top-center',
            }
          );
          this.disabledRegister = false;
        }
      );
  }
}
