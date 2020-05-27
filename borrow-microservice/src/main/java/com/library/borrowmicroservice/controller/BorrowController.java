package com.library.borrowmicroservice.controller;

import com.library.borrowmicroservice.model.Borrow;
import com.library.borrowmicroservice.services.BorrowDTO;
import com.library.borrowmicroservice.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Borrow> getBorrow(@RequestParam(name = "id", defaultValue = "")  String id) {
        Borrow borrow = borrowService.getBorrow(Long.valueOf(id));
        if(borrow == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(borrow, HttpStatus.OK);
    }

    @PostMapping(value="/api/borrow/addBorrow")
    public ResponseEntity<Borrow> createBorrow(@RequestBody BorrowDTO borrowDTO) {
        Borrow borrow = borrowService.createBorrow(borrowDTO);
        return new ResponseEntity<>(borrow,HttpStatus.OK);
    }


    @PutMapping(value="/api/borrow/updateBorrow")
    public ResponseEntity<Void> updateBorrow(@RequestBody BorrowDTO borrowDTO) {
        borrowService.updateBorrow(borrowDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value="/api/borrow/updateBorrowStatus")
    public ResponseEntity<Void> updateBorrowExtendStatus(@RequestParam(name = "id") String id) {
        if(id != null && !id.isEmpty()){
            borrowService.updateBorrowExtendStatus(Long.valueOf(id));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value="/api/borrow/deleteBorrow")
    public ResponseEntity<Void> deleteBorrow(@RequestParam(name = "id", defaultValue = "")  String id) {
        Borrow borrow = borrowService.getBorrow(Long.valueOf(id));
        if(borrow == null) return ResponseEntity.noContent().build();
        borrowService.deleteBorrow(borrow.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
