package com.library.bookmicroservice.services.book;

import com.library.bookmicroservice.exceptions.BookNotFoundException;
import com.library.bookmicroservice.model.Book;
import com.library.bookmicroservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class BookServiceImplTest {

    @Mock
    BookRepository repository;

    @Mock
    BookMapperImpl mapper;

    @InjectMocks
    BookServiceImpl service;

    Book book;
    Book book2;
    BookDTO bookDTO;

    List<Book> bookList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        book = Book.builder().id((long) 1).title("testBook").author("testBook").description("testBook").editor("testBook").parution(new Date()).gender("testBook").picture("testBook").avaible(true).LibraryID("1").build();
        book2 = Book.builder().id((long) 2).title("test2Book").author("test2Book").description("tes2tBook").editor("test2Book").parution(new Date()).gender("test2Book").picture("test2Book").avaible(true).LibraryID("2").build();
        bookDTO = BookDTO.builder().id((long) 1).title("testBook").author("testBook").description("testBook").editor("testBook").parution(new Date()).gender("testBook").picture("testBook").avaible(true).libraryID("1").build();
    }

    @Test
    void getBooks_GivenList_ReturnList() {
        bookList.add(book);
        bookList.add(book2);

        when(repository.findAll()).thenReturn(bookList);

        assertThat(service.getBooks().size()).isEqualTo(2);
    }

    @Test
    void getBooks_GivenEmpyList_ReturnEmptyList() {
        when(repository.findAll()).thenReturn(bookList);

        assertThat(service.getBooks().size()).isEqualTo(0);
    }

    @Test
    void getBooksDistinctsTitle_GivenList_ReturnList() {
        bookList.add(book);

        when(repository.findDistinctByTitle()).thenReturn(bookList);

        assertThat(service.getBooksDistinctsTitle().size()).isEqualTo(1);
        assertThat(service.getBooksDistinctsTitle().get(0).getTitle()).isEqualTo("testBook");
    }

    @Test
    void searchBooks_GivenList_ReturnList() {
        bookList.add(book);
        when(repository.searchBook("testBook")).thenReturn(bookList);
        assertThat(service.searchBooks("testBook").size()).isEqualTo(1);
    }

    @Test
    void getBook_GivenBook_ReturnBook() {
        when(repository.getOne((long) 1)).thenReturn(book);
        assertThat(service.getBook((long) 1)).isEqualTo(book);
    }

    @Test
    void createBook_GivenBook_ReturnBook() {
        when(repository.save(book)).thenReturn(book);
        when(mapper.bookDtoToBook(bookDTO)).thenReturn(book);

        assertThat(service.createBook(bookDTO)).isEqualTo(book);
    }

    @Test
    void updateBook_GivenNull_ReturnNull() {
        book = null;
        when(repository.getOne((long)1)).thenReturn(book);
        BookNotFoundException bookNotFoundException = assertThrows( BookNotFoundException.class, () -> service.updateBook(bookDTO));
        assertThat(bookNotFoundException.getMessage()).isEqualTo("Le livre recherché n'a pas été trouvé");
    }

    @Test
    void deleteBook() {
    }
}
