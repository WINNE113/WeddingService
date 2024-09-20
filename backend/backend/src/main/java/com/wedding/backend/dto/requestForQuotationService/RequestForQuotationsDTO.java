package com.wedding.backend.dto.requestForQuotationService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestForQuotationsDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String notes;
    private List<Long> serviceIds;
}
