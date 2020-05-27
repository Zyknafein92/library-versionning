package com.library.borrowmicroservice.services;

import com.library.borrowmicroservice.exceptions.BorrowNotFoundException;
import com.library.borrowmicroservice.model.Borrow;
import com.library.borrowmicroservice.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    BorrowRepository borrowRepository;
    @Autowired
    BorrowMapper borrowMapper;


    @Override
    public List<Borrow> getAllBorrows () {
        return borrowRepository.findAll();
    }

    public List<Borrow> getOutDatedBorrow(){
        return borrowRepository.findAllBorrowOutDated();
    }

    @Override
    public List<Borrow> getMyBorrows(String id) {
        return borrowRepository.findAllByUserID(id);
    }

    @Override
    public Borrow getBorrow(Long id) {
        return borrowRepository.getOne(id);
    }

    @Override
    public Borrow createBorrow(BorrowDTO borrowDTO) {
        borrowDTO.setDateStart(LocalDateTime.now());
        borrowDTO.setDateEnd(LocalDateTime.now().plusDays(28));
        borrowDTO.setDateExtend(LocalDateTime.now().plusDays(56));
        borrowDTO.setIsExtend(false);
        Borrow borrow = borrowMapper.borrowDtoToBorrow(borrowDTO);
        return borrowRepository.save(borrow);
    }

    @Override
    public void updateBorrow(BorrowDTO borrowDTO) {
        Borrow borrow = getBorrow(borrowDTO.getId());
        if(borrow == null) throw new BorrowNotFoundException("L'emprunt recherché n'a pas été trouvé");
        borrowMapper.updateBorrowFromBorrowDTO(borrowDTO,borrow);
        borrowRepository.save(borrow);
    }

    @Override
    public void updateBorrowExtendStatus(Long id) {
        Borrow borrow = getBorrow(id);
        borrow.setIsExtend(true);
        borrowRepository.save(borrow);
    }

    @Override
    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }
}
