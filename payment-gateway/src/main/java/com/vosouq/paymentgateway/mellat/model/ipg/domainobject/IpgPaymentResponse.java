package com.vosouq.paymentgateway.mellat.model.ipg.domainobject;

import com.vosouq.paymentgateway.mellat.enumeration.BpResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IpgPaymentResponse {
    private BpResponseCode bpResponseCode;
    private Long requesterId;
}
