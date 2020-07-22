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

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  disabledLogin = true;
  disabledRegister = true;
  rightPanel = false;
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
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
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

  onSubmitLogin(): void {}

  onSubmitRegister(): void {}
}
