import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Reservation} from "../models/reservation";
import {Book} from "../models/book";
import {FormGroup} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private reservationURL = 'http://localhost:9000/book-microservice/api/reservation';

  constructor(private http: HttpClient) { }

  getReservation(id: any) : Observable<Reservation> {
    return this.http.get<Book>(this.reservationURL + '/reservation', {
      params: new HttpParams()
          .set('id', id),
    });
  }

  getReservationsByBookID(id: any) : Observable<Array<Reservation>> {
    return this.http.get<Array<Book>>(this.reservationURL + '/reservations', {
      params: new HttpParams()
          .set('id', id)
    })
  }

  createReservation(reservation: Reservation): Observable<FormGroup> {
    console.log("reservation value: " , reservation);
    return this.http.post<FormGroup>(this.reservationURL + '/addReservation', reservation);
  }

  updateReservation(reservation: Reservation): Observable<Reservation> {
    return this.http.put<Reservation>(this.reservationURL + '/updateReservation', reservation);
  }

  deleteReservation(id : any): Observable<{}> {
    console.log('delete id: ', id);
    return this.http.delete<Book>( this.reservationURL + '/deleteReservation', {
      params: new HttpParams()
          .set('id', id)
    });
  }


}
