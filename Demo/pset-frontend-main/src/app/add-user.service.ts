import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUserLogin } from './models/user-login.model';
import { IUser } from './models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AddUserService {

  constructor(private http: HttpClient) { }
  register(user: IUser): Observable<IUser>{
    return this.http.post<IUser>("http://localhost:8080/api/users/register", user);

  }

  findAll(): Observable<IUser[]> {
    return this.http.get<IUser[]>("http://localhost:8080/api/users/");
  }

   login(username: string, password: string): Observable<any> {
    console.error(`Inside service login ${username}`);
    return this.http.post('http://localhost:8080/api/login', {"username": username, "password": password});
  }

  verifyEmail(verificationCode: string): Observable<{}> {
    return this.http.get(`http://localhost:8080/api/users/confirm-email/${verificationCode}`);
  }
}
