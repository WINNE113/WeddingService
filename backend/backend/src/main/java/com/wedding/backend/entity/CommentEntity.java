package com.wedding.backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntityWithIDIncrement {
    @Column(name = "parent_comment")
    private Long parentComment;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private boolean status;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "user_id")
    private Long userId;

}
