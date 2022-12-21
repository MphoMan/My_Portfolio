import { Component, OnInit } from '@angular/core';
import { resetFakeAsyncZone } from '@angular/core/testing';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AddUserService } from '../add-user.service';
import { CommonService } from '../common.service';
import { map } from 'rxjs/operators';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  login: any = FormGroup;
  submitted: boolean =false;
  users: any = [];

  constructor(private formBuilder: FormBuilder, private router: Router, private commserv: CommonService, private userService: AddUserService) { }

  ngOnInit(): void {

    this.login= this.formBuilder.group({
      username: ['',Validators.compose([Validators.required, Validators.email])],
      password: ['',Validators.required]
    })

  }

  get formValidate (){
    return this.login.controls
  }

  get emailValidate(){
    return this.login.get('email');
  }
  

  loginSubmit(){

    const username = this.getUsernameFromForm();
    const password = this.getPasswordFromForm();
    console.error(`Inside login`);
    this.userService.login(username, password).pipe(map(
      response => {
        sessionStorage.setItem('username',username);
        let tokenStr = 'Bearer ' + response.token;
        sessionStorage.setItem('token', tokenStr);
        // Added the directory
        localStorage.setItem("isLoggedIn", "true");
         this.router.navigate(['home']);
        return response;
      }
    ));
  }

  goToSignup(){
      this.router.navigate(["signup"]);
  }

  getUsernameFromForm(): string {
    return this.login.get(['username'])!.value;
  }

  getPasswordFromForm(): string {
    return this.login.get(['password'])!.value;
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username');
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem('username');
  }
}
