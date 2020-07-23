import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {UserService} from '../../../../services/user.service';
import {User} from '../../../../models/user';
import {TokenStorageService} from '../../../../services/security/token-storage.service';
import {Router} from '@angular/router';
import {BorrowService} from "../../../../services/borrow.service";
import {BookService} from "../../../../services/book.service";
import {Borrow} from "../../../../models/borrow";
import {Book} from "../../../../models/book";
import {AuthService} from "../../../../services/security/auth.service";
import {ReservationService} from "../../../../services/reservation.service";
import {Reservation} from "../../../../models/reservation";

@Component({
    selector: 'app-my-profil',
    templateUrl: './my-profil.component.html',
    styleUrls: ['./my-profil.component.css']
})
export class MyProfilComponent implements OnInit {

    forms: FormGroup;
    user: User;
    reservations : Array<Reservation>
    borrows : Array<Borrow>;
    books : Array<Book>;
    bookReservation : Book;
    messageError: string;


    constructor(private userService: UserService, private token: TokenStorageService, private router:Router, private borrowService:BorrowService, private bookService: BookService, private authService: AuthService, private reservationService: ReservationService) {
    }

    ngOnInit() {
        this.initProfil(this.token);
        this.initBook();
        this.initReservations();
    }

    private initProfil(token: TokenStorageService){
        this.userService.getProfil(this.token.getEmail()).subscribe(
            res => {
                this.user = res;
                this.initBorrow();
                console.log("user ", res.email)
            }
        );
    }

    private initBorrow() {
        this.borrowService.getBorrowsByUserID(this.user.id).subscribe(
            data => {
                this.borrows = data;
                this.borrows.forEach(borrow => {
                    this.books.filter(book => ('' +book.id) == borrow.bookID).forEach(book => borrow.bookID = book.title)
                })
            });
    }

    private initReservations() {
        this.reservationService.getReservationsByUserEmail(this.token.getEmail()).subscribe(
            data => {
                this.reservations = data;
            }
        )
    }

    refreshUser() {
        this.initProfil(this.token);
    }

    deleteUser(user: User) {
        this.userService.deleteUser(this.user.id).subscribe(
            response => {}),
            err => {
                console.log("error: ", err.error.message);
            };

        this.authService.deleteUser(this.user.id).subscribe(
            response => {
                this.token.signOut();
                this.router.navigate(['/home']);
            }),
            err => {
                console.log("error: ", err.error.message);
            };


    }

    private initBook() {
        this.bookService.getBooks().subscribe(
            data => {
                this.books = data;
            }
        )
    }

    extendBorrow(id: number) {
        this.borrowService.updateBorrowStatus(id).subscribe( res => {
            this.initBorrow();
        },
            err => {
                console.log('Error: ', err.error.message);
                this.messageError = err.error.message;
        });
    }

    deleteReservation(reservation: Reservation) {
        let bookID = reservation.bookID;
        this.reservationService.deleteReservation(reservation.id).subscribe(
            response => {
                console.log('reservation to delete: ', response);
                this.bookService.getBook(bookID).subscribe(data => {
                    this.bookReservation = data
                    this.reservationService.updateBookReservation(this.bookReservation).subscribe(
                        response => {
                            console.log('book to update: ', response);
                            this.initReservations();
                        },
                        err => {
                            console.log('Error: ', err.error.message);
                            this.messageError = err.error.message;
                        });
                });
                this.initReservations();
            });
    }

}




