package com.wedding.backend.dto.service;

import java.math.BigDecimal;

public interface ServiceDetail {
    Long getServiceId();

    String getTitle();

    String getAddressService();

    String getImage();

    String getInformation();

    String getLinkFacebook();

    String getLinkWebsite();

    String getPhoneNumberSupplier();

    String getRotation();

    String getSupplierId();

    String getSupplierName();

    String getAddressSupplier();

    String getLogo();

    String getServiceTypeName();

    BigDecimal getMinPrice();
    BigDecimal getMaxPrice();
}
