import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {CreateUserComponent} from './components/user/create-user/create-user.component';
import {MyProfilComponent} from './components/user/my-profil/my-profil.component';
import {SearchBookComponent} from './components/book/search-book/search-book.component';
import {ViewLibrarysComponent} from './components/library/view-librarys/view-librarys.component';
import {LoginComponent} from './components/auth/login/login.component';
import {ViewBookComponent} from './components/book/view-book/view-book.component';
import {PageNotFoundComponent} from './components/page-not-found/page-not-found.component';
import {AdminComponent} from './components/admin/admin/admin.component';
import {BookEditComponent} from './components/book/book-edit/book-edit.component';
import {LibraryEditComponent} from './components/library/library-edit/library-edit.component';
import {BorrowEditComponent} from './components/book/borrow-edit/borrow-edit.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full'},
  { path: 'home', component: HomeComponent},
  { path:  'search', component: SearchBookComponent},
  { path: 'book', component: ViewBookComponent},
  { path: 'create-user', component: CreateUserComponent },
  { path:  'my-profil', component: MyProfilComponent},
  { path:  'Login', component: LoginComponent},
  { path:  'contacts', component: ViewLibrarysComponent},
  { path: 'admin', component: AdminComponent},
  { path: 'edit-book', component: BookEditComponent},
  { path: 'edit-library', component: LibraryEditComponent},
  { path: 'edit-borrow', component: BorrowEditComponent},
  { path: '404', component: PageNotFoundComponent},
  { path : '**', redirectTo : '/404'},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
