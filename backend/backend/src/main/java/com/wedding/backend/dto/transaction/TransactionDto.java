package com.wedding.backend.dto.transaction;

import com.wedding.backend.dto.servicePackage.PartServicePackage;
import com.wedding.backend.dto.user.PartUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private Long transactionId;

    private Date purchaseDate;

    private Date expirationDate;

    private Integer extensionDays;

    private boolean expired;

    private PartUser partUser;

    private PartServicePackage partServicePackage;
}
