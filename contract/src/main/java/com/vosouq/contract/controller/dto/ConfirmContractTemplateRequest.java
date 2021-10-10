package com.vosouq.contract.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmContractTemplateRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Long contractTemplateId;
    @NotNull
    @ApiModelProperty(required = true)
    private Long suggestionId;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String title;
    @NotNull
    @ApiModelProperty(required = true)
    private Long price;
    private Long terminationPenalty;
    private String province;
    private String city;
    private String neighbourhood;
    private String description;
    @NotEmpty
    @ApiModelProperty(required = true)
    private List<PaymentTemplateRequest> paymentTemplates;
    @ApiModelProperty(required = true, example = "1596539486")
    private Long deliveryDate;
    private List<Long> fileIds;
    private List<Long> otherAttachments;
    private String signDescription;
}