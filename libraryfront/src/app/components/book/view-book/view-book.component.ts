import {Component, Input, OnInit} from '@angular/core';
import {Book} from '../../../../models/book';
import {BookService} from '../../../../services/book.service';
import {ActivatedRoute, Router} from '@angular/router';
import {LibraryService} from '../../../../services/library.service';
import {Library} from '../../../../models/library';

@Component({
  selector: 'app-view-book',
  templateUrl: './view-book.component.html',
  styleUrls: ['./view-book.component.css']
})
export class ViewBookComponent implements OnInit {

  book: Book;
  books: Array<Book>;
  librarys: Array<Library>;

  constructor(private bookService: BookService, private route:Router, private activatedRoute: ActivatedRoute, private libraryService: LibraryService) { }

  ngOnInit(){
    this.initLibrarys();
    this.initBook();
  }

  initBook() {
    this.activatedRoute.queryParams.subscribe(
      (params) => {
        const id = params['id'];
        if (id) {
          this.bookService.getBook(id).subscribe( data => {
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

}
