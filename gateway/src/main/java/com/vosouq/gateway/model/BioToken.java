package com.vosouq.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class BioToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    private long userId;
    private Long deviceId;
    private String phoneNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

}
