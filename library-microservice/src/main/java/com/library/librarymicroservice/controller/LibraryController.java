package com.library.librarymicroservice.controller;

import com.library.librarymicroservice.model.Library;
import com.library.librarymicroservice.services.LibraryDTO;
import com.library.librarymicroservice.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping(value = "/api/library/getAll")
    public ResponseEntity<List<Library>> getLibrarys() {
        List<Library> libraries = libraryService.getLibrarys();
        if(libraries == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(libraries, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/library/getLibrary", method = RequestMethod.GET)
    public ResponseEntity<Optional<Library>> getLibrary(@RequestParam(name = "id", defaultValue = "")  String id){
        Optional<Library> library = libraryService.getLibrary(Long.valueOf(id));
        if(!library.isPresent()) throw new ResponseStatusException(NOT_FOUND, "La bibliothèque n'existe pas.");
        return new ResponseEntity<>(library, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/library/addLibrary", method = RequestMethod.POST)
    public ResponseEntity<Library> createLibrary(@Valid @RequestBody LibraryDTO libraryDTO){
            Library library = libraryService.createLibrary(libraryDTO);
            return new ResponseEntity<>(library, HttpStatus.CREATED);
    }

    @RequestMapping(value ="/api/library/updateLibrary", method = RequestMethod.PUT)
    public ResponseEntity<Library> updateLibrary(@Valid @RequestBody LibraryDTO libraryDTO){
        Library library = libraryService.updateLibrary(libraryDTO);
        return new ResponseEntity<>(library, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/library/deleteLibrary", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteLibrary(@RequestParam(name = "id", defaultValue = "")  String id){
       Optional<Library> library = libraryService.getLibrary(Long.valueOf(id));
        if(!library.isPresent()) throw new ResponseStatusException(NOT_FOUND, "La bibliothèque n'existe pas.");
        libraryService.deleteLibrary(library.get().getId());
        return new ResponseEntity<>(library.get().getId(), HttpStatus.OK);
    }
}
