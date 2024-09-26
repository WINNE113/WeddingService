package com.wedding.backend.dto.service;

import java.math.BigDecimal;
import java.util.Date;

public interface ServiceByPackageDTO {
    Long getId();

    String getTitle();

    String getImage();

    String getAddress();

    Date getCreatedDate();

    Date getPurchaseDate();

    Long getSupplierId();

    BigDecimal getMaxPrice();

    BigDecimal getMinPrice();

    Integer getRn();
}
