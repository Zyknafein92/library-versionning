import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {FormGroup} from '@angular/forms';
import {Observable} from 'rxjs';
import {Book} from '../models/book';

@Injectable({
  providedIn: 'root'
})

export class BookService {

  private bookURL = 'http://localhost:9000/book-microservice/api/book';

  constructor(private http: HttpClient) {}

  getBook(id: any) : Observable<Book> {
    return this.http.get<Book>(this.bookURL + '/getBook', {
      params: new HttpParams()
        .set('id', id),
    });
  }

  searchBook(criteria: string) : Observable<Array<Book>> {
  return this.http.get<Array<Book>>(this.bookURL + '/searchBook', {
    params: new HttpParams()
      .set('criteria', criteria)
  })
  }

  getBooks() : Observable<Array<Book>> {
    return this.http.get<Array<Book>>(this.bookURL + '/getAll');
  }

  getBooksByDistinctTitle() : Observable<Array<Book>> {
    return this.http.get<Array<Book>>(this.bookURL + '/getAllDistinctTitle');
  }

  saveBook(form: FormGroup): Observable<FormGroup> {
    console.log("book form: " , form.value);
    return this.http.post<FormGroup>(this.bookURL + '/addBook', form.value);
  }

  updateBook(form: FormGroup): Observable<Book> {
    return this.http.put<Book>(this.bookURL + '/updateBook', form.value);
  }

  updateBookStatus(book: Book): Observable<Book> {
    console.log("book to update status", book);
    return this.http.put<Book>(this.bookURL + '/updateBookStatus', book);
  }

  deleteBook(idBook: any): Observable<{}> {
    console.log('delete id: ', idBook);
    return this.http.delete<Book>( this.bookURL + '/deleteBook', {
      params: new HttpParams()
        .set('id', idBook)
    });
  }

}
