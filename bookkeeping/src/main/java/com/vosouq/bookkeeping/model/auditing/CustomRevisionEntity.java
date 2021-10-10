package com.vosouq.bookkeeping.model.auditing;

import com.vosouq.bookkeeping.config.CustomRevisionListener;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@RevisionEntity(value = CustomRevisionListener.class)
@Table(name = "revinfo")
public class CustomRevisionEntity extends DefaultRevisionEntity {

    private Long userId;
}
