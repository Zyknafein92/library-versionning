package com.library.borrowmicroservice.services;

import com.library.borrowmicroservice.model.Borrow;

import java.util.List;
import java.util.Optional;

public interface BorrowService {

    List<Borrow> getAllBorrows();

    List<Borrow> getOutDatedBorrow();

    List<Borrow> getMyBorrows(String id);

    Optional<Borrow> getBorrow(Long id);

    Borrow createBorrow(BorrowDTO borrowDTO);

    Borrow updateBorrow(BorrowDTO borrowDTO);

    Borrow updateBorrowExtendStatus(Long id);

    Long deleteBorrow(Long id);

}
