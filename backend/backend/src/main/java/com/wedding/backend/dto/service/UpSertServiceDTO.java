package com.wedding.backend.dto.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpSertServiceDTO {
    private Long id;
    private String title;
    private String information;
    private String image;
    private String address;
    private String linkWebsite;
    private String linkFacebook;
    private String rotation;
    private Long serviceTypeId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
