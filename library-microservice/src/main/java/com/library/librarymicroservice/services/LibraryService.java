package com.library.librarymicroservice.services;

import com.library.librarymicroservice.model.Library;

import java.util.List;

public interface LibraryService {

    List<Library> getLibrarys();

    Library getLibrary(Long id);

    Library createLibrary(LibraryDTO libraryDTO);

    void updateLibrary(LibraryDTO libraryDTO);

    void deleteLibrary(Long id);

}