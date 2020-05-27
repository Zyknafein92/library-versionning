import { Component, OnInit } from '@angular/core';
import {BorrowService} from '../../../../services/borrow.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Borrow} from '../../../../models/borrow';
import {BookService} from '../../../../services/book.service';
import {Book} from '../../../../models/book';
import {User} from '../../../../models/user';
import {UserService} from '../../../../services/user.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-borrow-edit',
  templateUrl: './borrow-edit.component.html',
  styleUrls: ['./borrow-edit.component.css']
})
export class BorrowEditComponent implements OnInit {

  borrow: Borrow;
  forms: FormGroup;
  borrows: Array<Borrow>;
  books: Array<Book>;
  book: Book;
  users: Array<User>;


  constructor(private borrowService: BorrowService, private router: Router, private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute, private bookService: BookService, private userService: UserService) {
  }

  ngOnInit() {
    this.initBorrow();
    this.initBooks();
    this.initUsers();
    this.initForm();
    this.activatedRoute.queryParams.subscribe(
      (params) => {
        const id = params['id'];
        if (id) {
          this.patchValue(id);
        }
      })
  }

  private initBorrow() {
    this.borrowService.getBorrows().subscribe(
      data => {
        this.borrows = data;
      }
    )
  }

  private initForm() {
    this.forms = this.formBuilder.group({
      id: [''],
      userID: ['', Validators.required],
      bookID: ['', Validators.required],
      dateStart: [new Date(), Validators.required],
      dateEnd: [new Date(), Validators.required],
      dateExtend: [new Date(), Validators.required],
      isExtend: ['', Validators.required],
    })
  }

  private patchValue(id) {
    this.borrowService.getBorrow(id).subscribe(
      data => {
        this.borrow = data;
        console.log("borrow to search:", data);
        this.forms.patchValue({
          id: id,
          userID: data.userID,
          bookID: data.bookID,
          dateStart: data.dateStart,
          dateEnd: data.dateEnd,
          dateExtend: data.dateExtend,
          isExtend: data.isExtend,
        });
        console.log("forms :", this.forms.value);
      });
  }

  onSubmit() {
    if (!this.borrow || this.borrow.id == null) {
      this.borrowService.saveBorrow(this.forms).subscribe(
        () => {
          this.updateAvaibleStatus();
          this.bookService.getBooks().subscribe( res => {
            this.router.navigate(['admin']);
          });
        });
    } else {
      this.borrowService.updateBorrow(this.borrow).subscribe(
        () => {
          this.bookService.getBooks().subscribe( res => {
            this.router.navigate(['admin']);
          });
        });
    }
  }

  private initBooks() {
    this.bookService.getBooks().subscribe(
      data => {
        this.books = data.filter(value => value.avaible == true);
      }
    )
  }

  private initUsers() {
    this.userService.getUsers().subscribe(
      data => {
        this.users = data;
      }
    )
  }

  updateDateStart() {
    let date = new Date(this.forms.value.dateStart);
    date.setHours(12);
    this.borrow.dateStart = date;
  }

  updateDateEnd() {
    this.borrow.dateEnd = new Date(this.forms.value.dateEnd);
  }

  updateDateExtend() {
    this.borrow.dateExtend = new Date(this.forms.value.dateExtend);
  }


  updateAvaibleStatus() {
    this.bookService.getBook(this.forms.value.bookID).subscribe(
      data => {
        this.book = data;
        this.bookService.updateBookStatus(this.book).subscribe( res => {
          this.bookService.getBooks().subscribe();
        });
      });
  }
}

