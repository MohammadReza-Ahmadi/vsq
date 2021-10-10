package com.vosouq.contract.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Setter
@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "SERVICE")
public class ServiceContractTemplate extends ContractTemplate { }
