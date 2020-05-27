package com.library.librarymicroservice.services;

import com.library.librarymicroservice.model.Library;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LibraryMapper {

    Library librabyDtoToLibrary(LibraryDTO libraryDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateLibraryFromLibraryDTO(LibraryDTO libraryDTO, @MappingTarget Library library);
}
