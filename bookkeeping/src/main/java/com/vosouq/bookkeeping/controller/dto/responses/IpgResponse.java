package com.vosouq.bookkeeping.controller.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IpgResponse {
    private String refId;
    private Long orderId;
}
