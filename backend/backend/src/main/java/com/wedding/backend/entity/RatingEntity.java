package com.wedding.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ratings", uniqueConstraints = { @UniqueConstraint(name = "UP_ratings", columnNames = { "service_id", "user_id" }) })
public class RatingEntity extends BaseEntityWithIDIncrement {

    @Column(name = "star_point")
    private double starPoint;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "user_id")
    private Long userId;
}
