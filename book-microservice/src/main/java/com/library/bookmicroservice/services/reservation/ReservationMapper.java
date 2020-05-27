package com.library.bookmicroservice.services.reservation;

import com.library.bookmicroservice.model.Reservation;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReservationMapper {

    Reservation reservatioDtoToReservation(ReservationDTO reservatioDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateReservationFromReservationDTO(ReservationDTO reservationDTO, @MappingTarget Reservation reservation);
}
