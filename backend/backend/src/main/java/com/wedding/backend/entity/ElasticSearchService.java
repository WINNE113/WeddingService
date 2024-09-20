package com.wedding.backend.entity;

import com.wedding.backend.common.StatusCommon;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "services")
@Getter
@Setter
public class ElasticSearchService {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long serviceId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String information;

    @Field(type = FieldType.Double, name = "price")
    private double price;

    @Field(type = FieldType.Text)
    private String image;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Text)
    private String linkWebsite;

    @Field(type = FieldType.Text)
    private String linkFacebook;

    @Field(type = FieldType.Text)
    private String rotation;

    @Field(type = FieldType.Boolean)
    private boolean isDeleted;

    @Field(type = FieldType.Boolean)
    private boolean isSelected;

    @Enumerated(EnumType.STRING)
    @Field(type = FieldType.Keyword)
    private StatusCommon status;
}
