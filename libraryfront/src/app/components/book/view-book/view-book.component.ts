import {Component, OnInit} from '@angular/core';
import {Book} from '../../../../models/book';
import {BookService} from '../../../../services/book.service';
import {ActivatedRoute, Router} from '@angular/router';
import {LibraryService} from '../../../../services/library.service';
import {Library} from '../../../../models/library';
import {ReservationService} from "../../../../services/reservation.service";
import {Reservation} from "../../../../models/reservation";
import {TokenStorageService} from "../../../../services/security/token-storage.service";
import {User} from "../../../../models/user";


@Component({
    selector: 'app-view-book',
    templateUrl: './view-book.component.html',
    styleUrls: ['./view-book.component.css']
})
export class ViewBookComponent implements OnInit {

    user: User;
    book: Book;
    reservation: Reservation;
    reservations: Array<Reservation>;
    books: Array<Book>;
    librarys: Array<Library>;
    messageError: string;

    constructor(private bookService: BookService, private route: Router, private activatedRoute: ActivatedRoute, private libraryService: LibraryService, private reservationService: ReservationService, private tokenStorageService: TokenStorageService) {
    }

    ngOnInit() {
        this.initLibrarys();
        this.initBook();
        this.initReservations();
    }

    /* Init  */

    private initBook() {
        this.activatedRoute.queryParams.subscribe(
            (params) => {
                const id = params['id'];
                if (id) {
                    this.bookService.getBook(id).subscribe(data => {
                        this.book = data;
                        this.initListBook();
                    });
                }
            });
    }

    private initListBook() {
        this.bookService.getBooks().subscribe(
            data => {
                this.books = data.filter(b => b.title === this.book.title && b.author === this.book.author);
                this.books.forEach(book => {
                    this.librarys.filter(library => book.libraryID == ('' + library.id))
                        .forEach(library => book.libraryName = library.name)
                });
                console.log('data initListBook: ', this.books)
            });
    }

    private initLibrarys() {
        this.libraryService.getLibrarys().subscribe(
            data => {
                this.librarys = data;
                console.log('data : ', data);
            },
            err => {
                console.log("error: ", err.error.message);
            });
    }

    private initReservations() {
        this.reservationService.getReservations().subscribe(
            response => {
                this.reservations = response
                console.log("Init Reservations :", this.reservations);
            }
        )
    }

    /*  Reservation  */

    createReservation(book: Book) {
        this.book = book;
        this.reservation = new Reservation();
        this.reservation.date = null;
        this.reservation.bookID = this.book.id.toString();
        this.reservation.userEmail = this.tokenStorageService.getEmail();
        this.reservation.bookTitle = this.book.title;

        this.reservationService.createReservation(this.reservation).subscribe(
            response => {
                console.log('reservation to create: ', response);
                this.initReservations();
                this.bookService.getBook(book.id).subscribe(data => {
                    this.book = data;
                    this.initListBook();
                });
            },
            err => {
                console.log('Error: ', err.error.message);
                this.messageError = err.error.message;
            });
    }

    deleteReservation(book: Book) {
        let toDelete = this.reservations.find(reservation => reservation.userEmail === this.tokenStorageService.getEmail() && reservation.bookTitle == book.title && reservation.bookID == (''+ book.id));

        this.reservationService.deleteReservation(toDelete.id).subscribe(
            response => {
                console.log('reservation to delete: ', response);
                this.reservationService.updateBookReservation(book).subscribe(
                    response => {
                        console.log('book to update: ', response);
                        this.initListBook();
                        this.initReservations();
                    },
                    err => {
                        console.log('Error: ', err.error.message);
                        this.messageError = err.error.message;
                    });
                this.bookService.getBook(book.id).subscribe(data => {
                    this.book = data;
                });
            },
            err => {
                console.log('Error: ', err.error.message);
                this.messageError = err.error.message;
            });
    }

    checkBookDisponibility(book: Book) {
        return (this.reservations.filter(reservation => reservation.bookID == (book.id + '')).length <= 1);
    }

    checkAvaibleReservation(book: Book) {
        let check = this.reservations.find(reservation => reservation.userEmail === this.tokenStorageService.getEmail() && reservation.bookTitle == book.title);
        if (check != null) return false;
        else return true;
    }

    checkMyReservation(book: Book) {
        let check = this.reservations.find(reservation => reservation.userEmail === this.tokenStorageService.getEmail() && reservation.bookTitle == book.title && reservation.bookID == (''+ book.id));
        console.log("CMR", check);
        if (check != null) return true;
        else return false;
    }
}
