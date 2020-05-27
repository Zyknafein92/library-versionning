package com.library.librarymicroservice.services;

import com.library.librarymicroservice.exceptions.LibraryNotFoundException;
import com.library.librarymicroservice.model.Library;
import com.library.librarymicroservice.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Library getLibrary(Long id) {
        return libraryRepository.getOne(id);
    }

    @Override
    public Library createLibrary(LibraryDTO libraryDTO) {
        Library library = libraryMapper.librabyDtoToLibrary(libraryDTO);
        return libraryRepository.save(library);
    }

    @Override
    public void updateLibrary(LibraryDTO libraryDTO) {
        Library library = getLibrary(libraryDTO.getId());
        if(library == null) throw new LibraryNotFoundException(" La bibliothèque n'existe pas.");
        libraryMapper.updateLibraryFromLibraryDTO(libraryDTO,library);
        libraryRepository.save(library);

    }

    @Override
    public void deleteLibrary(Long id) { libraryRepository.deleteById(id); }

}
