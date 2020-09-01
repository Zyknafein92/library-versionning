package com.library.bookmicroservice.services.book;

import com.library.bookmicroservice.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getBooks();

    List<Book> getBooksDistinctsTitle();

    List<Book> searchBooks(String criteria);

    Optional<Book> getBook(Long id);

    Book createBook(BookDTO bookDTO);

    Book updateBook(BookDTO bookDTO);

    Long deleteBook(Long id);

}
