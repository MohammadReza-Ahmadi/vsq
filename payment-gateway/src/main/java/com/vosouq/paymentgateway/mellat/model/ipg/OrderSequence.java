package com.vosouq.paymentgateway.mellat.model.ipg;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
public class OrderSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long orderId;

    @OneToOne(mappedBy = "orderSequence", optional = false)
    private BpPayment bpPayment;

}