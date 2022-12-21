import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl, FormControlName } from '@angular/forms';
import { Router } from '@angular/router';
import { AddUserService } from '../add-user.service';
import { CommonService } from '../common.service';
import {IUser, User} from '../models/user.model';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signup: any = FormGroup;
  submitted: boolean =false;
  id:any = 2;

  password_pattern = '(?=\\D*\\d)(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z]).{8,30}';
  http: any;

  constructor(private formBulder: FormBuilder, private router: Router, private commServ: CommonService,
    private userService: AddUserService ) { }

  ngOnInit(): void {
    this.signup =this.formBulder.group({
      //Adding my new validation below
      username: new FormControl(null, [Validators.required, Validators.pattern('[a-zA-Z]*')]),
      email: ['', Validators.compose([Validators.required, Validators.email,
         Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])],
      firstName: new FormControl(null, [Validators.required, Validators.pattern('[a-zA-Z]*')]),
      lastName: new FormControl(null, [Validators.required, Validators.pattern('[a-zA-Z]*')]),
      password: new FormControl(null, [Validators.required, Validators.maxLength(8)]),
      confirmPassword: new FormControl(null, [Validators.required]),
      type: new FormControl( null, [Validators.required])
    },
    {
      validators: this.mustMatch('password','confirmPassword')
    })
  }

  get formValidate (){
    return this.signup.controls
  }

  get emailValidate(){
    return this.signup.get('email');
  }

  mustMatch(ControlName: string, matchingControlName:string){
      return(formGroup: FormGroup)=> {
        const control = formGroup.controls[ControlName];
        const matchingControl = formGroup.controls[matchingControlName];

        if(matchingControl.errors && !matchingControl.errors['MustMatch']){
          return
        }
        if(control.value !==matchingControl.value){
          matchingControl.setErrors({MustMatch: true});
        }
        else {
          matchingControl.setErrors(null)
        }
      }
  }

  signupSubmit(){
    this.submitted =true;
    const user = this.createFromForm();
    console.error(`User ${JSON.stringify(user)}`)
    this.userService.register(user).subscribe(res =>{
     alert("Hello " + user.username + " you have successfully registered, please Check your email to login");
      this.router.navigate(['verifyemail']);
    });

  }

  goTologin(){
    this.router.navigate(['login']);
  }

  protected createFromForm(): IUser {
    return {
      ...new User(),
      username: this.signup.get(['username'])!.value,
      email: this.signup.get(['email'])!.value,
      password: this.signup.get(['password'])!.value,
      confirmPassword: this.signup.get(['confirmPassword'])!.value,
      firstName: this.signup.get(['firstName'])!.value,
      lastName: this.signup.get(['lastName'])!.value,
      type: this.signup.get(['type'])!.value
    }
  }

}
