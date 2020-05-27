import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../../../services/user.service';
import {AuthService} from '../../../../services/security/auth.service';
import {Router} from '@angular/router';
import {User} from '../../../../models/user';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private userService: UserService, private authService: AuthService, private router: Router) { }

  forms: FormGroup;
  formsPassword: FormGroup
  password : String;

  @Input()
  user: User;

  private messageError: string;

  ngOnInit() {
    this.initform();
    this.initformPassword();
  }

  private initform() {
    this.forms = this.formBuilder.group(
      {
        id: this.user.id,
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        birthday: this.user.birthday,
        city: this.user.city,
        postalCode: this.user.postalCode,
        email: this.user.email,
        phone: this.user.phone,
        address: this.user.address,
      });
  }

  private initformPassword() {
    this.formsPassword = this.formBuilder.group(
      {
        id:  this.user.id,
        password: new FormControl(),
      }
    )
  }


  updateUser() {
    console.log(this.forms.value);
    this.userService.updateUser(this.forms)
      .subscribe(
        response => {
          this.userService.getProfil(this.forms.value.email);
        },
        err => {
          console.log('Error: ', err.error.message);
          this.messageError = err.error.message;
        });
  }

  updatePassword() {
    this.authService.updateUser(this.formsPassword).subscribe(
      response => {
        console.log('response: ', response);
      },
      err => {
        console.log('Error: ', err.error.message);
        this.messageError = err.error.message;
      });
  }


}
