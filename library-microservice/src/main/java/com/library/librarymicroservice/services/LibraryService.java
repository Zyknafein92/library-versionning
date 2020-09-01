package com.library.librarymicroservice.services;

import com.library.librarymicroservice.model.Library;

import java.util.List;
import java.util.Optional;

public interface LibraryService {

    List<Library> getLibrarys();

    Optional<Library> getLibrary(Long id);

    Library createLibrary(LibraryDTO libraryDTO);

    Library updateLibrary(LibraryDTO libraryDTO);

    Long deleteLibrary(Long id);

}
