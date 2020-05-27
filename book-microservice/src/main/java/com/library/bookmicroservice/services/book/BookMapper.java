package com.library.bookmicroservice.services.book;

import com.library.bookmicroservice.model.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookMapper {

    Book bookDtoToBook(BookDTO bookDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateBookFromBookDTO(BookDTO bookDTO, @MappingTarget Book book);
}
