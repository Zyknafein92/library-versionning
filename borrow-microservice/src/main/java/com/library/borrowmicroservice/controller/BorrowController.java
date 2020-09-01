package com.library.borrowmicroservice.controller;

import com.library.borrowmicroservice.model.Borrow;
import com.library.borrowmicroservice.services.BorrowDTO;
import com.library.borrowmicroservice.services.BorrowService;
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
public class BorrowController {

    @Autowired
    private BorrowService borrowService;


    @GetMapping(value = "/api/borrow/getAll")
    public List<Borrow> getBorrowById() {
        return borrowService.getAllBorrows();
    }

    @GetMapping(value= "/api/borrow/getMyBorrows")
    public List<Borrow> getMyBorrows(@RequestParam(name = "userID", defaultValue = "") String userID) {
        return borrowService.getMyBorrows(userID);
    }

    @GetMapping(value = "/api/borrow/getBorrow")
    public ResponseEntity<Optional<Borrow>> getBorrow(@RequestParam(name = "id", defaultValue = "")  String id) {
        Optional<Borrow> borrow = borrowService.getBorrow(Long.valueOf(id));
        if(!borrow.isPresent()) throw new ResponseStatusException(NOT_FOUND, "L'emprunt sélectionné n'a pas été trouvé");
        return new ResponseEntity<>(borrow, HttpStatus.OK);
    }

    @PostMapping(value="/api/borrow/addBorrow")
    public ResponseEntity<Borrow> createBorrow(@Valid @RequestBody BorrowDTO borrowDTO) {
        Borrow borrow = borrowService.createBorrow(borrowDTO);
        return new ResponseEntity<>(borrow,HttpStatus.CREATED);
    }


    @PutMapping(value="/api/borrow/updateBorrow")
    public ResponseEntity<Borrow> updateBorrow(@Valid @RequestBody BorrowDTO borrowDTO) {
        Borrow borrow = borrowService.updateBorrow(borrowDTO);
        return new ResponseEntity<>(borrow,HttpStatus.OK);
    }

    @PutMapping(value="/api/borrow/updateBorrowStatus")
    public ResponseEntity<Borrow> updateBorrowExtendStatus(@RequestParam(name = "id") String id) {
        Borrow borrow = borrowService.updateBorrowExtendStatus(Long.valueOf(id));
        return new ResponseEntity<>(borrow,HttpStatus.OK);
    }

    @DeleteMapping(value="/api/borrow/deleteBorrow")
    public ResponseEntity<Long> deleteBorrow(@RequestParam(name = "id", defaultValue = "")  String id) {
        Optional<Borrow> borrow = borrowService.getBorrow(Long.valueOf(id));
        if(!borrow.isPresent()) throw new ResponseStatusException(NOT_FOUND, "L'emprunt sélectionné n'a pas été trouvé");
        borrowService.deleteBorrow(borrow.get().getId());
        return new ResponseEntity<>(borrow.get().getId(),HttpStatus.OK);
    }
}
