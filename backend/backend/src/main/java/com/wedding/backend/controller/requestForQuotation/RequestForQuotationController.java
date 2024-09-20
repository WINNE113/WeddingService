package com.wedding.backend.controller.requestForQuotation;

import com.wedding.backend.dto.requestForQuotationService.BookingServiceDTO;
import com.wedding.backend.dto.requestForQuotationService.RequestForQuotationsDTO;
import com.wedding.backend.service.IService.requestForQuotation.IRequestForQuotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/v1/request-for-quotation")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RequestForQuotationController {

    private final IRequestForQuotationService requestForQuotationService;

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<?> addRequestForQuotation(@RequestBody BookingServiceDTO bookingServiceDTO) {
        return ResponseEntity.ok(requestForQuotationService.addBooking(bookingServiceDTO));
    }

    @PostMapping("/send-request-for-quotation")
    public ResponseEntity<?> sendPriceQuote(@RequestBody RequestForQuotationsDTO request, Principal connectedUser) {
        return ResponseEntity.ok(requestForQuotationService.sendPriceQuotes(request, connectedUser));
    }

    @PostMapping(value = "/add-request-for-quotation-to-cart")
    public ResponseEntity<?> addRequestForQuotationToCart(@RequestParam(name = "serviceId") Long serviceId, Principal connectedUser) {
        return ResponseEntity.ok(requestForQuotationService.addRequestForQuotationToCart(serviceId, connectedUser));
    }

    @DeleteMapping(value = "/remove-by-customer/{serviceId}")
    public ResponseEntity<?> removeRequestForQuotationByCustomer(@PathVariable(name = "serviceId") Long serviceId, Principal connectedUser){
        return ResponseEntity.ok(requestForQuotationService.removeRequestForQuotationByCustomer(serviceId, connectedUser));
    }

    @GetMapping(value = "/request-for-quotation-by-customer")
    public ResponseEntity<?> getRequestForQuotationToCartByCustomerId(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                      @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                                                      Principal connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(requestForQuotationService.getRequestForQuotationToCartByCustomerId(pageable,connectedUser));
    }

    @GetMapping("/request-for-quotation-by-supplier-id")
    public ResponseEntity<?> getRequestForQuotationBySupplierId(Principal connectedUser) {
        return ResponseEntity.ok(requestForQuotationService.getBookingServiceBySupplierId(connectedUser));
    }

    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    @PatchMapping("/change/status-request-for-quotation")
    public ResponseEntity<?> changeStatusRequestForQuotation(@RequestParam(name = "status") String status, @RequestParam(name = "bookingId") Long bookingId) {
        return ResponseEntity.ok(requestForQuotationService.changeStatusBooking(status, bookingId));
    }
}
