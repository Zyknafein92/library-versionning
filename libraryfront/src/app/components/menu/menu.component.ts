import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../../../services/security/token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  authorities: string;
  tokenEmail: string;

  constructor(private token: TokenStorageService) { }

  ngOnInit() {
    this.authorities = this.token.getAuthorities();
    this.tokenEmail = this.token.getEmail();
  }

  logout() {
    this.token.signOut();
    window.location.reload();
  }
}
