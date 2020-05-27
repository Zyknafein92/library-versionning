import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';

import {JwtResponse} from './JwtReponse';
import {AuthLoginInfo} from './login-info';
import {FormGroup} from '@angular/forms';
import {Book} from "../../models/book";
import {User} from "../../models/user";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private URL = 'http://localhost:9000/api/security';


  constructor(private http: HttpClient) {
  }

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.URL + '/login', credentials, httpOptions);
  }

  saveUser(form: FormGroup): Observable<FormGroup> {
    return this.http.post<FormGroup>(this.URL + '/addUser', form.value);
  }

  updateUser(form: FormGroup): Observable<FormGroup> {
    console.log("form update security:", form.value);
    return this.http.put<FormGroup>(this.URL + '/updateUser', form.value);
  }

  deleteUser(id: any) {
    return this.http.delete<User>( this.URL + '/deleteUser', {
      params: new HttpParams()
        .set('id', id)
    });
  }
}
