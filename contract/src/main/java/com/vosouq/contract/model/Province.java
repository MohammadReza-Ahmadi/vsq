package com.vosouq.contract.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Province {
    @Id
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String slug;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
