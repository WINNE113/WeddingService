package com.wedding.backend.mapper;

import com.wedding.backend.dto.requestForQuotationService.BookingServiceDTO;
import com.wedding.backend.entity.RequestForQuotationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestForQuotationServiceMapper {
    BookingServiceDTO entityToDto(RequestForQuotationEntity booking);

    RequestForQuotationEntity dtoToEntity(BookingServiceDTO bookingServiceDTO);
}
