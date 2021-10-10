package com.vosouq.profile.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class BlockedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String phoneNumber;
    private Long deviceId;
    private String udid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date blockUntil;

    public int getRemainingBlockDurationInSeconds(){
        return (int) (blockUntil.getTime() - System.currentTimeMillis()) / 1000;
    }
}