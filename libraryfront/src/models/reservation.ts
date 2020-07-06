import {Book} from "./book";

export class Reservation {
    id?:number;
    date?:Date;
    userEmail?:string;
    bookID?:string;
    bookTitle?:string;
    reservationPosition?:number;
    bookReturn?:Date;
}
