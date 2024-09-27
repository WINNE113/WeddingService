package com.wedding.backend.dto.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDTO {
    private Long id;
    private String name;
    private String logo;
    private String phoneNumberSupplier;
    private String emailSupplier;
    private String addressSupplier;
    private Integer followerCount;
    private String statusSupplier;
    private Date createdDate;
    private Date modifiedDate;
    private List<String> imagesLicence;
}
