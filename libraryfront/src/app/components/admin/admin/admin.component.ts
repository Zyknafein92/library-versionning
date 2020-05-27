import { Component, OnInit } from '@angular/core';
import {LibraryService} from '../../../../services/library.service';
import {BookService} from '../../../../services/book.service';
import {UserService} from '../../../../services/user.service';
import {Router} from '@angular/router';
import {BorrowService} from '../../../../services/borrow.service';
import {Library} from '../../../../models/library';
import {Borrow} from '../../../../models/borrow';
import {Book} from '../../../../models/book';
import {User} from '../../../../models/user';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  librarys: Array<Library>;
  users: Array<User>;
  books: Array<Book>;
  borrows: Array<Borrow>;

  constructor(private libraryService: LibraryService, private bookService: BookService, private userService: UserService, private borrowService: BorrowService, private router: Router) {
  }

  ngOnInit() {
    this.initLibrarys();
    this.initUsers();
    this.initBooks();
    this.initBorrows();
  }

  private initLibrarys() {
    this.libraryService.getLibrarys().subscribe(data => this.librarys = data)
  }

  private initUsers() {
    this.userService.getUsers().subscribe(data => this.users = data);
  }

  private initBooks() {
    this.bookService.getBooks().subscribe(data => this.books = data)
  }

  private initBorrows() {
    this.borrowService.getBorrows().subscribe(data => this.borrows = data)
  }

  // BOOK

  addBook() {
    this.router.navigate(['edit-book']);
  }

  updateBook(id: number) {
    this.router.navigate(['edit-book'], {queryParams: {id}});
  }

  deleteBook(id: number) {
    this.bookService.deleteBook(id).subscribe(
      next => {
        this.initBooks()
      }
    );
  }

  // LIBRARY

  addLibrary() {
    this.router.navigate(['edit-library']);
  }

  updateLibrary(id: number) {
    this.router.navigate(['edit-library'], {queryParams: {id}});
  }

  deleteLibrary(id: any) {
    this.libraryService.deleteLibrary(id).subscribe(
      next => {
        this.initLibrarys()
      })
  }

  //BORROW


  addBorrow() {
    this.router.navigate(['edit-borrow']);
  }

  updateBorrow(id: number) {
    this.router.navigate(['edit-borrow'], {queryParams: {id}});
  }

  deleteBorrow(id: number) {
    let borrow = this.borrows.find(value => value.id == id);
    let book = this.books.find(value => ""+value.id == borrow.bookID);
    console.log("book to find: ", book);
    this.bookService.updateBookStatus(book).subscribe(next => {
      this.initBooks();
    });

    this.borrowService.deleteBorrow(id).subscribe(
      next => {
        this.initBorrows();
      });
  }

}
