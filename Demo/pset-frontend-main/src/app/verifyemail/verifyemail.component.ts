import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AddUserService } from '../add-user.service';
import { CommonService } from '../common.service';
import { UserLogin } from '../models/user-login.model';

@Component({
  selector: 'app-verifyemail',
  templateUrl: './verifyemail.component.html',
  styleUrls: ['./verifyemail.component.css']
})
export class VerifyemailComponent implements OnInit {

  verifyEmail: any = FormGroup;
  submitted: boolean =false;

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private commserv: CommonService,
    private userService: AddUserService) { }

  ngOnInit(): void {

    this.verifyEmail= this.formBuilder.group({
      code: ['',Validators.required]

    })
  }

  get formValidate (){
    return this.verifyEmail.controls
  }

  get emailValidate(){
    return this.verifyEmail.get('code');
  }

  loginSubmit(){

    this.submitted =true;
    this.userService.verifyEmail(this.verifyEmail.get(['code'])!.value).subscribe(res => {
        console.log("You have successfully confirmed your registration");
        //after change this to go home page
        localStorage.setItem("isLoggedIn", "true");
        this.router.navigate(['home']);
        // TODO 
        //this.router.navigate(['login']);
    }
    // , (error) => {
    //     alert("You have entered wrong or Invalid code");
    //     localStorage.clear();
    //   }
      );

  }

  getCodeFromForm(): string {
    return this.verifyEmail.get(['code'])!.value;
  }

}
