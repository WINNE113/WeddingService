package com.wedding.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wedding.backend.common.StatusCommon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "services")
public class ServiceEntity extends BaseEntityWithIDIncrement {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "information", columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    @Lob
    private String information;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "link_website")
    private String linkWebsite;

    @Column(name = "link_facebook")
    private String linkFacebook;

    @Column(name = "rotation")
    private String rotation;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private StatusCommon status;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private UserEntity authorId;

    @ManyToOne()
    @JoinColumn(name = "service_type_id", referencedColumnName = "id")
    @JsonBackReference
    private ServiceTypeEntity serviceType;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_album_id", referencedColumnName = "id")
    private ServiceAlbumEntity serviceAlbum;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_promotion_id", referencedColumnName = "id")
    private ServicePromotionEntity servicePromotion;

    @OneToMany(mappedBy = "services")
    @JsonBackReference
    private List<ReportEntity> reports;

}
