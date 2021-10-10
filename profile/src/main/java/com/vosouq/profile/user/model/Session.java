package com.vosouq.profile.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Session {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private long deviceId;
    private String deviceName;
    private String os;
    private String osVersion;
    private String appVersion;
    private String fcmToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

}
