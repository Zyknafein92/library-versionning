package com.library.bookmicroservice.services.book;

import com.library.bookmicroservice.exceptions.BookNotFoundException;
import com.library.bookmicroservice.model.Book;
import com.library.bookmicroservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Book> getBook(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> searchBooks(String criteria) {
        List<Book> searchResult = bookRepository.searchBook(criteria);
        return searchResult;
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book book = bookMapper.bookDtoToBook(bookDTO);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(BookDTO bookDTO) {
        Optional<Book> bookOptional = getBook(bookDTO.getId());
        Book book = null;

        if(bookOptional.isPresent()) {
            book = new Book(
                    bookOptional.get().getId(),
                    bookOptional.get().getTitle(),
                    bookOptional.get().getAuthor(),
                    bookOptional.get().getDescription(),
                    bookOptional.get().getEditor(),
                    bookOptional.get().getParution(),
                    bookOptional.get().getGender(),
                    bookOptional.get().getPicture(),
                    bookOptional.get().getAvaible(),
                    bookOptional.get().getLibraryID()
            );
        }

        if (book == null) throw new BookNotFoundException("Le livre recherché n'a pas été trouvé");
        bookMapper.updateBookFromBookDTO(bookDTO, book);
        bookRepository.save(book);
        return book;
    }

    @Override
    public Long deleteBook(Long id) {
        bookRepository.deleteById(id);
        return id;
    }

}
