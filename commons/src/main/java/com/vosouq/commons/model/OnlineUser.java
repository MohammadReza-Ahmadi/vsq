package com.vosouq.commons.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OnlineUser {

    private long userId;
    private long deviceId;
    private String phoneNumber;
    private String clientId;
    private String clientName;

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public OnlineUser clone() {
        return new OnlineUser(userId, deviceId, phoneNumber, clientId, clientName);
    }

    @Override
    public String toString() {
        return "OnlineUser{" +
                "userId=" + userId +
                ", deviceId=" + deviceId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
