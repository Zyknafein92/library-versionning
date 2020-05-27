import {Component, Input, OnInit} from '@angular/core';
import {BookService} from '../../../../services/book.service';
import {Book} from '../../../../models/book';
import {ActivatedRoute, Router} from '@angular/router';
import {LibraryService} from '../../../../services/library.service';
import {Library} from '../../../../models/library';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {

  book: Book;
  forms: FormGroup;
  librarys: Array<Library>;

  constructor(private bookService: BookService, private libraryService: LibraryService, private router: Router, private formBuilder: FormBuilder, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.initLibrarys();
    this.initForm();
    this.activatedRoute.queryParams.subscribe(
          (params) => {
            const id = params['id'];
            if (id) {
              this.patchValue(id);
            }
      })
  }

  initForm() {
    this.forms = this.formBuilder.group({
      id: [''],
      title: ['', Validators.required],
      author: ['', Validators.required],
      gender: ['', Validators.required],
      description: ['', Validators.required],
      parution:['', Validators.required],
      editor: ['', Validators.required],
      avaible: ['', Validators.required],
      picture: ['', Validators.required],
      libraryID: ['', Validators.required],
    })
  }

  initLibrarys() {
    this.libraryService.getLibrarys().subscribe(
      data => this.librarys = data
    )
  }

  onSubmit() {
    if (!this.book || this.book.id == null) {
      this.bookService.saveBook(this.forms).subscribe(
        next => this.router.navigate(['admin'])
      )
    } else {
      this.bookService.updateBook(this.forms).subscribe(
        next => this.router.navigate(['admin'])
      )
    }
  }

  private patchValue(id) {
    console.log("id: ", id);
    this.bookService.getBook(id).subscribe(
      data => {
        this.book = data;
        console.log("book to search:", data);
        this.forms.patchValue({
          id: id,
          title: data.title,
          author: data.author,
          gender: data.gender,
          description: data.description,
          parution: data.parution,
          editor: data.editor,
          avaible: data.avaible,
          picture: data.picture,
          libraryID: data.libraryID,
        });
        console.log("forms :", this.forms.value);
      });
  }
}
