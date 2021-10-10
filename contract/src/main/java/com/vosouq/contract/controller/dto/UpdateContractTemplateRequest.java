package com.vosouq.contract.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateContractTemplateRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Long id;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String title;
    private Long price;
    private String province;
    private String city;
    private String neighbourhood;
    private String description;
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean repeatable;
    @Min(2)
    @ApiModelProperty(allowableValues = "greater than or equal to 1")
    private Integer numberOfRepeats;
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean viewable;
    private List<Long> fileIds;

}