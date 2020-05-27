package com.library.borrowmicroservice.services;

import com.library.borrowmicroservice.model.Borrow;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BorrowMapper {

    Borrow borrowDtoToBorrow(BorrowDTO borrowDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateBorrowFromBorrowDTO(BorrowDTO borrowDTO, @MappingTarget Borrow borrow);
}