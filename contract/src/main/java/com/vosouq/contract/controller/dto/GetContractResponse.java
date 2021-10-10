package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.ContractState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetContractResponse {

    private ContractType contractType;
    private Long id;
    private Long createDate;
    private Long updateDate;
    private ContractState state;
    private String title;
    private Long price;
    private Long terminationPenalty;
    private Long deliveryDate;
    private String province;
    private String city;
    private String neighbourhood;
    private String description;
    private Boolean multiStepPayment;
    private List<PaymentResponse> payments;
    private Long sellerSignDate;
    private Long buyerSignDate;
    private List<Long> fileIds;
    private List<Long> otherAttachments;
    private String signDescription;

}
