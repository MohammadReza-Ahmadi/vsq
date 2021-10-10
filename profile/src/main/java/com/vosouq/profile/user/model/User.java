package com.vosouq.profile.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private String phoneNumber;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    private String nationalCode;
    private String serial;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String profileImageAddress;

}
