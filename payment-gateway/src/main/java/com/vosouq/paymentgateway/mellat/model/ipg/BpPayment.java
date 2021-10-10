package com.vosouq.paymentgateway.mellat.model.ipg;

import com.vosouq.paymentgateway.mellat.enumeration.BpRequestStatus;
import com.vosouq.paymentgateway.mellat.enumeration.BpResponseCode;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BpPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
//    @MapsId
    private OrderSequence orderSequence;

    private Long requesterId;
    private String refId;
    private Long saleOrderId;
    private Long saleReferenceId;

    @Enumerated(EnumType.STRING)
    private BpResponseCode resCode;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private BpRequestStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date localDateTime;

    private String additionalData;
}
