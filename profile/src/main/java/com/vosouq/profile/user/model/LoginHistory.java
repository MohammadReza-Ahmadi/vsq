package com.vosouq.profile.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class LoginHistory {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private long userId;
    private long deviceId;
    private String deviceName;
    private String phoneNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Enumerated(value = EnumType.STRING)
    private LoginAction action;


}
