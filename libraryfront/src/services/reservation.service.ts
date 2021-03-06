import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Reservation} from "../models/reservation";
import {Book} from "../models/book";


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

  getReservations() : Observable<Array<Reservation>> {
    return this.http.get<Array<Reservation>>(this.reservationURL + 's');
  }

  getReservationsByBookID(id: any) : Observable<Array<Reservation>> {
    return this.http.get<Array<Reservation>>(this.reservationURL + '/reservations', {
      params: new HttpParams()
          .set('id', id)
    })
  }

  getReservationsByUserEmail(email: string) : Observable<Array<Reservation>> {
    return this.http.get<Array<Reservation>>(this.reservationURL + 's/email/' + email);
  }

  createReservation(reservation: Reservation): Observable<Reservation> {
    console.log("reservation value: " , reservation);
    return this.http.post<Reservation>(this.reservationURL + '/addReservation', reservation);
  }

  updateReservation(reservation: Reservation): Observable<Reservation> {
    return this.http.put<Reservation>(this.reservationURL + '/updateReservation', reservation);
  }

  updateBookReservation(book : Book): Observable<Reservation> {
    return this.http.put<Reservation>(this.reservationURL + '/checkReservation',  book);
  }


  deleteReservation(id : any): Observable<{}> {
    console.log('delete id: ', id);
    return this.http.delete<Reservation>( this.reservationURL + '/deleteReservation', {
      params: new HttpParams()
          .set('id', id)
    });
  }


}
