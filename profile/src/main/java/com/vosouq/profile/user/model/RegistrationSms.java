package com.vosouq.profile.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class RegistrationSms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private long deviceId;
    private String code;
    private boolean verified;
    private int failedTry;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date tryDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;

}
