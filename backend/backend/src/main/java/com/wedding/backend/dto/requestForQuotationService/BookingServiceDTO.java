package com.wedding.backend.dto.requestForQuotationService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingServiceDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String notes;
    private Long serviceId;
}
