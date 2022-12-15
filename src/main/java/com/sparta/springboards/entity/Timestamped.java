package com.sparta.springboards.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public class Timestamped {
    //생성일자
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    //마지막 수정일자
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;
}