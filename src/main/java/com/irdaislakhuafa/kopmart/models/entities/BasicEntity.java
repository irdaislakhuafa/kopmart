package com.irdaislakhuafa.kopmart.models.entities;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// mapped this class as superclass for entities
@MappedSuperclass
// add entity listener
@EntityListeners(value = { AuditingEntityListener.class })
// lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicEntity<A> {
    @CreatedBy
    protected A createdBy;

    @CreatedDate
    // time/date temporal
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdDate;

    @LastModifiedBy
    protected A lastModifiedBy;

    @LastModifiedDate
    // time/date temporal
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedDate;
}
