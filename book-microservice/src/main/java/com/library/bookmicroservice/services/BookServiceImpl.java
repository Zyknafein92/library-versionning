package com.library.bookmicroservice.services;

import com.library.bookmicroservice.exceptions.BookNotFoundException;
import com.library.bookmicroservice.model.Book;
import com.library.bookmicroservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksDistinctsTitle() {
        return bookRepository.findDistinctByTitle();
    }

    @Override
    public List<Book> searchBooks(String criteria) {
        List<Book> searchResult = bookRepository.searchBook(criteria);
        if(searchResult == null) throw new BookNotFoundException("Aucun livre ne correspond à la recherche");
        return searchResult;
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book book = bookMapper.bookDtoToBook(bookDTO);
        return bookRepository.save(book);
    }

    @Override
    public void updateBookStatus(BookDTO bookDTO) {
        Book book = getBook(bookDTO.getId());

        if (book.getAvaible()) {
            book.setAvaible(false);
        } else {
            book.setAvaible(true);
        }
        bookRepository.save(book);
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        Book book = getBook(bookDTO.getId());
        if (book == null) throw new BookNotFoundException("Le livre recherché n'a pas été trouvé");
        bookMapper.updateBookFromBookDTO(bookDTO, book);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
