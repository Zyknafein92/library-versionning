import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { SearchBookComponent } from './components/book/search-book/search-book.component';
import { CreateUserComponent } from './components/user/create-user/create-user.component';
import { UpdateUserComponent } from './components/user/update-user/update-user.component';
import { MyProfilComponent } from './components/user/my-profil/my-profil.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { ViewLibrarysComponent } from './components/library/view-librarys/view-librarys.component';
import { LoginComponent } from './components/auth/login/login.component';
import {httpInterceptorProviders} from '../services/security/auth-interceptor.service';
import { ViewBookComponent } from './components/book/view-book/view-book.component';
import { MenuComponent } from './components/menu/menu.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { BookEditComponent } from './components/book/book-edit/book-edit.component';
import { LibraryEditComponent } from './components/library/library-edit/library-edit.component';
import { BorrowEditComponent } from './components/book/borrow-edit/borrow-edit.component';
import { AdminComponent } from './components/admin/admin/admin.component';




@NgModule({
  declarations: [
    AppComponent,
    SearchBookComponent,
    CreateUserComponent,
    UpdateUserComponent,
    MyProfilComponent,
    HomeComponent,
    ViewLibrarysComponent,
    LoginComponent,
    ViewBookComponent,
    MenuComponent,
    PageNotFoundComponent,
    BookEditComponent,
    LibraryEditComponent,
    BorrowEditComponent,
    AdminComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    RouterModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
