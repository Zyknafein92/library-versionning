package com.library.librarymicroservice.services;

import com.library.librarymicroservice.exceptions.LibraryNotFoundException;
import com.library.librarymicroservice.model.Library;
import com.library.librarymicroservice.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    LibraryMapper libraryMapper;

    @Override
    public List<Library> getLibrarys() {
        return libraryRepository.findAll();
    }

    @Override
    public Optional<Library> getLibrary(Long id) { return libraryRepository.findById(id); }

    @Override
    public Library createLibrary(LibraryDTO libraryDTO) {
        Library library = libraryMapper.librabyDtoToLibrary(libraryDTO);
        return libraryRepository.save(library);
    }

    @Override
    public Library updateLibrary(LibraryDTO libraryDTO) {
        Optional<Library> libraryOptional = getLibrary(libraryDTO.getId());
        Library library = null;

        if (libraryOptional.isPresent()){
            library = new Library(
            libraryOptional.get().getId(),
            libraryOptional.get().getAddress(),
            libraryOptional.get().getName(),
            libraryOptional.get().getPhone()
            );
        }

        if(library == null) throw new LibraryNotFoundException(" La bibliothèque n'existe pas.");
        libraryMapper.updateLibraryFromLibraryDTO(libraryDTO,library);
        libraryRepository.save(library);
        return library;

    }

    @Override
    public Long deleteLibrary(Long id) {
        libraryRepository.deleteById(id);
        return id;
    }

}
