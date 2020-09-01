package com.library.borrowmicroservice.services;

import com.library.borrowmicroservice.batch.DatabaseConnect;
import com.library.borrowmicroservice.exceptions.BorrowNotFoundException;
import com.library.borrowmicroservice.exceptions.BorrowRulesException;
import com.library.borrowmicroservice.model.Borrow;
import com.library.borrowmicroservice.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Optional<Borrow> getBorrow(Long id) {
        return borrowRepository.findById(id);
    }

    @Override
    public Borrow createBorrow(BorrowDTO borrowDTO) {
        borrowDTO.setDateStart(LocalDateTime.now());
        borrowDTO.setDateEnd(LocalDateTime.now().plusDays(28));
        borrowDTO.setDateExtend(LocalDateTime.now().plusDays(56));
        borrowDTO.setIsExtend(false);
        Borrow borrow = borrowMapper.borrowDtoToBorrow(borrowDTO);
        DatabaseConnect.updateBookStatusNotAvaible(borrowDTO.getBookID());
        return borrowRepository.save(borrow);
    }

    @Override
    public Borrow updateBorrow(BorrowDTO borrowDTO) {
        Optional<Borrow> borrowOptional = getBorrow(borrowDTO.getId());
        Borrow borrow = null;
        if(borrowOptional.isPresent()) {
            borrow = new Borrow(
                    borrowOptional.get().getId(),
                    borrowOptional.get().getUserID(),
                    borrowOptional.get().getBookID(),
                    borrowOptional.get().getDateStart(),
                    borrowOptional.get().getDateEnd(),
                    borrowOptional.get().getDateExtend(),
                    borrowOptional.get().getIsExtend()
            );
            borrowMapper.updateBorrowFromBorrowDTO(borrowDTO,borrow);
            borrowRepository.save(borrow);
            return borrow;
        } else throw new BorrowNotFoundException("L'emprunt recherché n'a pas été trouvé");
    }

    @Override
    public Borrow updateBorrowExtendStatus(Long id) {
        Date today = new Date();
        Optional<Borrow> borrowOptional = getBorrow(id);
        Borrow borrow = null;

        if(borrowOptional.isPresent()) {
            borrow = new Borrow(
                    borrowOptional.get().getId(),
                    borrowOptional.get().getUserID(),
                    borrowOptional.get().getBookID(),
                    borrowOptional.get().getDateStart(),
                    borrowOptional.get().getDateEnd(),
                    borrowOptional.get().getDateExtend(),
                    borrowOptional.get().getIsExtend()
            );
        } else throw new BorrowNotFoundException("L'emprunt recherché n'a pas été trouvé");

        borrow.setIsExtend(today.before(borrow.getDateEnd()));

        if(borrow.getIsExtend()) {
            borrowRepository.save(borrow);
            return borrow;
        } else {
            throw new BorrowRulesException (
                    "Vous ne pouvez plus prolonger votre emprunt après la date butoir. " +
                            "Merci de restituer l'ouvrage le plus rapidement possible.");
        }

    }

    @Override
    public Long deleteBorrow(Long id) {
            borrowRepository.deleteById(id);
            return id;
    }

}
