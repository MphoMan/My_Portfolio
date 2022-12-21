import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AddUserService } from '../add-user.service';
import { CommonService } from '../common.service';
import { IUser } from '../models/user.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  userList: any =[];
  users: IUser[] = [];
  
  
  constructor(private commonServ: CommonService, private router: Router, private userService: AddUserService) { }

  ngOnInit(): void {
    // this.commonServ.getUser().subscribe((data:any) =>{
    //   this.userList = data;
    // })
    this.userService.findAll().subscribe(res => {
      this.users = res;
    });
  }

  save(){
    sessionStorage.setItem('username', 'this.users');
  }

  get(){
    return sessionStorage.getItem('username');
  }

  logOut(){
    localStorage.clear();
    this.router.navigate(['login']);
  }

}
