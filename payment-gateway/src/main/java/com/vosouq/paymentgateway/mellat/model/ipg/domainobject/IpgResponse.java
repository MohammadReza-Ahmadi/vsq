package com.vosouq.paymentgateway.mellat.model.ipg.domainobject;

import com.vosouq.paymentgateway.mellat.enumeration.BpResponseCode;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class IpgResponse {
    private String refId;
    private Long orderId;

    @NonNull
    @Enumerated(EnumType.STRING)
    private BpResponseCode resCode;
}
