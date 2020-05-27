import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Borrow} from '../models/borrow';
import {FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})

export class BorrowService {

  private borrowURL = 'http://localhost:9000/borrow-microservice/api/borrow';

  constructor(private http: HttpClient) { }

  getBorrows() {
    return this.http.get<Array<Borrow>>(this.borrowURL + '/getAll');
  }

  getBorrowsByUserID(userID: any) {
    return this.http.get<Array<Borrow>>(this.borrowURL + '/getMyBorrows' ,{
      params: new HttpParams()
        .set('userID', userID)
    });
  }

  getBorrow(idBorrow: string) {
    return this.http.get<Borrow>(this.borrowURL + '/getBorrow', {
      params: new HttpParams()
        .set('id', idBorrow),
    });
  }

  saveBorrow(form: FormGroup): Observable<FormGroup> {
    return this.http.post<FormGroup>(this.borrowURL + '/addBorrow', form.value);
  }

  updateBorrow(borrow: Borrow): Observable<Borrow> {
    return this.http.put<Borrow>(this.borrowURL + '/updateBorrow', borrow);
  }

  updateBorrowStatus(idBorrow : any) {
    console.log("id to update", idBorrow);
    return this.http.put<Borrow>(this.borrowURL + '/updateBorrowStatus', {}, {params:{id : idBorrow}})
  }

  deleteBorrow(idBorrow: any): Observable<{}> {
    return this.http.delete<Borrow>( this.borrowURL + '/deleteBorrow', {
      params: new HttpParams()
        .set('id', idBorrow)
    });
  }

}
