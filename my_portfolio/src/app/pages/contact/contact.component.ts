import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators, FormControlName} from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient, HttpClientModule } from "@angular/common/http";


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  latitude = 51.678418;
  longitude = 7.809007;
  // locationChosen = false; avoiding default location

  onChoselocation(event: any){
    this.latitude = event.coords.lat;
    this.longitude = event.coords.lng;
    // this.locationChosen = true; when selected on the map
  }

  sendData: any = FormGroup;
  submitted: boolean = false; // avoid form from submitting
  
  

  constructor(private formBulder: FormBuilder, private router: Router, private http: HttpClient){}

  ngOnInit(): void {

     this.sendData = this.formBulder.group({ 
      name: new FormControl(null, [Validators.required]),
      email: ['', Validators.compose([Validators.required, 
              Validators.email,Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])],
    subject: new FormControl(null, [Validators.required]),
    message: new FormControl(null, [Validators.required, Validators.maxLength(256)])

     })
  }

  get formValidate(){
    return this.sendData.controls;
  }

  get emailValidate(){
    return this.sendData.get('email');
  }

  submitMessage(){
 
    this.submitted= true;
    // alert("Thank you, i'll get back to you as soon as possible.");
   
   
  }

  
}
