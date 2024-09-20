package com.wedding.backend.service.IService.requestForQuotation;

import com.wedding.backend.base.BaseResult;
import com.wedding.backend.base.BaseResultWithDataAndCount;
import com.wedding.backend.dto.requestForQuotationService.BookingServiceDTO;
import com.wedding.backend.dto.requestForQuotationService.BookingServicesBySupplier;
import com.wedding.backend.dto.requestForQuotationService.RequestForQuotationsDTO;
import com.wedding.backend.dto.service.ServiceByRequestForQuotation;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface IRequestForQuotationService {
    BaseResult addBooking(BookingServiceDTO bookingServiceDTO);

    BaseResultWithDataAndCount<List<BookingServicesBySupplier>> getBookingServiceBySupplierId(Principal connectedUser);

    BaseResult changeStatusBooking(String status, Long bookingId);

    BaseResult addRequestForQuotationToCart(Long serviceId, Principal connectedUser);

    BaseResultWithDataAndCount<List<ServiceByRequestForQuotation>> getRequestForQuotationToCartByCustomerId(Pageable pageable, Principal connectedUser);

    BaseResult removeRequestForQuotationByCustomer(Long serviceId, Principal connectedUser);

    BaseResult sendPriceQuotes(RequestForQuotationsDTO request, Principal connectedUser);
}
