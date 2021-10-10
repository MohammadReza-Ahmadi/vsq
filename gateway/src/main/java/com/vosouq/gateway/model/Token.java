package com.vosouq.gateway.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Long userId;
    private Long deviceId;
    private String phoneNumber;
    private String clientId;
    private String clientName;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireDate;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

}
