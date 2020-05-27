import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';
import {User} from '../models/user';
import {TokenStorageService} from './security/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

   private userURL = 'http://localhost:9000/user-microservice/api/user';

  constructor(private http: HttpClient, private token: TokenStorageService) { }

  getUser(idUser: string) {
    return this.http.get<User>(this.userURL + '/getUser', {
      params: new HttpParams()
        .set('id', idUser),
    });
  }

  getProfil(email: string) {
    return this.http.get<User>(this.userURL + '/myProfil',
      {
        params : new HttpParams()
          .set('email', this.token.getEmail())
      });
  }

  getUsers() {
    return this.http.get<Array<User>>(this.userURL + '/getAll');
  }

  saveUser(form: FormGroup): Observable<FormGroup> {
    return this.http.post<FormGroup>(this.userURL + '/addUser', form.value);
  }

  updateUser(form: FormGroup): Observable<User> {
    console.log("form to update:", form.value);
    return this.http.put<User>(this.userURL + '/updateUser', form.value);
  }

  deleteUser(idUser: any): Observable<{}> {
    return this.http.delete<User>( this.userURL + '/deleteUser', {
      params: new HttpParams()
        .set('id', idUser)
    });
  }
}
