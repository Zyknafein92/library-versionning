import { Component, OnInit } from '@angular/core';
import {Book} from '../../../../models/book';
import {LibraryService} from '../../../../services/library.service';
import {ActivatedRoute, Router} from '@angular/router';
import {BookService} from '../../../../services/book.service';
import {Library} from '../../../../models/library';

@Component({
  selector: 'app-search-book',
  templateUrl: './search-book.component.html',
  styleUrls: ['./search-book.component.css']
})
export class SearchBookComponent implements OnInit {

  book : Book;
  searchText: string;
  books: Array<Book>;
  librarys: Array<Library>;


  constructor(private libraryService: LibraryService, private bookService: BookService, private router: Router) { }

  ngOnInit() {
    this.initLibrarys();
  }

  private initLibrarys() {
    this.libraryService.getLibrarys().subscribe(
      data => {
        this.librarys = data;
        this.initBooks();
        console.log('data : ', data);
      },
      err => {
        console.log("error: ", err.error.message);
      });
  }

  private initBooks() {
    this.bookService.getBooksByDistinctTitle().subscribe(
      data => {
        this.books = data;
          console.log('data : ', data);
          this.books.forEach(book => {
        this.librarys.filter(library => book.libraryID == ('' + library.id))
          .forEach(library => book.libraryName = library.name)
      })
  },
      err => {
        console.log("error: ", err.error.message);
      });

  }


  viewBook(id: number) {
    this.router.navigate(['book'], {queryParams: {id}});
  }

  searchBook(criteria: string) {
    this.bookService.searchBook(criteria).subscribe(
      data => {
        this.books = data;
        console.log('data: ', data);
        this.books.forEach(book => {
          this.librarys.filter(library => book.libraryID == ('' + library.id))
            .forEach(library => book.libraryName = library.name)
        })
      },
      err => {
        console.log("error: ", err.error.message);
      });
  }

}
