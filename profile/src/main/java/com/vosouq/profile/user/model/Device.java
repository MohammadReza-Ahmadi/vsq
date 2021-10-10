package com.vosouq.profile.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Device {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long userId;
    private String udid;
    private String name;
    private String os;
    private String osVersion;
    private String appVersion;
    private String fcmToken;
    private String pushToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fcmUpdateDate;
    private String bioToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date bioTokenCreateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date bioTokenUpdateDate;

}
