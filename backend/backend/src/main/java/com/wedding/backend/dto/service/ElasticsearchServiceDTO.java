package com.wedding.backend.dto.service;

import com.wedding.backend.common.StatusCommon;
import lombok.Data;


@Data
public class ElasticsearchServiceDTO {

    private String id;

    private Long serviceId;

    private String title;

    private String information;

    private String image;

    private String address;

    private String linkWebsite;

    private String linkFacebook;

    private String rotation;

    private boolean isDeleted;

    private boolean isSelected;

    private boolean isPublishToElasticsearch;
}
