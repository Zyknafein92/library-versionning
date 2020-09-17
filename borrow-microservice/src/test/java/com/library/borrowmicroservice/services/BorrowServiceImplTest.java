package com.library.borrowmicroservice.services;

import com.library.borrowmicroservice.exceptions.BorrowNotFoundException;
import com.library.borrowmicroservice.exceptions.BorrowRulesException;
import com.library.borrowmicroservice.model.Borrow;
import com.library.borrowmicroservice.repository.BorrowRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BorrowServiceImplTest {

    @InjectMocks
    BorrowServiceImpl service;

    @Mock
    BorrowRepository repository;

    @Mock
    BorrowMapperImpl mapper;

    Borrow borrow1, borrow2;

    BorrowDTO borrowDTO;

    List<Borrow> borrowList = new ArrayList<>();

    SimpleDateFormat formater;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        borrow1 = Borrow.builder().id((long)1).bookID("1").userID("1").dateStart(null).dateEnd(null).dateExtend(null).isExtend(false).build();
        borrowDTO = BorrowDTO.builder().id((long)1).bookID("1").userID("1").dateStart(null).dateEnd(null).dateExtend(null).isExtend(false).build();
        borrow2 = Borrow.builder().id((long)2).bookID("2").userID("2").dateStart(null).dateEnd(null).dateExtend(null).isExtend(false).build();

    }

    @Test
    void getAllBorrows_GiveList_ReturnList() {
        borrowList.add(borrow1);
        borrowList.add(borrow2);

        when(repository.findAll()).thenReturn(borrowList);

        assertThat(service.getAllBorrows().size()).isEqualTo(2);
    }

    @Test
    void getOutDatedBorrow_GivenList_ReturnList() throws ParseException {
        Date start = new SimpleDateFormat("dd/MM/yyyy").parse("10/12/2010");
        Date end = new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2010");

        borrow1.setDateStart(start);
        borrow1.setDateEnd(end);
        borrowList.add(borrow1);

        when(repository.findAllBorrowOutDated()).thenReturn(borrowList);

        assertThat(service.getOutDatedBorrow().size()).isEqualTo(1);
    }

    @Test
    void getMyBorrows_GivenList_ReturnList() {
        borrowList.add(borrow1);

        when(repository.findAllByUserID("1")).thenReturn(borrowList);

        assertThat(service.getMyBorrows("1").size()).isEqualTo(1);
    }

    @Test
    void getBorrow_GivenBorrow_ReturnBorrow() {
        when(repository.findById((long)1)).thenReturn(java.util.Optional.ofNullable(borrow1));

        assertThat(service.getBorrow((long) 1)).isEqualTo(java.util.Optional.ofNullable(borrow1));
    }


    @Test
    void createBorrow_GivenBorrowDTO_ReturnBorrow(){
        Borrow borrowTest =  Borrow.builder().id((long)1).bookID("1").userID("1").dateStart(java.sql.Date.valueOf(LocalDateTime.now().toLocalDate())).dateEnd(java.sql.Date.valueOf(LocalDateTime.now().plusDays(28).toLocalDate())).dateExtend(java.sql.Date.valueOf(LocalDateTime.now().plusDays(28).toLocalDate())).isExtend(false).build();

        when(mapper.borrowDtoToBorrow(borrowDTO)).thenReturn(borrowTest);
        service.createBorrow(borrowDTO);

        assertThat(borrowDTO.getDateStart().toLocalDate()).isEqualTo(LocalDateTime.now().toLocalDate());
        assertThat(borrowDTO.getDateEnd().toLocalDate()).isEqualTo(LocalDateTime.now().plusDays(28).toLocalDate());
        assertThat(borrowDTO.getDateExtend().toLocalDate()).isEqualTo(LocalDateTime.now().plusDays(56).toLocalDate());
        assertThat(borrowDTO.getIsExtend()).isEqualTo(false);
        assertThat(borrowDTO.getId()).isEqualTo(borrowTest.getId());
        assertThat(borrowDTO.getBookID()).isEqualTo(borrowTest.getBookID());
        assertThat(borrowDTO.getUserID()).isEqualTo(borrowTest.getUserID());
        assertThat(borrowDTO.getIsExtend()).isEqualTo(borrowTest.getIsExtend());
    }

    @Test
    void updateBorrow_GivenNull_ReturnException(){
        when(service.getBorrow((long)1)).thenReturn(java.util.Optional.empty());
        BorrowNotFoundException borrowNotFoundException = assertThrows(BorrowNotFoundException.class, () -> service.updateBorrow(borrowDTO));
        assertThat(borrowNotFoundException.getMessage()).isEqualTo("L'emprunt recherché n'a pas été trouvé");
    }


    @Test
    void updateBorrowExtendStatus_GivenBorrow_ReturnBorrow() throws ParseException {
        String sDate1="30/09/2020";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        borrow1.setDateEnd(date);
        when(service.getBorrow((long)1)).thenReturn(java.util.Optional.ofNullable(borrow1));

        assertThat(service.updateBorrowExtendStatus((long)1).getIsExtend()).isEqualTo(true);
    }

    @Test
    void updateBorrowExtendStatus_GivenNull_ReturnException() {
        when(service.getBorrow((long)1)).thenReturn(java.util.Optional.empty());
        BorrowNotFoundException borrowNotFoundException = assertThrows(BorrowNotFoundException.class, () -> service.updateBorrowExtendStatus((long)1));
        assertThat(borrowNotFoundException.getMessage()).isEqualTo("L'emprunt recherché n'a pas été trouvé");
    }

    @Test
    void updateBorrowExtendStatus_GivenBorrowExtend_ReturnException() {
        borrow1.setDateEnd(new Date());
        when(repository.findById((long) 1)).thenReturn(java.util.Optional.ofNullable(borrow1));
        BorrowRulesException borrowRulesException = assertThrows(BorrowRulesException.class, () -> service.updateBorrowExtendStatus((long)1));
        assertThat(borrowRulesException.getMessage()).isEqualTo("Vous ne pouvez plus prolonger votre emprunt après la date butoir. " +
                "Merci de restituer l'ouvrage le plus rapidement possible.");
    }


    @Test
    void deleteBorrow() {
        when(repository.findById((long) 1)).thenReturn(java.util.Optional.ofNullable(borrow1));
        Assertions.assertThat(service.deleteBorrow((long)1)).isEqualTo(1);
    }
}
