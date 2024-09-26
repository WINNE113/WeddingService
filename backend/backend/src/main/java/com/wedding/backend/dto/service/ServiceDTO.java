package com.wedding.backend.dto.service;

import com.wedding.backend.common.StatusCommon;
import com.wedding.backend.entity.ServiceTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDTO {
    private Long id;
    private String title;
    private String image;
    private String address;
    private boolean isDeleted;
    private String status;
    private ServiceTypeEntity serviceType;
    private boolean isSelected;
    private Date createdDate;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
