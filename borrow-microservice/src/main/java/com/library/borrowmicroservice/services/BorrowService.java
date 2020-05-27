package com.library.borrowmicroservice.services;

import com.library.borrowmicroservice.model.Borrow;

import java.util.List;

public interface BorrowService {

    List<Borrow> getAllBorrows();

    List<Borrow> getOutDatedBorrow();

    List<Borrow> getMyBorrows(String id);

    Borrow getBorrow(Long id);

    Borrow createBorrow(BorrowDTO borrowDTO);

    void updateBorrow(BorrowDTO borrowDTO);

    void updateBorrowExtendStatus(Long id);

    void deleteBorrow(Long id);

}
