package com.library.bookmicroservice.repository;

import com.library.bookmicroservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select distinct on (b.title) b.* from Book b " +
            "where lower(b.title) like lower(concat('%', :criteria, '%')) " +
            "or lower(b.author) like lower(concat('%',:criteria, '%')) " +
            "or lower(b.gender) like lower(concat('%',:criteria, '%'))", nativeQuery = true)
    List<Book> searchBook(@Param("criteria") String criteria);

    @Query(value = "select distinct on (b.title) b.* from Book b " ,nativeQuery = true)
    List<Book> findDistinctByTitle();
}
